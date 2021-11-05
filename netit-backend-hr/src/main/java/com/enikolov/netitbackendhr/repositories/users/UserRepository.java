package com.enikolov.netitbackendhr.repositories.users;

import com.enikolov.netitbackendhr.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
