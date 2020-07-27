package com.pbidenko.springauth.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pbidenko.springauth.entity.Usr;
import com.pbidenko.springauth.entity.UsrProfile;
import com.pbidenko.springauth.exception.ProfileNotFoundException;
import com.pbidenko.springauth.service.ProfileStorageService;
import com.pbidenko.springauth.service.UsrStorageService;

@Controller
public class ProfileController {

	@Autowired
	ProfileStorageService profileStorageService;

	@Autowired
	UsrStorageService usrStorageService;

	@GetMapping("/my_profile")
	public String profile(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = "";

		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}

		Usr usr = usrStorageService.findByEmail(username);
		model.addAttribute("username", username);
		model.addAttribute("usr", usr);

		try {
			UsrProfile profile = profileStorageService.findByUsr(usr);
			model.addAttribute("profile", profile);

		} catch (ProfileNotFoundException e) {
			model.addAttribute("profile", null);
			e.printStackTrace();
		}

		return "profile";
	}
	
	@PostMapping("/saveProfile/{id}")
	public ModelAndView saveProfile(UsrProfile profile, @RequestParam String profdescription, @RequestParam String project) {
		
		return new ModelAndView("redirect:/my_profile");
	}

	@PostMapping("/updateProfile/{id}")
	@ResponseBody
	public Map<String, Object> updateProfile(UsrProfile profile, @PathVariable String id) {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("status", null);

		Usr usr = usrStorageService.findById(id);

		try {
			UsrProfile profileExists = profileStorageService.findByUsr(usr);
			
			profileExists.setName(profile.getName());
			profileExists.setSurname(profile.getSurname());
			profileExists.setAge(profile.getAge());
			profileExists.setCountry(profile.getCountry());
			profileExists.setProfessionSet(profile.getProfessionSet());
			profileExists.setNationality(profile.getNationality());
					
			profileStorageService.save(profileExists);	
			response.compute("status", (k, v) -> v = "edit");
			
		} catch (ProfileNotFoundException e) {
			profile.setAuthUser(usr);
			profileStorageService.save(profile);
			response.compute("status", (k, v) -> v = "add");
		}

		return response;

	}

	@PostMapping("/loadImage/{id}")
	@ResponseBody
	Map<String, Object> loadImage(@RequestParam("image") String imageString, @PathVariable int id) {
		Map<String, Object> response = new HashMap<String, Object>();

		try {
			String success = profileStorageService.saveImage(imageString, id);
			response.put("status", success);
		} catch (Exception e) {

			response.computeIfPresent("status", (k, v) -> v = "error");
		}

		return response;
	}

}
