package com.enikolov.netitbackendhr.controllers.html;

import java.util.Optional;

import com.enikolov.netitbackendhr.models.users.Employer;
import com.enikolov.netitbackendhr.models.users.User;
import com.enikolov.netitbackendhr.repositories.users.EmployerRepository;
import com.enikolov.netitbackendhr.services.data.UserData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class DashboardController {

    @Autowired
    private UserData logUser;

    @Autowired
    private EmployerRepository employerRepository;

    @GetMapping("/dashboard")
    public String getDashboardPage(Model model){
        User user = logUser.getLoggedUser();
        
        if(!checkForFullRegisteredEmployer(user)){

            model.addAttribute("username", user.getUsername());
            model.addAttribute("employer", new Employer());
            return "auth/employer-register";
        }

        model.addAttribute("loggedUser", user);

        return "main/dashboard";
    }

    private boolean checkForFullRegisteredEmployer(User user){

        Optional<Employer> employerFill = this.employerRepository.findEmployerByUserId(user.getId());

        if(employerFill.isPresent()){
            return true;
        }

        return false;
    }
    
}
