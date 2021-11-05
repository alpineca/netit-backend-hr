package com.enikolov.netitbackendhr.controllers.html;

import com.enikolov.netitbackendhr.models.DTO.UserDTO;
import com.enikolov.netitbackendhr.models.users.User;
import com.enikolov.netitbackendhr.repositories.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/register")
    public String getRegisterPage(Model model){
        UserDTO entity = new UserDTO();
        int password2 = 0;
        model.addAttribute("user", entity);
        model.addAttribute("password2", password2);
        return "register";
    }

    @PostMapping("/register/dto")
    public RedirectView createUser(@ModelAttribute UserDTO entityDTO){
        User entity = entityDTO.createUserEntity();

        if(entityDTO.getPassword().equals(entityDTO.getMatchingPassword())){
            this.userRepository.save(entity);
            return new RedirectView("/login");
        }
        else{
            return new RedirectView("/register");
        }

    }

    @GetMapping("/show/{id}")
    public String getUser(){
        return null;
    }

    @PutMapping("/update/{id}")
    public RedirectView updateUser(){
        return null;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable int id){
        Optional<User> httpResult = this.userRepository.findById(id);

        if(httpResult.isPresent()) {
            this.userRepository.deleteById(id);
//            return new RedirectView("/login");

            return "login";
        }

//        return new RedirectView("/error");
        return "error";
    }

}
