package com.startup.myerp.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashBoardController {

    @GetMapping("/admin/dashboard")
    public String adminDashBoard(Model model,
                                 @AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("role", "ADMIN");
        return "dashboard/admin-dashboard";
    }

    @GetMapping("/user/dashboard")
    public String userDashBoard(Model model,
                                 @AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("role", "USER");
        return "dashboard/dashboard";
    }

    @GetMapping("/")
    public String home(Model model,
                                @AuthenticationPrincipal UserDetails userDetails){
        if(userDetails!=null) {
            model.addAttribute("username", userDetails.getUsername());
        }
        return "index";
    }
}
