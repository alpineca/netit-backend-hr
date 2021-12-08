package com.enikolov.netitbackendhr.services.auth;

import java.util.Optional;

import com.enikolov.netitbackendhr.models.users.User;
import com.enikolov.netitbackendhr.repositories.users.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserLoginService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Optional<User> dbEntry = this.userRepository.findUserByEmail(username);

        if(dbEntry.isEmpty()){
            throw new UsernameNotFoundException("This user is not available!");
        }
        return new UserLogin(dbEntry.get());
    }


}
