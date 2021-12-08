package com.enikolov.netitbackendhr.controllers.html;

import com.enikolov.netitbackendhr.models.DTO.ChangeMailDTO;
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
//        UserDTO entity = new UserDTO();
//        int password2 = 0;
//        model.addAttribute("user", entity);
//        model.addAttribute("password2", password2);
        return "register";
    }

    @PostMapping("/register/dto")
    public RedirectView createUser(@ModelAttribute User entity){
//        User entity = entityDTO.createUserEntity();
//
//        if(entityDTO.getPassword().equals(entityDTO.getConfirmPassword())){
//            this.userRepository.save(entity);
//            return new RedirectView("/login");
//        }
//        else{
//            return new RedirectView("/register");
//        }\
        return new RedirectView("/register");

    }

    @GetMapping("/show/{id}")
    public String getUser(){
        return null;
    }

    @GetMapping("/change-email")
    public String changeEmail(Model model){

//        ChangeMailDTO changeMail = new ChangeMailDTO();
//
//        model.addAttribute("change_mail", changeMail);

        return "/change-email";
    }

    @PostMapping("/change-email/process")
    public String processChangeEmail(@ModelAttribute ChangeMailDTO changeMailDTO){
        int userId = changeMailDTO.getUserId();
        String newMail = changeMailDTO.getNewMail();
        Optional<User> httpResult = this.userRepository.findById(userId);

        if(httpResult.isPresent()){
            User entity = this.userRepository.getById(userId);
            entity.setEmail(newMail);

            this.userRepository.save(entity);

            return "success";
        }
        return "fail";
    }

    @GetMapping("/change-password")
    public RedirectView changePassword(){


        return null;
    }

    @GetMapping("/delete/{id}")
    public RedirectView deleteUser(@PathVariable("id") int id){
        Optional<User> httpResult = this.userRepository.findById(id);

        if(httpResult.isPresent()) {
            User user = this.userRepository.getById(id);
            this.userRepository.deleteById(user.getId());
            return new RedirectView("/success");

        }

        return new RedirectView("/fail");
    }

}
