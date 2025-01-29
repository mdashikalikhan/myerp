package com.startup.myerp.controller;

import com.startup.myerp.entity.ErpUser;
import com.startup.myerp.repository.ErpUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class AuthenticationController {

    private final ErpUserRepository erpUserRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/login")
    public String loginPage(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session.getAttribute("logout")!=null){
            session.invalidate();
            request.setAttribute("logout", "logout");
        } else if(session.getAttribute("error")!=null){
            session.invalidate();
            request.setAttribute("error", "error");
        } else if(session.getAttribute("registered")!=null){
            session.invalidate();
            request.setAttribute("registered", "registered");
        }
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("user", new ErpUser());
        model.addAttribute("roles", ErpUser.Role.values());
        return "register";
    }

    @PostMapping("register")
    public String registerUser(@ModelAttribute ErpUser erpUser, HttpServletRequest request){
        erpUser.setPassword(bCryptPasswordEncoder.encode(erpUser.getPassword()));
        erpUserRepository.save(erpUser);
        request.getSession(false).setAttribute("registered", "registered");
        return "redirect:/login";
    }
}
