package com.volkov.spring.security.controllers;

import com.volkov.spring.security.entities.User;
import com.volkov.spring.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MainController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String homePage(){
        return "home";
    }

    @GetMapping("/authenticated")
    public String pageForAuthenticatedUsers(Principal principal){
        User user = userService.findByUserName(principal.getName());

        return "secured part of web service " + user.getEmail();
    }

    @GetMapping("/read_profile")
    public String pageForReadProfile(){
        return "read profile page" ;
    }

    @GetMapping("/only_for_admins")
    public String pageOnlyForAdmins(){
        return "admins page" ;
    }
}
