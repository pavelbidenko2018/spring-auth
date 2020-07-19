package com.pbidenko.springauth.controller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	
	
	@GetMapping("/")
	public String profile(Model model) {
	
		String userID = "1"; 
		
		Usr usr = usrStorageService.findById(userID);
		
		try {
			UsrProfile profile = profileStorageService.findById(usr.getId());
			model.addAttribute("profile", profile);
		} catch (ProfileNotFoundException e) {
			
			e.printStackTrace();
		}

		model.addAttribute("username", "user");
		model.addAttribute("usr", usr);

		return "profile";
	}

	// **** This POST METHOD is running by ajax request from the file ./js/profile.js 
	@PostMapping("/loadImage/{id}")
	@ResponseBody
	Map<String, Object> loadImage(@RequestParam("image") String imageString, @PathVariable int id) {
		
		Map<String, Object> response = new HashMap<String, Object>();
		byte[] decodedBytes = null;
		
		// ***** THIS BLOCK IS FOR GETTING a byte[] which contains image bytes -------- works OK.....
		String partSeparator = ",";
		if (imageString.contains(partSeparator)) {
			String encodedImg = imageString.split(partSeparator)[1];
			 decodedBytes = Base64.getDecoder().decode(encodedImg.getBytes(StandardCharsets.UTF_8));

		} else {
			decodedBytes = Base64.getDecoder().decode(imageString.getBytes(StandardCharsets.UTF_8));
		}
		

		// ***** UPDATING A DB RECORD WITH APPROPRIATE ID -------- works also OK..... 
		try {
			
			String success = profileStorageService.saveImage(decodedBytes, id); //returns a String like <img src="/users/file_name.png">
			response.put("status", success);
		} catch (Exception e) {
			response.computeIfPresent("status", (k, v) -> v = "error");
		}

		/* ***** RETURNS A MAP WHICH CONTAINS a KEY "status" and a VALUE like this:
		 * 
		<img src="/users/file_name.png/users/9f12e41a-95fc-4fcf-ac50-3e671461f9f2_2020-07-19.png
		
		It returns a String like desired...
		And I can even see this image in the browser...
		http://localhost:8080/users/9f12e41a-95fc-4fcf-ac50-3e671461f9f2_2020-07-19.png
		
		BUT no image could be seen on the page before manually reload
		
		*/
		
		return response;
	}

}
