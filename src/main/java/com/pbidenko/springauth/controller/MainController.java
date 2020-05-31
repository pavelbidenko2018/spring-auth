package com.pbidenko.springauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pbidenko.springauth.entity.Usr;
import com.pbidenko.springauth.repository.UsrRepo;

@Controller
public class MainController {

	@Autowired
	UsrRepo usrRepo;

	@GetMapping
	public String index(@RequestParam(required = false, name = "name", defaultValue = "User") String name,
			Model model) {

		Usr usr = usrRepo.findByEmail("foo@foo.xx").orElse(null);
		model = (usr != null) ? model.addAttribute("name", usr.getEmail()) : model.addAttribute("name", "no value");

		return "index";
	}

}