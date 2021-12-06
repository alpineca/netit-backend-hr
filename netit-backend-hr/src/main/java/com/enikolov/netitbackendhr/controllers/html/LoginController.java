package com.enikolov.netitbackendhr.controllers.html;

import java.util.Optional;

import com.enikolov.netitbackendhr.components.InfoMessage;
import com.enikolov.netitbackendhr.enums.MessageStyle;
import com.enikolov.netitbackendhr.models.users.User;
import com.enikolov.netitbackendhr.repositories.users.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String getLoginPage(Model model){


        // if(message != null){
        //     model.addAttribute("message", message);
        // }

        InfoMessage message = new InfoMessage();
        message.setMessage("Username or password not valid!");
        message.setStyle(MessageStyle.ERROR_MSG);

        model.addAttribute("user", new User());
        model.addAttribute("message", message);
        return "auth/login";
    }

    @PostMapping("/login")
    public RedirectView loginProcess(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes){

        Optional<User> findUser = this.userRepository.findUserByUsernameAndPassword(user.getUsername(), user.getPassword());

        if(findUser.isPresent()){

            User thisUser = findUser.get();

            if(thisUser.getUserRole().equals("EMPLOYER")){
                return new RedirectView("employer-dashboard");
            }
            if(thisUser.getUserRole().equals("EMPLOYEE")){
                return new RedirectView("employee-dashboard");
            }
            if(thisUser.getUserRole().equals("HR")){
                return new RedirectView("hr-dashboard");
            }
            if(thisUser.getUserRole().equals("SUPER")){
                return new RedirectView("super-dashboard");
            }
        }
        InfoMessage message = new InfoMessage();
        message.setMessage("Username or password not valid!");
        message.setStyle(MessageStyle.ERROR_MSG);


        redirectAttributes.addFlashAttribute(message);
        return new RedirectView("login");
    }
}
