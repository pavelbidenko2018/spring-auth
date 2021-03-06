package com.pbidenko.springauth.controller;

import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pbidenko.springauth.entity.Role;
import com.pbidenko.springauth.entity.Usr;
import com.pbidenko.springauth.repository.UsrRepo;

@Controller
public class Registration {

	@Autowired
	UsrRepo userRepo;
	
	@Autowired
	BCryptPasswordEncoder encoder;

	@GetMapping("/registration")
	public String registation() {
		return "registration";
	}

	@PostMapping("/addUser")
	public String addUser(@RequestParam String username, @RequestParam String email, @RequestParam String pwd, @RequestParam String repwd, Model model) {
		
		if(!pwd.equals(repwd)) {
			return "registration";
		}
		
		Usr newUsr = new Usr(username, email, encoder.encode(pwd));
		
		newUsr.setRolesSet(new HashSet<Role>() {
			{
				add(Role.USER);
				add(Role.PROF);
			}
		});
		userRepo.save(newUsr);
		return "index";
	}

}
