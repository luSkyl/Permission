package com.java.lcy.Permission.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping("/index")
    public ModelAndView index(){
        return new ModelAndView("admin");
    }
}
