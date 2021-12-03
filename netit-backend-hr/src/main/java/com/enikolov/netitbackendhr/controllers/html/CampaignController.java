package com.enikolov.netitbackendhr.controllers.html;

import com.enikolov.netitbackendhr.models.general.Campaign;
import com.enikolov.netitbackendhr.models.users.User;
import com.enikolov.netitbackendhr.repositories.general.CampaignRepository;
import com.enikolov.netitbackendhr.services.data.UserData;
import com.enikolov.netitbackendhr.utils.SystemClock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@Controller
public class CampaignController {

    @Autowired
    private SystemClock systemClock;

    @Autowired
    private UserData userData;

    @Autowired
    private CampaignRepository campaignRepository;

    @GetMapping("/campaigns/show-all")
    public String getShowAllPage(Model model){
        List<Campaign> campaignsList = this.campaignRepository.findAllByEmployerId(this.userData.getLoggedEmployer().getId());

        if(campaignsList.isEmpty()) {
            model.addAttribute("error", "No campaigns");
            return "campaigns/show-all";
        }

        model.addAttribute("campaigns", campaignsList);
        return "campaigns/show-all";
    }

    @GetMapping("/campaigns/create")
    public String getCreateCampaignPage(Model model){

        model.addAttribute("campaign", new Campaign());

        return "campaigns/create";
    }

    @GetMapping("/campaigns/edit/{id}")
    public String getEditCampaignPage(@PathVariable int id, Model model){
        User thisUser = userData.getLoggedUser();

        Optional<Campaign> campaignModel = this.campaignRepository.getMyCampaign(id, thisUser.getId());
        if(campaignModel.isPresent()) {
            Campaign campaignEntity = campaignModel.get();

            model.addAttribute("campaignEntity", campaignEntity);
            return "/campaigns/edit";
        }
        return "/campaigns/show-all";
    }

    @PostMapping("/campaigns/edit")
    public RedirectView editCampaign(@ModelAttribute Campaign campaign){
        User thisUser = userData.getLoggedUser();
        Campaign entityToUpdate;

        Optional<Campaign> campaignModel = this.campaignRepository.getMyCampaign(campaign.getId(), thisUser.getId());
        if(campaignModel.isPresent()){
            entityToUpdate = campaignModel.get();

            entityToUpdate.setTitle(campaign.getTitle());
            entityToUpdate.setDescription(campaign.getDescription());
            entityToUpdate.setSalaryMin(campaign.getSalaryMin());
            entityToUpdate.setSalaryMax(campaign.getSalaryMax());

            this.campaignRepository.save(entityToUpdate);
        }



        return new RedirectView("/campaigns/show-all");
    }

    @GetMapping("/campaigns/delete/{id}")
    public RedirectView deleteCampaign(@PathVariable int id){
        User thisUser = userData.getLoggedUser();
        Optional<Campaign> campaignModel = this.campaignRepository.getMyCampaign(id, thisUser.getId());
        if(campaignModel.isPresent()){
            Campaign entityToDelete = campaignModel.get();

            this.campaignRepository.delete(entityToDelete);
        }

        return new RedirectView ("/campaigns/show-all");
    }

    @GetMapping("/campaigns/view/{id}")
    public String viewOneCampaign(@PathVariable int id, Model model){

//        this.campaignRepository.deleteById(id);
        Optional<Campaign> campaignModel = this.campaignRepository.findById(id);
        if(campaignModel.isPresent()){
            Campaign campaignEntity = campaignModel.get();
            model.addAttribute("campaign", campaignEntity);
            return "/campaigns/show-one";
        }
//        model.addAttribute("campaign")
        return "/campaigns/show-all";
    }

    @PostMapping("/campaigns/create")
    public RedirectView createNewCampaign(@ModelAttribute Campaign newCampaign){
        String date             = this.systemClock.getSystemDate();
        String time             = this.systemClock.getSystemTime();

        newCampaign.setPublishDate(date);
        newCampaign.setPublishTime(time);
        newCampaign.setEmployerId(this.userData.getLoggedEmployer().getId());



        this.campaignRepository.save(newCampaign);
        return new RedirectView("show-all");
    }

}
