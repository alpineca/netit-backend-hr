package com.enikolov.netitbackendhr.repositories.users;

import java.util.Optional;

import com.enikolov.netitbackendhr.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    public Optional<User> findUserByUsernameAndPassword(String username, String password);

    public Optional<User> findUserByUsername(String username);

}
