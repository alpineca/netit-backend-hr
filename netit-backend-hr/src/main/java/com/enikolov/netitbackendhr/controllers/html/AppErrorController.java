package com.enikolov.netitbackendhr.controllers.html;

import com.enikolov.netitbackendhr.services.data.UserDataService;
import org.dom4j.util.UserDataAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class AppErrorController implements ErrorController {

    @Autowired
    private UserDataService userDataService;

    @RequestMapping("/error")
    public RedirectView handleError(){
        if(userDataService.isUserLogged()){
            System.out.println("***************");
            System.out.println("some error");
            return new RedirectView("/user-dispatch");
        }

        return new RedirectView("/login");
    }

}
