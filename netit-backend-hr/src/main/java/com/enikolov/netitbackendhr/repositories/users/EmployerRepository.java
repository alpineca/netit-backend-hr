package com.enikolov.netitbackendhr.repositories.users;

import java.util.Optional;

import com.enikolov.netitbackendhr.models.general.Employer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployerRepository extends JpaRepository<Employer, Integer> {

    public Optional<Employer> findEmployerByUserId(int id);
}
