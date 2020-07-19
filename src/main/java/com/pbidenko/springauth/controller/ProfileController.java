package com.pbidenko.springauth.controller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
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
		try {
			UsrProfile profile = profileStorageService.findById(usr.getId());
			model.addAttribute("profile", profile);
		} catch (ProfileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		model.addAttribute("username", username);
		model.addAttribute("usr", usr);

		return "profile";
	}

	@PostMapping("/saveProfile/{id}")
	@ResponseBody
	public Map<String, Object> saveProfile(UsrProfile profile, @PathVariable String id) {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("status", "success");

		Usr usr = usrStorageService.findById(id);
		profile.setAuthUser(usr);

		try {
			profileStorageService.save(profile);
		} catch (Exception e) {

			response.computeIfPresent("status", (k, v) -> v = "failure");
		}

		return response;

	}

	// **** This POST METHOD is running by ajax request 
	@PostMapping("/loadImage/{id}")
	@ResponseBody
	Map<String, Object> loadImage(@RequestParam("image") String imageString, @PathVariable int id) {
		
		Map<String, Object> response = new HashMap<String, Object>();
		byte[] decodedBytes = null;
		
		// ***** THIS BLOCK IS FOR GETTING a byte[] which contains image bytes -- works OK
		String partSeparator = ",";
		if (imageString.contains(partSeparator)) {
			String encodedImg = imageString.split(partSeparator)[1];
			 decodedBytes = Base64.getDecoder().decode(encodedImg.getBytes(StandardCharsets.UTF_8));

		} else {
			decodedBytes = Base64.getDecoder().decode(imageString.getBytes(StandardCharsets.UTF_8));
		}
		

		// ***** UPDATING A DB RECORD WITH APPROPRIATE ID - works OK 
		try {
			
			String success = profileStorageService.saveImage(decodedBytes, id); //returns a String like <img src="/users/file_name.png">
			response.put("status", success);
		} catch (Exception e) {
			response.computeIfPresent("status", (k, v) -> v = "error");
		}

		// ***** RETURNS A MAP WHICH CONTAINS a KEY "status" and a VALUE like <img src="/users/file_name.png> - works also OK
		return response;
	}

	
//	@PostMapping("/loadImage/{id}")
//
//	ModelAndView loadImage(@RequestParam("image") String imageString, @PathVariable int id) {
//		
//		ModelAndView model = new ModelAndView();
//		
//		
//		byte[] decodedBytes = null;
//		String partSeparator = ",";
//		if (imageString.contains(partSeparator)) {
//			String encodedImg = imageString.split(partSeparator)[1];
//			 decodedBytes = Base64.getDecoder().decode(encodedImg.getBytes(StandardCharsets.UTF_8));
//
//		} else {
//			decodedBytes = Base64.getDecoder().decode(imageString.getBytes(StandardCharsets.UTF_8));
//		}
//
//		try {
//			profileStorageService.saveImage(decodedBytes, id);
//			
//		} catch (Exception e) {
//	
//			model.setViewName("index");
//		}
//				
//		try {
//			UsrProfile profile = profileStorageService.findById(id);
//			model.setViewName("profileUpd");
//			model.addObject("profile", profile);
//			model.addObject("usr", usrStorageService.findById(String.valueOf(id)));
//		} catch (ProfileNotFoundException e) {
//			model.setViewName("index");
//		}
//		
//		return model;
//	}
	
}
