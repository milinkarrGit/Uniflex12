
package com.uniflex.app.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "App Running";
    }


    @GetMapping("/")
    @ResponseBody
    public String test() {
        return "ok test";
    }
}