package com.enikolov.netitbackendhr.controllers.html;

import java.util.Optional;

import com.enikolov.netitbackendhr.models.users.User;
import com.enikolov.netitbackendhr.repositories.users.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String getLoginPage(Model model){


        model.addAttribute("user", new User());
        return "auth/login";
    }

    @PostMapping("/login")
    public String loginProcess(@ModelAttribute User user, Model model){

        Optional<User> findUser = this.userRepository.findUserByUsernameAndPassword(user.getUsername(), user.getPassword());

        if(findUser.isPresent()){

            // model.addAttribute("username", user.getUsername());
            return "main/dashboard";
        }

        return "auth/login";
    }
}
