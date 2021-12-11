package com.enikolov.netitbackendhr.services.data;

import com.enikolov.netitbackendhr.components.SystemClock;
import com.enikolov.netitbackendhr.enums.AppliesStatus;
import com.enikolov.netitbackendhr.models.general.Applies;
import com.enikolov.netitbackendhr.models.general.Campaign;
import com.enikolov.netitbackendhr.models.users.Employee;
import com.enikolov.netitbackendhr.models.users.Employer;
import com.enikolov.netitbackendhr.repositories.general.AppliesRepository;
import com.enikolov.netitbackendhr.repositories.general.CampaignRepository;
import com.enikolov.netitbackendhr.repositories.users.EmployeeRepository;
import com.enikolov.netitbackendhr.repositories.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AppliesDataService {
    @Autowired
    private SystemClock systemClock;
    @Autowired
    private UserDataService userDataService;
    @Autowired
    private AppliesRepository appliesRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CampaignRepository campaignRepository;

    public void createNewApply(Employee thisEmployee, Campaign thisCampaign){
        Applies applyThisCampaign = new Applies();
        String time = this.systemClock.getSystemTime();
        String date = this.systemClock.getSystemDate();


        applyThisCampaign.setCampaign(thisCampaign);
        applyThisCampaign.setEmployee(thisEmployee);
        applyThisCampaign.setDate(date);
        applyThisCampaign.setTime(time);
        applyThisCampaign.setStatus(AppliesStatus.PENDING);

        this.appliesRepository.save(applyThisCampaign);
    }

    public List<Applies> getAppliedCampaigns(){
        Employee thisEmployee = this.userDataService.getLoggedEmployee();
        return this.appliesRepository.findAllByEmployeeId(thisEmployee.getId());
    }
    public List<Applies> getAppliesForMyCampaigns(){
        Employer thisEmployer           = this.userDataService.getLoggedEmployer();
        List<Applies> appliesList       = new ArrayList<>();
        List<Campaign> campaignList     = this.campaignRepository.findAllByEmployerId(thisEmployer.getId());

        for(Campaign element : campaignList){
            Optional<Applies> applyModel = this.appliesRepository.findByCampaignId(element.getId());
            if(applyModel.isPresent()){
                appliesList.add(applyModel.get());
            }
        }
        
        return appliesList;
    }
    public List<Applies> gettAllApplies(){
        return this.appliesRepository.findAll();
    }
    public Optional<Applies> getApplyById(int id){
        return this.appliesRepository.findById(id);
    }
    public HashMap<Campaign, AppliesStatus> getBluesAppliedCampaigns(){
        Employee thisEmployee = this.userDataService.getLoggedEmployee();
        HashMap<Campaign, AppliesStatus> appliedCampaigns = new HashMap<>();


        List<Applies> appliesModels = this.appliesRepository.findAllByEmployeeId(thisEmployee.getId());
        for(Applies apply : appliesModels){
            if(apply.getStatus().equals(AppliesStatus.INTERVIEW_APPROVED)){
                appliedCampaigns.put(apply.getCampaign(), apply.getStatus());
            }
        }

        return appliedCampaigns;
    }
    public List<Applies> getAppliesByCampaign(Campaign campaign){
        return this.appliesRepository.findAllByCampaignId(campaign.getId());
    }
    public void deleteApply(Applies apply){
        this.appliesRepository.delete(apply);
    }
    public void setApplieStatus(int id, AppliesStatus status){
        Optional<Applies> thisApplieModel = this.appliesRepository.findById(id);
        if(thisApplieModel.isPresent()){
            Applies thisApplie = thisApplieModel.get();
            thisApplie.setStatus(status);
            this.appliesRepository.save(thisApplie);
        }

    }
}
