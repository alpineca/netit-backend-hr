package com.enikolov.netitbackendhr.services.data;

import com.enikolov.netitbackendhr.models.users.Employee;
import com.enikolov.netitbackendhr.models.users.Employer;
import com.enikolov.netitbackendhr.models.users.User;
import com.enikolov.netitbackendhr.repositories.users.EmployeeRepository;
import com.enikolov.netitbackendhr.repositories.users.EmployerRepository;
import com.enikolov.netitbackendhr.repositories.users.UserRepository;
import com.enikolov.netitbackendhr.services.auth.UserLogin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDataService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmployerRepository employerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public boolean isUserLogged(){
        try{
            this.getLoggedUser();
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
    public void saveNewUser(User user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        this.userRepository.save(user);
    }
    public Optional<User> isUserAuthenticated(User tryLogin){

        return this.userRepository.findUserByEmailAndPassword(tryLogin.getEmail(), tryLogin.getPassword());

    }
    public User getLoggedUser(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserLogin userLoginModel = (UserLogin) auth.getPrincipal();
        

        return userLoginModel.getUser();
    }

    public Employer getLoggedEmployer(){
        int userId =  this.getLoggedUser().getId();

        Optional<Employer> employerModel = this.employerRepository.findEmployerByUserId(userId);

        if(employerModel.isPresent()){
            return employerModel.get();
        }

        return null;
    }

    public Employee getLoggedEmployee(){
        int userId =  this.getLoggedUser().getId();

        Optional<Employee> employeeModel = this.employeeRepository.findEmployeeByUserId(userId);

        if(employeeModel.isPresent()){
            return employeeModel.get();
        }

        return null;
    }

    public boolean isRegistrationDone(){
        User user = this.getLoggedUser();

        Optional<Employer> employerFill = this.employerRepository.findEmployerByUserId(user.getId());
        Optional<Employee> employeeFill = this.employeeRepository.findEmployeeByUserId(user.getId());
        String userRole = user.getUserRole();

        if(userRole.equals("EMPLOYER") && employerFill.isPresent()){
            return true;
        }

        if(userRole.equals("EMPLOYEE") && employeeFill.isPresent()){
            return true;
        }
        if(userRole.equals("HR")){
            return true;
        }

        return false;
    }
    
}
