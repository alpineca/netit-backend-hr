package com.enikolov.netitbackendhr.controllers.html;

import com.enikolov.netitbackendhr.models.users.Employee;
import com.enikolov.netitbackendhr.models.users.Employer;
import com.enikolov.netitbackendhr.models.users.User;
import com.enikolov.netitbackendhr.repositories.users.EmployerRepository;
import com.enikolov.netitbackendhr.services.data.CategoryDataService;
import com.enikolov.netitbackendhr.services.data.CityDataService;
import com.enikolov.netitbackendhr.services.data.UserDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;


@Controller
public class DashboardController {

    @Autowired
    private UserDataService logUser;

    @Autowired
    private EmployerRepository employerRepository;
    @Autowired
    private CityDataService cityDataService;
    @Autowired
    private CategoryDataService categoryDataService;

    @GetMapping("/employer-dashboard")
    public RedirectView getDashboardPage(Model model){
        User user = logUser.getLoggedUser();
        
        if(!logUser.isRegistrationDone(user)){
            return new RedirectView("/employer-register");
        }

        model.addAttribute("user", user);

        return new RedirectView("main/employer-dashboard");
    }

    @GetMapping("/employee-dashboard")
    public String getEmployeeDashboardPage(Model model){
        User user = logUser.getLoggedUser();
        model.addAttribute("user", user);

        if(!logUser.isRegistrationDone(user)){

            model.addAttribute("username", user.getUsername());
            model.addAttribute("employee", new Employee());
            model.addAttribute("cities", this.cityDataService.getAllCities());

            return "auth/employee-register";
        }

        return "main/employee-dashboard";
    }
    
}
