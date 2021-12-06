package com.enikolov.netitbackendhr.services.data;

import java.util.List;

import com.enikolov.netitbackendhr.components.SystemClock;
import com.enikolov.netitbackendhr.models.general.Campaign;
import com.enikolov.netitbackendhr.repositories.general.AppliesRepository;
import com.enikolov.netitbackendhr.repositories.general.CampaignRepository;
import com.enikolov.netitbackendhr.repositories.users.EmployeeRepository;
import com.enikolov.netitbackendhr.repositories.users.EmployerRepository;
import com.enikolov.netitbackendhr.services.AppliesDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CampaignsDataService {

    @Autowired
    private SystemClock systemClock;
    @Autowired
    private UserDataService userData;
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

    public List<Campaign> getFilteredByEmployerIdCampaigns(int id){

        List<Campaign> campaignsList = this.campaignRepository.findAllByEmployerId(id);

        return campaignsList;
        
    }
    
}
