package com.enikolov.netitbackendhr.controllers.html;

import com.enikolov.netitbackendhr.components.InfoMessage;
import com.enikolov.netitbackendhr.enums.AppliesStatus;
import com.enikolov.netitbackendhr.enums.MessageStyle;
import com.enikolov.netitbackendhr.models.general.Applies;
import com.enikolov.netitbackendhr.models.general.Campaign;
import com.enikolov.netitbackendhr.models.users.Employee;
import com.enikolov.netitbackendhr.models.users.User;
import com.enikolov.netitbackendhr.services.data.AppliesDataService;
import com.enikolov.netitbackendhr.services.data.MessageDataService;
import com.enikolov.netitbackendhr.services.data.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
public class AppliesController {
    @Autowired
    private UserDataService userDataService;
    @Autowired
    private AppliesDataService appliesDataService;
    @Autowired
    private MessageDataService messageDataService;

    @GetMapping("/applies/show-all")
    public String getAppliesPage(Model model) {
        User user           = this.userDataService.getLoggedUser();
        InfoMessage message = new InfoMessage();
        List<Applies> applies = new ArrayList<>();
        model.addAttribute("user", user);
        boolean hasUnread = this.messageDataService.isUnreadMessages();
        if(user.getUserRole().equals("HR")){
            applies = this.appliesDataService.gettAllApplies();
        }
        if(user.getUserRole().equals("EMPLOYER")){
            applies = this.appliesDataService.getAppliesForMyCampaigns();
        }
        if(user.getUserRole().equals("EMPLOYEE")){
            applies = this.appliesDataService.getAppliedCampaigns();
        }

        if(applies.size() < 1){
            message.setMessage("There is no applies!");
            message.setStyle(MessageStyle.ERROR_MSG);
        }

        model.addAttribute("message", message);
        model.addAttribute("applies", applies);
        model.addAttribute("hasUnread", hasUnread);
        return "applies/show-all";
    }

    @GetMapping("/applies/show/{id}")
    public String getShowThisApply(@PathVariable int id, Model model){
        Optional<Applies> applyModel = this.appliesDataService.getApplyById(id);
        if(applyModel.isPresent()){
            Applies apply       = applyModel.get();
            Campaign campaign   = apply.getCampaign();
            Employee candidate  = apply.getEmployee();
            User candidateUser  = candidate.getUser();

            model.addAttribute("apply"          , apply);
            model.addAttribute("campaign"       , campaign);
            model.addAttribute("candidate"      , candidate);
            model.addAttribute("candidateUser"  , candidateUser);
            model.addAttribute("user"           , this.userDataService.getLoggedUser());
            model.addAttribute("hasUnread", this.messageDataService.isUnreadMessages());
            return "/applies/show-one";
        }

        return "/applies/show-all";
    }

    @GetMapping("/applies/show-all/blues")
    public String getBluesAppliesPage(Model model) {
        User user = this.userDataService.getLoggedUser();
        model.addAttribute("user", user);

        HashMap<Campaign, AppliesStatus> applies = this.appliesDataService.getBluesAppliedCampaigns();

        model.addAttribute("applies", applies);
        model.addAttribute("hasUnread", this.messageDataService.isUnreadMessages());
        return "applies/show-all";
    }
    @GetMapping("/applies/review/{id}/{status}")
    public RedirectView rejectApply(@PathVariable int id, @PathVariable String status){
        String userRole = this.userDataService.getLoggedUser().getUserRole();
        AppliesStatus setStatus = AppliesStatus.PENDING;
        if(!userRole.equals("HR")){
            return new RedirectView("/applies/show-all");
        }
        if(status.equals("interview-approved")){
            setStatus = AppliesStatus.INTERVIEW_APPROVED;
        }
        if(status.equals("job-approved")){
            setStatus = AppliesStatus.JOB_APPROVED;
        }
        if(status.equals("rejected")){
            setStatus = AppliesStatus.REJECTED;
        }
        this.appliesDataService.setApplieStatus(id, setStatus);

        return new RedirectView("/applies/show/" + id);
    }
}
