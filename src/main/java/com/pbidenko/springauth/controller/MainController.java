package com.pbidenko.springauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

		model.addAttribute("activeArticleList", articleStorageService.getActiveArticleList());
		

		return "index";
	}

}