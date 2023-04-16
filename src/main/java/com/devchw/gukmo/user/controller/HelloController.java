package com.devchw.gukmo.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HelloController {

    @GetMapping("/")
    public String hello(Model model) {
        return "index.tiles1";
    }
}
