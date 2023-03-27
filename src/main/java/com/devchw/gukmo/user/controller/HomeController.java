package com.devchw.gukmo.user.controller;

import javax.servlet.http.HttpServletRequest;

import com.devchw.gukmo.entity.member.Member;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HomeController {

	@GetMapping(value = {"/index", "/"})
	public String hello(@ModelAttribute Member member) {
		return "index.tiles1";
	}
}