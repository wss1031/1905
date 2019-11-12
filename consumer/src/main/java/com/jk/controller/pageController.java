package com.jk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("page")
public class pageController {

    @RequestMapping("toindex")
    public String toindex(){
        return "index";
    }

    @RequestMapping("toshow")
    public String toshow(){
        return "show";
    }

    @RequestMapping("tomain")
    public String tomain(){
        return "main";
    }
    @RequestMapping("tologin")
    public String tologin(){
        return "login";
    }


}
