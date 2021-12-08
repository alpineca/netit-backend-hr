package com.enikolov.netitbackendhr.services.data;

import com.enikolov.netitbackendhr.models.users.Employee;
import com.enikolov.netitbackendhr.models.users.User;
import com.enikolov.netitbackendhr.repositories.users.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EmployeeDataService {
    @Autowired
    private UserDataService userDataService;
    @Autowired
    private EmployeeRepository employeeRepository;

    public void saveEmployee(Employee employee){
        User user = this.userDataService.getLoggedUser();

        employee.setUser(user);

        this.employeeRepository.save(employee);
    }

}
