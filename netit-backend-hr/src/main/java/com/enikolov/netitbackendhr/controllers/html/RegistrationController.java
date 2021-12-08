package com.enikolov.netitbackendhr.controllers.html;

import java.util.HashMap;

import com.enikolov.netitbackendhr.models.users.Employee;
import com.enikolov.netitbackendhr.models.users.Employer;
import com.enikolov.netitbackendhr.models.users.User;
import com.enikolov.netitbackendhr.services.data.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class RegistrationController {

    @Autowired
    private UserDataService userDataService;
    @Autowired
    private CityDataService cityDataService;
    @Autowired
    private CategoryDataService categoryDataService;
    @Autowired
    private EmployeeDataService employeeDataService;
    @Autowired
    private EmployerDataService employerDataService;


    @GetMapping("/register")
    public String registerUser(Model model){

        HashMap<String, String> selectAccountType = getAccountTypes();

        model.addAttribute("selectAccountType"  , selectAccountType);
        model.addAttribute("user", new User());
        return "auth/register";

    }
    @GetMapping("/finish-register")
    public String getFinishRegisterPage(Model model){
        User loggedUser = userDataService.getLoggedUser();

        model.addAttribute("cities",        this.cityDataService.getAllCities());
        model.addAttribute("categories",    this.categoryDataService.getAllCategories());
        model.addAttribute("username",      loggedUser.getFullname());
        model.addAttribute("employee",      new Employee());
        model.addAttribute("employer",      new Employer());
        model.addAttribute("user",          loggedUser);
        return "auth/finish-register";
    }
    @PostMapping("/finish-register")
    public RedirectView finishRegister(@ModelAttribute Employee employee, Employer employer){
        User user = this.userDataService.getLoggedUser();

        try {

            if(user.getUserRole().equals("EMPLOYER")){
                this.employerDataService.saveEmployer(employer);
            }
            if(user.getUserRole().equals("EMPLOYEE")){
                this.employeeDataService.saveEmployee(employee);
            }
            return new RedirectView("/user-dispatch");

        }catch (Exception e){
            return new RedirectView("/user-dispatch");
        }
    }
    @PostMapping("/register")
    public RedirectView registerUser(@ModelAttribute User user, Model model){
        
        // User registeredUser = null;

        if(!checkForIncorrectInputs(user)){
            return new RedirectView("/register");
        }
        else{
            this.userDataService.saveNewUser(user);
        }
        return new RedirectView("/login");
    }

    private HashMap<String, String> getAccountTypes(){
        HashMap<String, String> typesHashMap = new HashMap<>();

        typesHashMap.put("EMPLOYEE", "Im searching for job");
        typesHashMap.put("EMPLOYER", "Im offer job");
        typesHashMap.put("HR", "Im HR agent");

        return typesHashMap;

    }

    private boolean checkForIncorrectInputs(User user){

        if(user.getPassword().isEmpty()){
            return false;
        }
        if(user.getConfirmPassword().isEmpty()){
            return false;
        }
        if(user.getEmail().isEmpty()){
            return false;
        }
        if(user.getUserRole() == "select"){
            return false;
        }

        if(!(user.getPassword().equals(user.getConfirmPassword()) )){
            return false;
        }

        return true;

    }
    
}