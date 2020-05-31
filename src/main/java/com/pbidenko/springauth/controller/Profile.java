package com.pbidenko.springauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pbidenko.springauth.entity.Usr;
import com.pbidenko.springauth.repository.UsrRepo;

@Controller
public class Profile {
	
	@Autowired
	UsrRepo usrRepo;
	
	@GetMapping("/my_profile")
	public String profile(Model model)
	{
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = "";
		
		if (principal instanceof UserDetails) {
		username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		
		model.addAttribute("username", username);
		
		return "profile";
	}
}
