package com.pbidenko.springauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbidenko.springauth.repository.UsrRepo;
import com.pbidenko.springauth.service.ArticleStorageService;
import com.pbidenko.springauth.service.EmailService;

@Controller
public class MainController {

	@Autowired
	UsrRepo usrRepo;
	
	@Autowired
	ArticleStorageService articleStorageService;
	
	@Autowired 
	EmailService emailService;

	@GetMapping
	public String index(@RequestParam(required = false, name = "name", defaultValue = "User") String name,
			Model model) {
		
		String username = getAuthorizedUser();
	
		model.addAttribute("username",username);
		model.addAttribute("activeArticleList", articleStorageService.getActiveArticleList());		

		return "index";
	}
	
	@PostMapping("/sendEmail")
	@ResponseBody
	public String sendEmail(@RequestParam String userEmail, @RequestParam String message) {
		emailService.sendMail(userEmail, "test", message);
		return "OK";
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