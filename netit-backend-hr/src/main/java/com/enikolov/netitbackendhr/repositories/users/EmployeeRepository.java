package com.enikolov.netitbackendhr.repositories.users;

import com.enikolov.netitbackendhr.models.users.Employee;
import com.enikolov.netitbackendhr.models.users.Employer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    public Optional<Employee> findEmployeeByUserId(int id);
}
