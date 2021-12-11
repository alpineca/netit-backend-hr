package com.enikolov.netitbackendhr.services.data;

import java.util.List;
import java.util.Optional;

import com.enikolov.netitbackendhr.components.SystemClock;
import com.enikolov.netitbackendhr.models.general.Applies;
import com.enikolov.netitbackendhr.models.general.Campaign;
import com.enikolov.netitbackendhr.repositories.general.AppliesRepository;
import com.enikolov.netitbackendhr.repositories.general.CampaignRepository;
import com.enikolov.netitbackendhr.repositories.users.EmployeeRepository;
import com.enikolov.netitbackendhr.repositories.users.EmployerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CampaignsDataService {

    @Autowired
    private SystemClock systemClock;
    @Autowired
    private UserDataService userDataService;
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
    public Optional<Campaign> getById(int id){
        return this.campaignRepository.findById(id);
    }
    public Optional<Campaign> getMyCampaign(int id){
        return this.campaignRepository.getMyCampaign(id, this.userDataService.getLoggedUser().getId());
    }
    public List<Campaign> getAll(){
        return this.campaignRepository.findAll();

    }
    public List<Campaign> getAllByThisEmployer(){

        return this.campaignRepository.findAllByEmployerId(this.userDataService.getLoggedEmployer().getId());
    }
    public void updateThisCampaign(Campaign entityToUpdate, Campaign entityNewData){
        entityToUpdate.setTitle(entityNewData.getTitle());
        entityToUpdate.setDescription(entityNewData.getDescription());
        entityToUpdate.setSalaryMin(entityNewData.getSalaryMin());
        entityToUpdate.setSalaryMax(entityNewData.getSalaryMax());

        this.campaignRepository.save(entityToUpdate);
    }
    public void deleteCampaign(Campaign entity){
        List<Applies> appliesByCampaign = this.appliesDataService.getAppliesByCampaign(entity);
        if(appliesByCampaign.isEmpty()){
            this.campaignRepository.delete(entity);
            return;
        }else{
            for(Applies apply : appliesByCampaign){
                this.appliesDataService.deleteApply(apply);
            }
            this.campaignRepository.delete(entity);
        }
    }
    public void createNewCampaign(Campaign newCampaign){
        String date             = this.systemClock.getSystemDate();
        String time             = this.systemClock.getSystemTime();

        newCampaign.setPublishDate(date);
        newCampaign.setPublishTime(time);
        newCampaign.setEmployer(this.userDataService.getLoggedEmployer());

        this.campaignRepository.save(newCampaign);
    }

//    private List<Campaign> setEmployerTitle(List<Campaign> campaignList){
//        for(Campaign element : campaignList){
//            element.setEmployerTitle(getEmployerTitle(element.getEmployerId()));
//        }
//
//        return campaignList;
//
//    }
//    private String getEmployerTitle(int employerId){
//        Optional<Employer> employerModel = this.employerRepository.findById(employerId);
//
//        if(!employerModel.isPresent()){
////            Employer employerEntity = employerModel.get();
//            return "No available";
//        }
//
//        Employer employerEntity = employerModel.get();
//
//        return employerEntity.getCompanyName();
//
//    }
    
}
