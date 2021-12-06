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
    public String getDashboardPage(Model model){
        User user = logUser.getLoggedUser();
        
        if(!logUser.isRegistrationDone(user)){

            model.addAttribute("cities", this.cityDataService.getAllCities());
            model.addAttribute("categories", this.categoryDataService.getAllCategories());
            model.addAttribute("username", user.getUsername());
            model.addAttribute("employer", new Employer());
            return "auth/employer-register";
        }

        model.addAttribute("user", user);

        return "main/employer-dashboard";
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
