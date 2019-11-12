package com.jk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("page")
@Controller
public class pageController {


    @RequestMapping("toindex")
    public String toindex(){
        return "index";
    }
}
