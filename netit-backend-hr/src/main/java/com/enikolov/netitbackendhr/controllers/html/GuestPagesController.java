package com.enikolov.netitbackendhr.controllers.html;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class GuestPagesController {

    @GetMapping("/")
    public String getIndexPage(){

        return "main/index";
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
