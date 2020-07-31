package com.pbidenko.springauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pbidenko.springauth.entity.Usr;
import com.pbidenko.springauth.repository.UsrRepo;
import com.pbidenko.springauth.service.ArticleStorageService;

@Controller
public class MainController {

	@Autowired
	UsrRepo usrRepo;
	
	@Autowired
	ArticleStorageService articleStorageService;

	@GetMapping
	public String index(@RequestParam(required = false, name = "name", defaultValue = "User") String name,
			Model model) {
		
		String username = getAuthorizedUser();
	
		model.addAttribute("username",username);
		model.addAttribute("activeArticleList", articleStorageService.getActiveArticleList());		

		return "index";
	}

	private String getAuthorizedUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		String username = "";
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		return username;
	}

}