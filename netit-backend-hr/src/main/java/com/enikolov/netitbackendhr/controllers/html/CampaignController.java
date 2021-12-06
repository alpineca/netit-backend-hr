package com.enikolov.netitbackendhr.controllers.html;

import com.enikolov.netitbackendhr.models.DTO.FilterByEmployer;
import com.enikolov.netitbackendhr.models.general.Campaign;
import com.enikolov.netitbackendhr.models.users.Employee;
import com.enikolov.netitbackendhr.models.users.Employer;
import com.enikolov.netitbackendhr.models.users.User;
import com.enikolov.netitbackendhr.repositories.general.AppliesRepository;
import com.enikolov.netitbackendhr.repositories.general.CampaignRepository;
import com.enikolov.netitbackendhr.repositories.users.EmployeeRepository;
import com.enikolov.netitbackendhr.repositories.users.EmployerRepository;
import com.enikolov.netitbackendhr.services.AppliesDataService;
import com.enikolov.netitbackendhr.services.data.CampaignsDataService;
import com.enikolov.netitbackendhr.services.data.EmployerDataService;
import com.enikolov.netitbackendhr.services.data.UserDataService;
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
    private UserDataService userData;
    @Autowired
    private CampaignsDataService campaignsDataService;
    @Autowired
    private EmployerDataService employerDataService;
    @Autowired
    private CampaignRepository campaignRepository;
    @Autowired
    private EmployerRepository employerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AppliesRepository appliesRepository;
    @Autowired
    private AppliesDataService appliesDataService;

    @GetMapping("/campaigns/show-all")
    public String getShowAllPage(Model model){
        User user = userData.getLoggedUser();
        model.addAttribute("user", user);
        List<Employer> allEmployers = this.employerDataService.getEmployerSelectionList();
        List<Campaign> campaignsList = null;

        if(user.getUserRole().equals("EMPLOYEE")){
            campaignsList = this.campaignRepository.findAll();
            campaignsList = setEmployerTitle(campaignsList);

        }
        if(user.getUserRole().equals("EMPLOYER")){
            campaignsList = this.campaignRepository.findAllByEmployerId(this.userData.getLoggedEmployer().getId());
            campaignsList = setEmployerTitle(campaignsList);

        }

        if(campaignsList.isEmpty()) {
            model.addAttribute("error", "No campaigns");
            return "campaigns/show-all";
        }

        InfoMessage message = (InfoMessage) model.asMap().get("message");
        FilterByEmployer employerSelected = new FilterByEmployer();

        model.addAttribute("allEmployers"       , allEmployers);
        model.addAttribute("employerSelected"   , employerSelected);
        model.addAttribute("message"            , message);
        model.addAttribute("campaigns"          , campaignsList);

        return "campaigns/show-all";
    }

    @PostMapping("/campaigns/filter-by-employer")
    public RedirectView getFilteredCampaigns(@ModelAttribute FilterByEmployer filterByEmployer, RedirectAttributes redirectAttributes){

        int employerId = filterByEmployer.getId();
        
        System.out.println("****************");
        System.out.println("EMPLOYER ID " + employerId);

        List<Campaign> campaignsList = this.campaignsDataService.getFilteredByEmployerIdCampaigns(employerId);

        redirectAttributes.addFlashAttribute("campaigns", campaignsList);

        return new RedirectView("/campaigns/show-filtered");
    }

    @GetMapping("/campaigns/show-filtered")
    public String showFilteredCampaigns(Model model){
        User user = userData.getLoggedUser();
        model.addAttribute("user", user);
        List<Employer> allEmployers = this.employerDataService.getEmployerSelectionList();
        FilterByEmployer employerSelected = new FilterByEmployer();
        InfoMessage message             = (InfoMessage) model.asMap().get("message");
        List<Campaign> campaignsList    = (List<Campaign>) model.asMap().get("campaigns");


        model.addAttribute("allEmployers", allEmployers);
        model.addAttribute("employerSelected", employerSelected);
        model.addAttribute("message"    , message);
        model.addAttribute("campaigns"  , campaignsList);

        return "campaigns/show-all";
    }

    @GetMapping("/campaigns/show-one")
    public String getShowOneCampaignPage(Model model){
        User user = userData.getLoggedUser();
        model.addAttribute("user", user);

        Campaign campaign = (Campaign) model.asMap().get("campaign");

        model.addAttribute("campaign", campaign);

        return "campaigns/show-one";
    }

    @GetMapping("/campaigns/create")
    public String getCreateCampaignPage(Model model){
        User user = userData.getLoggedUser();
        model.addAttribute("user", user);

        model.addAttribute("campaign", new Campaign());

        return "campaigns/create";
    }
    @GetMapping("/campaigns/edit")
    public String getEditCampaignPage(Model model){
        User user = userData.getLoggedUser();
        model.addAttribute("user", user);

        Campaign campaignEntity = (Campaign) model.asMap().get("campaignEntity");

        return "campaigns/edit";
    }

    @GetMapping("/campaigns/edit/{id}")
    public RedirectView getEditCampaignPage(@PathVariable int id, Model model, RedirectAttributes redirectAttributes){
        User user = userData.getLoggedUser();
        model.addAttribute("user", user);

        Optional<Campaign> campaignModel = this.campaignRepository.getMyCampaign(id, user.getId());
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
        User user = userData.getLoggedUser();

        Optional<Employee> thisEmployeeModel = this.employeeRepository.findEmployeeByUserId(user.getId());
        Optional<Campaign> thisCampaignModel = this.campaignRepository.findById(id);

        if(thisEmployeeModel.isEmpty() || thisCampaignModel.isEmpty()){
            return new RedirectView("show-all");
        }

        Employee thisEmployee = thisEmployeeModel.get();
        Campaign thisCampaign = thisCampaignModel.get();

        this.appliesDataService.createNewApply(thisEmployee, thisCampaign);
        return new RedirectView("/campaigns/show-all");
    }

    @PostMapping("/campaigns/edit")
    public RedirectView editCampaign(@ModelAttribute Campaign campaign, RedirectAttributes redirectAttributes){
        User user           = userData.getLoggedUser();
        InfoMessage message = new InfoMessage();
        Campaign entityToUpdate;

        Optional<Campaign> campaignModel = this.campaignRepository.getMyCampaign(campaign.getId(), user.getId());
        if(campaignModel.isPresent()){
            entityToUpdate = campaignModel.get();

            entityToUpdate.setTitle(campaign.getTitle());
            entityToUpdate.setDescription(campaign.getDescription());
            entityToUpdate.setSalaryMin(campaign.getSalaryMin());
            entityToUpdate.setSalaryMax(campaign.getSalaryMax());

            this.campaignRepository.save(entityToUpdate);

            message.setMessage("Campaign succsessfuly edited!");
            message.setStyle(MessageStyle.SUCCESS_MSG);

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
        User thisUser = userData.getLoggedUser();
        InfoMessage message = new InfoMessage();
        Optional<Campaign> campaignModel = this.campaignRepository.getMyCampaign(id, thisUser.getId());

        if(campaignModel.isPresent()){
            Campaign entityToDelete = campaignModel.get();
            this.campaignRepository.delete(entityToDelete);

            message.setMessage("Your campaign is succsessfuly deleted!");
            message.setStyle(MessageStyle.SUCCESS_MSG);

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
        User user           = userData.getLoggedUser();
        model.addAttribute("user", user);
        InfoMessage message = new InfoMessage();

        Optional<Campaign> campaignModel = this.campaignRepository.findById(id);
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
        String date             = this.systemClock.getSystemDate();
        String time             = this.systemClock.getSystemTime();
        InfoMessage message     = new InfoMessage();

        newCampaign.setPublishDate(date);
        newCampaign.setPublishTime(time);
        newCampaign.setEmployerId(this.userData.getLoggedEmployer().getId());

        this.campaignRepository.save(newCampaign);

        message.setMessage("New campaign successfuly created!");
        message.setStyle(MessageStyle.SUCCESS_MSG);

        redirectAttributes.addFlashAttribute("message", message);

        return new RedirectView("show-all");
    }

    private List<Campaign> setEmployerTitle(List<Campaign> campaignList){
        for(Campaign element : campaignList){
            element.setEmployerTitle(getEmployerTitle(element.getEmployerId()));
        }

        return campaignList;

    }
    private String getEmployerTitle(int employerId){
        Optional<Employer> employerModel = this.employerRepository.findById(employerId);

        if(!employerModel.isPresent()){
//            Employer employerEntity = employerModel.get();
            return "No available";
        }

        Employer employerEntity = employerModel.get();

        return employerEntity.getCompanyName();

    }

}
