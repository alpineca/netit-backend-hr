package com.enikolov.netitbackendhr.controllers.html;

import com.enikolov.netitbackendhr.enums.AppliesStatus;
import com.enikolov.netitbackendhr.models.general.Campaign;
import com.enikolov.netitbackendhr.models.users.User;
import com.enikolov.netitbackendhr.services.AppliesDataService;
import com.enikolov.netitbackendhr.services.data.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;

@Controller
public class AppliesController {
    @Autowired
    private UserDataService userDataService;
    @Autowired
    private AppliesDataService appliesDataService;

    @GetMapping("/applies/show-all")
    public String getAppliesPage(Model model) {
        User user = this.userDataService.getLoggedUser();
        model.addAttribute("user", user);

        HashMap<Campaign, AppliesStatus> applies = this.appliesDataService.getAppliedCampaigns();

        model.addAttribute("applies", applies);
        return "applies/show-all";
    }

    @GetMapping("/applies/show-all/blues")
    public String getBluesAppliesPage(Model model) {
        User user = this.userDataService.getLoggedUser();
        model.addAttribute("user", user);

        HashMap<Campaign, AppliesStatus> applies = this.appliesDataService.getBluesAppliedCampaigns();

        model.addAttribute("applies", applies);
        return "applies/show-all";
    }
}
