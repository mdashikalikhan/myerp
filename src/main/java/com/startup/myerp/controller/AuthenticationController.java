package com.startup.myerp.controller;

import com.startup.myerp.repository.ErpUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
public class AuthenticationController {

    private final ErpUserRepository erpUserRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    public String registerForm(){

    }
}
