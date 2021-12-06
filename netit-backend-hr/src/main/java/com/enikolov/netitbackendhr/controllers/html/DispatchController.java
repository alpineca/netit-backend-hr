package com.enikolov.netitbackendhr.controllers.html;

import com.enikolov.netitbackendhr.models.users.Employee;
import com.enikolov.netitbackendhr.models.users.Employer;
import com.enikolov.netitbackendhr.models.users.User;
import com.enikolov.netitbackendhr.repositories.users.EmployeeRepository;
import com.enikolov.netitbackendhr.repositories.users.EmployerRepository;
import com.enikolov.netitbackendhr.repositories.users.UserRepository;
import com.enikolov.netitbackendhr.services.data.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Controller
public class DispatchController {

    @Autowired
    private UserDataService userData;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployerRepository employerRepository;


    @GetMapping("/user-dispatch")
    public RedirectView userDispatch(){

        User user = userData.getLoggedUser();
        String userRole = user.getUserRole();

        if(userRole.equals("EMPLOYER") && isRegistrationDone(user)){
            return new RedirectView("employer-dashboard");
        }

        if(userRole.equals("EMPLOYER") && !isRegistrationDone(user)){
            return new RedirectView("employer-register");
        }

        if(userRole.equals("EMPLOYEE") && isRegistrationDone(user)){
            return new RedirectView("applies/show-all");
        }
        if(userRole.equals("EMPLOYEE") && !isRegistrationDone(user)){
            return new RedirectView("employee-dashboard");
        }

//        if(userRole.equals("HR") && isRegistrationDone(user)){
//            return new RedirectView("hr-dashboard");
//        }

        return new RedirectView("logout");
    }

    private boolean isRegistrationDone(User user){

        Optional<Employer> employerFill = this.employerRepository.findEmployerByUserId(user.getId());
        Optional<Employee> employeeFill = this.employeeRepository.findEmployeeByUserId(user.getId());
        String userRole = user.getUserRole();

        if(userRole.equals("EMPLOYER") && employerFill.isPresent()){
            return true;
        }

        if(userRole.equals("EMPLOYEE") && employeeFill.isPresent()){
            return true;
        }

        return false;
    }

}
