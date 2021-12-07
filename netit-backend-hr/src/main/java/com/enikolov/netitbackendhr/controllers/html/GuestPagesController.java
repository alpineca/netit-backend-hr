package com.enikolov.netitbackendhr.controllers.html;

import com.enikolov.netitbackendhr.services.data.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Controller
public class GuestPagesController {
    @Autowired
    private UserDataService userDataService;

    @GetMapping("/")
    public RedirectView getIndexPage(){

        if(userDataService.isUserLogged()){
            return new RedirectView("/campaigns/show-all");
        }

        return new RedirectView("/login");
    }

    @GetMapping("/test")
    public String getTestPage(){
        return "test-it";
    }

    @GetMapping("/error")
    public String getErrorPage(){
        return "main/index";
    }



}
