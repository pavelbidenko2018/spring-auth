package com.pbidenko.springauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pbidenko.springauth.repositories.UserRepo;

@Controller
public class MainController {

	@Autowired
	UserRepo userRepo;
	
	@GetMapping
	public String index(@RequestParam(required = false, name = "name", defaultValue = "User") String name,
			Model model) {
		model.addAttribute("name", name);
		return "index";
	}

}
