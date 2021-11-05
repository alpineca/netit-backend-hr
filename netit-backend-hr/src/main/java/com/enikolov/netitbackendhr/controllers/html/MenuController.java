package com.enikolov.netitbackendhr.controllers.html;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {

    @GetMapping("/")
    public String getIndexPage(){
        return "index";
    }

    @GetMapping("/test")
    public String getTestPage(){
        return "test-it";
    }


}
