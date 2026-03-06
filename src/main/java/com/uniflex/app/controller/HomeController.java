package com.uniflex.app.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Authentication auth) {
        if (auth == null) return "redirect:/login";

        var roles = AuthorityUtils.authorityListToSet(auth.getAuthorities());

        if (roles.contains("ROLE_ADMIN"))   return "redirect:/admin/dashboard";
        if (roles.contains("ROLE_TEACHER")) return "redirect:/teacher/dashboard";
        if (roles.contains("ROLE_STUDENT")) return "redirect:/student/dashboard";

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}