package com.enikolov.netitbackendhr.controllers.html;

import java.util.HashMap;

import com.enikolov.netitbackendhr.models.DTO.UserDTO;
import com.enikolov.netitbackendhr.models.users.Employee;
import com.enikolov.netitbackendhr.models.users.Employer;
import com.enikolov.netitbackendhr.models.users.User;
import com.enikolov.netitbackendhr.repositories.users.EmployeeRepository;
import com.enikolov.netitbackendhr.repositories.users.EmployerRepository;
import com.enikolov.netitbackendhr.repositories.users.UserRepository;
import com.enikolov.netitbackendhr.services.data.CategoryDataService;
import com.enikolov.netitbackendhr.services.data.CityDataService;
import com.enikolov.netitbackendhr.services.data.UserDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private UserRepository userRepository;

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/register")
    public String registerUser(Model model){

        HashMap<String, String> selectAccountType = getAccountTypes();

        model.addAttribute("selectAccountType"  , selectAccountType);
        model.addAttribute("userDTO", new UserDTO());
        return "auth/register";

    }
    @GetMapping("/employer-register")
    private String getEmployerRegisterPage(Model model){
        User user = this.userDataService.getLoggedUser();

        model.addAttribute("cities", this.cityDataService.getAllCities());
        model.addAttribute("categories", this.categoryDataService.getAllCategories());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("employer", new Employer());
        return "auth/employer-register";
    }

    @PostMapping("/employer-register")
    public RedirectView employerRegister(@ModelAttribute Employer employer){

        User loggedUser = userDataService.getLoggedUser();
        employer.setUserId(loggedUser.getId());

        this.employerRepository.save(employer);
        return new RedirectView("/campaigns/show-all");
    }

    @PostMapping("/employee-register")
    public RedirectView employeeRegister(@ModelAttribute Employee employee){

        User loggedUser = userDataService.getLoggedUser();
//        employee.setUserId(loggedUser.getId());
        employee.setUser(loggedUser);

        this.employeeRepository.save(employee);
        return new RedirectView("/employee-dashboard");
    }

    @PostMapping("/finish-registration")
    public String registerUser(@ModelAttribute UserDTO userDTO, Model model){
        
        // User registeredUser = null;

        if(!checkForIncorrectInputs(userDTO)){

            HashMap<String, String> selectAccountType = getAccountTypes();

            model.addAttribute("selectAccountType"  , selectAccountType);
            model.addAttribute("userDTO", new UserDTO());  
            return "auth/register";
        }
        else{
            //SAVE USER
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPassword = encoder.encode(userDTO.getPassword());
            userDTO.setPassword(encodedPassword);

            User entity = userDTO.createUserEntity();

            this.userRepository.save(entity);

        }
    
        model.addAttribute("user", new User());
        return "auth/login";
    }

    private HashMap<String, String> getAccountTypes(){
        HashMap<String, String> typesHashMap = new HashMap<>();

        typesHashMap.put("EMPLOYEE", "Im searching for job");
        typesHashMap.put("EMPLOYER", "Im offer job");
        typesHashMap.put("HR", "Im HR agent");

        return typesHashMap;

    }

    private boolean checkForIncorrectInputs(UserDTO user){

        if(user.getUsername().isEmpty()){
            return false;
        }
        if(user.getPassword().isEmpty()){
            return false;
        }
        if(user.getConfirmPassword().isEmpty()){
            return false;
        }
        if(user.getMail().isEmpty()){
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

    private boolean checkForBadInputsEmployer(Employer employer){

        


        return true;
    }
    
}