package com.enikolov.netitbackendhr.controllers.html;

import com.enikolov.netitbackendhr.models.DTO.FilterByEmployer;
import com.enikolov.netitbackendhr.models.general.Campaign;
import com.enikolov.netitbackendhr.models.users.Employee;
import com.enikolov.netitbackendhr.models.users.Employer;
import com.enikolov.netitbackendhr.models.users.User;
import com.enikolov.netitbackendhr.services.data.*;
import com.enikolov.netitbackendhr.components.InfoMessage;
import com.enikolov.netitbackendhr.components.SystemClock;
import com.enikolov.netitbackendhr.enums.MessageStyle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@Controller
public class CampaignController {

    @Autowired
    private SystemClock systemClock;
    @Autowired
    private UserDataService userDataService;
    @Autowired
    private CampaignsDataService campaignsDataService;
    @Autowired
    private EmployerDataService employerDataService;
    @Autowired
    private CategoryDataService categoryDataService;
    @Autowired
    private AppliesDataService appliesDataService;
    @Autowired
    private MessageDataService messageDataService;

    @GetMapping("/campaigns/show-all")
    public String getShowAllPage(Model model){
        User user                           = userDataService.getLoggedUser();
        model.addAttribute("user", user);
        List<Employer> allEmployers         = this.employerDataService.getEmployerSelectionList();
        List<Campaign> campaignsList        = null;
        InfoMessage message                 = (InfoMessage) model.asMap().get("message");
        FilterByEmployer employerSelected   = new FilterByEmployer();

        if(user.getUserRole().equals("EMPLOYEE") || user.getUserRole().equals("HR")){
            campaignsList = this.campaignsDataService.getAll();

        }
        if(user.getUserRole().equals("EMPLOYER")){

            campaignsList = this.campaignsDataService.getAllByThisEmployer();

        }

        if(campaignsList.size() == 0) {
            message = new InfoMessage();
            message.setMessage("No campaigns found");
            message.setStyle(MessageStyle.ERROR_MSG);
        }

        model.addAttribute("allEmployers"       , allEmployers);
        model.addAttribute("employerSelected"   , employerSelected);
        model.addAttribute("message"            , message);
        model.addAttribute("campaigns"          , campaignsList);
        model.addAttribute("hasUnread", this.messageDataService.isUnreadMessages());

        return "campaigns/show-all";
    }

    @PostMapping("/campaigns/filter-by-employer")
    public RedirectView getFilteredCampaigns(@ModelAttribute FilterByEmployer filterByEmployer, RedirectAttributes redirectAttributes){
        int employerId = filterByEmployer.getId();
        List<Campaign> campaignsList = this.campaignsDataService.getFilteredByEmployerIdCampaigns(employerId);

        redirectAttributes.addFlashAttribute("campaigns", campaignsList);

        return new RedirectView("/campaigns/show-filtered");
    }

    @GetMapping("/campaigns/show-filtered")
    public String showFilteredCampaigns(Model model){
        User user = userDataService.getLoggedUser();
        model.addAttribute("user", user);
        List<Employer> allEmployers = this.employerDataService.getEmployerSelectionList();
        FilterByEmployer employerSelected = new FilterByEmployer();
        InfoMessage message             = (InfoMessage) model.asMap().get("message");
        List<Campaign> campaignsList    = (List<Campaign>) model.asMap().get("campaigns");


        model.addAttribute("allEmployers", allEmployers);
        model.addAttribute("employerSelected", employerSelected);
        model.addAttribute("message"    , message);
        model.addAttribute("campaigns"  , campaignsList);
        model.addAttribute("hasUnread", this.messageDataService.isUnreadMessages());

        return "campaigns/show-all";
    }

    @GetMapping("/campaigns/show-one")
    public String getShowOneCampaignPage(Model model){
        User user = userDataService.getLoggedUser();
        model.addAttribute("user", user);

        Campaign campaign = (Campaign) model.asMap().get("campaign");

        model.addAttribute("campaign", campaign);

        return "campaigns/show-one";
    }

    @GetMapping("/campaigns/create")
    public String getCreateCampaignPage(Model model){
        User user = userDataService.getLoggedUser();
        model.addAttribute("user", user);

        model.addAttribute("categories", this.categoryDataService.getAllCategories());
        model.addAttribute("campaign", new Campaign());
        model.addAttribute("hasUnread", this.messageDataService.isUnreadMessages());
        return "campaigns/create";
    }
    @GetMapping("/campaigns/edit")
    public String getEditCampaignPage(Model model){
        User user = userDataService.getLoggedUser();
        model.addAttribute("user", user);

        Campaign campaignEntity = (Campaign) model.asMap().get("campaignEntity");
        model.addAttribute("hasUnread", this.messageDataService.isUnreadMessages());
        return "campaigns/edit";
    }

    @GetMapping("/campaigns/edit/{id}")
    public RedirectView getEditCampaignPage(@PathVariable int id, Model model, RedirectAttributes redirectAttributes){
        User user = userDataService.getLoggedUser();
        model.addAttribute("user", user);

        Optional<Campaign> campaignModel = this.campaignsDataService.getMyCampaign(id);
        if(campaignModel.isPresent()) {
            Campaign campaignEntity = campaignModel.get();

            redirectAttributes.addFlashAttribute("campaignEntity", campaignEntity);
            return new RedirectView("/campaigns/edit");
        }

        InfoMessage message = new InfoMessage("Campaign not found", MessageStyle.ERROR_MSG);
        redirectAttributes.addFlashAttribute("message", message);

        return new RedirectView("/campaigns/show-all");
    }

    @GetMapping("/campaigns/apply/{id}")
    public RedirectView applyForCampaign(@PathVariable int id, RedirectAttributes redirectAttributes){
        User user = userDataService.getLoggedUser();
        InfoMessage message = new InfoMessage();

        Optional<Employee> thisEmployeeModel = Optional.ofNullable(this.userDataService.getLoggedEmployee());
        Optional<Campaign> thisCampaignModel = this.campaignsDataService.getById(id);

        if(thisEmployeeModel.isEmpty() || thisCampaignModel.isEmpty()){
            return new RedirectView("show-all");
        }

        Employee thisEmployee = thisEmployeeModel.get();
        Campaign thisCampaign = thisCampaignModel.get();

        try{
            this.appliesDataService.createNewApply(thisEmployee, thisCampaign);
            message.setMessage("You successfuly applied for campaing. \n The HR Agents will review your apply soon.");
            message.setStyle(MessageStyle.SUCCESS_MSG);
        }catch (Exception e){
            e.printStackTrace();
            message.setMessage("Something went wrong, Please try again later!");
            message.setStyle(MessageStyle.ERROR_MSG);
        }

        redirectAttributes.addFlashAttribute("message", message);
        return new RedirectView("/campaigns/show-all");
    }

    @PostMapping("/campaigns/edit")
    public RedirectView editCampaign(@ModelAttribute Campaign campaign, RedirectAttributes redirectAttributes){
        User user           = userDataService.getLoggedUser();
        InfoMessage message = new InfoMessage();
        Campaign entityToUpdate;

        Optional<Campaign> campaignModel = this.campaignsDataService.getMyCampaign(campaign.getId());
        if(campaignModel.isPresent()){
            entityToUpdate = campaignModel.get();

            try{
                this.campaignsDataService.updateThisCampaign(entityToUpdate, campaign);
                message.setMessage("Campaign succsessfuly edited!");
                message.setStyle(MessageStyle.SUCCESS_MSG);

            }catch (Exception e){
                e.printStackTrace();
                message.setMessage("Something went wrong!");
                message.setStyle(MessageStyle.ERROR_MSG);
            }

            redirectAttributes.addFlashAttribute("message", message);
            return new RedirectView("/campaigns/show-all");
        }
        
        message.setMessage("Campaign not found!");
        message.setStyle(MessageStyle.ERROR_MSG);

        redirectAttributes.addFlashAttribute("message", message);
        return new RedirectView("/campaigns/show-all");
    }

    @GetMapping("/campaigns/delete/{id}")
    public RedirectView deleteCampaign(@PathVariable int id, RedirectAttributes redirectAttributes){
        User thisUser = userDataService.getLoggedUser();
        InfoMessage message = new InfoMessage();
        Optional<Campaign> campaignModel = this.campaignsDataService.getMyCampaign(id);

        if(campaignModel.isPresent()){
            Campaign entityToDelete = campaignModel.get();
            try{
                this.campaignsDataService.deleteCampaign(entityToDelete);
                message.setMessage("Your campaign is succsessfuly deleted!");
                message.setStyle(MessageStyle.SUCCESS_MSG);
            }catch(Exception e){
                e.printStackTrace();
                message.setMessage("Something went wrong");
                message.setStyle(MessageStyle.ERROR_MSG);
            }
            redirectAttributes.addFlashAttribute("message", message);
            return new RedirectView ("/campaigns/show-all");
        }

        message.setMessage("Campaign not found!");
        message.setStyle(MessageStyle.ERROR_MSG);

        redirectAttributes.addFlashAttribute("message", message);
        return new RedirectView ("/campaigns/show-all");
    }

    @GetMapping("/campaigns/view/{id}")
    public RedirectView viewOneCampaign(@PathVariable int id, Model model, RedirectAttributes redirectAttributes){
        User user           = userDataService.getLoggedUser();
        model.addAttribute("user", user);
        InfoMessage message = new InfoMessage();

        Optional<Campaign> campaignModel = this.campaignsDataService.getById(id);
        if(campaignModel.isPresent()){
            Campaign campaignEntity = campaignModel.get();

            redirectAttributes.addFlashAttribute("campaign", campaignEntity);
            return new RedirectView("/campaigns/show-one");
        }

        message.setMessage("Campaign not found");
        message.setStyle(MessageStyle.ERROR_MSG);

        redirectAttributes.addFlashAttribute("message", message);
        return new RedirectView("/campaigns/show-all");
    }

    @PostMapping("/campaigns/create")
    public RedirectView createNewCampaign(@ModelAttribute Campaign newCampaign, RedirectAttributes redirectAttributes){
        InfoMessage message     = new InfoMessage();
        try{

            this.campaignsDataService.createNewCampaign(newCampaign);
            message.setMessage("New campaign successfuly created!");
            message.setStyle(MessageStyle.SUCCESS_MSG);

        }catch (Exception e){
            e.printStackTrace();
            message.setMessage("Something went wrong");
            message.setStyle(MessageStyle.ERROR_MSG);
        }

        redirectAttributes.addFlashAttribute("message", message);
        return new RedirectView("show-all");
    }

}
