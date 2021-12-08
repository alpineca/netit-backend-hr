package com.enikolov.netitbackendhr.controllers.html;

import com.enikolov.netitbackendhr.models.users.User;
import com.enikolov.netitbackendhr.services.data.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class DispatchController {

    @Autowired
    private UserDataService userDataService;

    @GetMapping("/user-dispatch")
    public RedirectView userDispatch(){

        User user = userDataService.getLoggedUser();
        String userRole = user.getUserRole();

        if(!this.userDataService.isRegistrationDone()){
            return new RedirectView("/finish-register");
        }
        if(userRole.equals("EMPLOYER")){
            return new RedirectView("/campaigns/show-all");
        }
        if(userRole.equals("HR") || userRole.equals("EMPLOYEE")){
            return new RedirectView("/applies/show-all");
        }

        return new RedirectView("/logout");
    }

}
