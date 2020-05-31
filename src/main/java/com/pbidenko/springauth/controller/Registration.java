package com.pbidenko.springauth.controller;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.pbidenko.springauth.entity.Role;
import com.pbidenko.springauth.entity.Usr;
import com.pbidenko.springauth.repository.UsrRepo;

@Controller
public class Registration {

	@Autowired
	UsrRepo userRepo;

	@GetMapping("/registration")
	public String registation() {
		return "registration";
	}

	@PostMapping("registration")
	public String addUser(Usr newUsr, Model model) {

		System.out.println("Name " + newUsr.getFirstname());

		newUsr.setRolesSet(new HashSet<Role>() {
			{
				add(Role.USER);
				add(Role.PROF);
			}
		});
		userRepo.save(newUsr);
		return "redirect:index";
	}

}
