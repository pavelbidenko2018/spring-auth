package com.pbidenko.springauth.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pbidenko.springauth.entity.Role;
import com.pbidenko.springauth.entity.User;
import com.pbidenko.springauth.repository.UserRepo;

@Controller
public class MainController {

	@Autowired
	UserRepo userRepo;

	@GetMapping
	public String index(@RequestParam(required = false, name = "name", defaultValue = "User") String name,
			Model model) {
		model.addAttribute("name", name);

		Set<Role> roles = new HashSet<Role>();
		roles.add(Role.ADMIN);
		roles.add(Role.PROF);

		User user = new User(name, roles);

		userRepo.save(user);

		return "index";
	}
	
//	@GetMapping("/login")
//	public String login() {
//		return "login";
//	}

}
