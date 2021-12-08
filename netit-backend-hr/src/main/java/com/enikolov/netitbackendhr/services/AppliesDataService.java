package com.enikolov.netitbackendhr.services;

import com.enikolov.netitbackendhr.components.SystemClock;
import com.enikolov.netitbackendhr.enums.AppliesStatus;
import com.enikolov.netitbackendhr.models.general.Applies;
import com.enikolov.netitbackendhr.models.general.Campaign;
import com.enikolov.netitbackendhr.models.users.Employee;
import com.enikolov.netitbackendhr.repositories.general.AppliesRepository;
import com.enikolov.netitbackendhr.repositories.general.CampaignRepository;
import com.enikolov.netitbackendhr.repositories.users.EmployeeRepository;
import com.enikolov.netitbackendhr.repositories.users.UserRepository;
import com.enikolov.netitbackendhr.services.data.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component
public class AppliesDataService {
    @Autowired
    private SystemClock systemClock;
    @Autowired
    private UserDataService userData;
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

    public HashMap<Campaign, AppliesStatus> getAppliedCampaigns(){
        Employee thisEmployee = this.userData.getLoggedEmployee();
        HashMap<Campaign, AppliesStatus> appliedCampaigns = new HashMap<>();


        List<Applies> appliesModels = this.appliesRepository.findAllByEmployeeId(thisEmployee.getId());
        for(Applies apply : appliesModels){
            appliedCampaigns.put(apply.getCampaign(), apply.getStatus());
        }

        return appliedCampaigns;
    }
    public HashMap<Campaign, AppliesStatus> gettAllApplies(){
        HashMap<Campaign, AppliesStatus> appliedCampaigns = new HashMap<>();


        List<Applies> appliesModels = this.appliesRepository.findAll();
        for(Applies apply : appliesModels){
            appliedCampaigns.put(apply.getCampaign(), apply.getStatus());
        }

        return appliedCampaigns;
    }
    public Optional<Applies> getApplyById(int id){
        return this.appliesRepository.findById(id);
    }
    public HashMap<Campaign, AppliesStatus> getBluesAppliedCampaigns(){
        Employee thisEmployee = this.userData.getLoggedEmployee();
        HashMap<Campaign, AppliesStatus> appliedCampaigns = new HashMap<>();


        List<Applies> appliesModels = this.appliesRepository.findAllByEmployeeId(thisEmployee.getId());
        for(Applies apply : appliesModels){
            if(apply.getStatus().equals(AppliesStatus.INTERVIEW_APPROVED)){
                appliedCampaigns.put(apply.getCampaign(), apply.getStatus());
            }
        }

        return appliedCampaigns;
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
