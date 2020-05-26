package com.pbidenko.springauth.controller;


import java.util.HashSet;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.pbidenko.springauth.repositories.UserRepo;

import com.pbidenko.springauth.entity.Roles;
import com.pbidenko.springauth.entity.User;



@Controller
public class MainController {

	@Autowired
	UserRepo userRepo;

	@GetMapping
	public String index(@RequestParam(required = false, name = "name", defaultValue = "User") String name,
			Model model) {
		model.addAttribute("name", name);
		
		Set<Roles> roles = new HashSet<Roles>();
		roles.add(new Roles("ADMIN"));
		
		User user = new User(name, roles);
		
		userRepo.save(user);
		
		return "index";
	}

}
