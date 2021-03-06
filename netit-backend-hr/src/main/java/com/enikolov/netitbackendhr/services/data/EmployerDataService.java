package com.enikolov.netitbackendhr.services.data;

import java.util.List;
import java.util.Optional;

import javax.persistence.Access;

import com.enikolov.netitbackendhr.models.users.Employee;
import com.enikolov.netitbackendhr.models.users.Employer;
import com.enikolov.netitbackendhr.models.users.User;
import com.enikolov.netitbackendhr.repositories.users.EmployerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployerDataService {

    @Autowired
    private UserDataService userDataService;
    @Autowired
    private EmployerRepository employerRepository;

    public List<Employer> getEmployerSelectionList(){

        List<Employer> allEmployers = this.employerRepository.findAll();

        return allEmployers;
        
    }
    public void saveEmployer(Employer employer){
        User user = this.userDataService.getLoggedUser();

        employer.setUser(user);

        this.employerRepository.save(employer);
    }
    
}
