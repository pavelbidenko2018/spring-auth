package com.pbidenko.springauth.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.pbidenko.springauth.entity.Usr;
import com.pbidenko.springauth.entity.UsrProfile;
import com.pbidenko.springauth.exception.ProfileNotFoundException;
import com.pbidenko.springauth.service.ProfileStorageService;
import com.pbidenko.springauth.service.S3ServiceImpl;
import com.pbidenko.springauth.service.UsrStorageService;

@Controller
public class ProfileController {

	@Autowired
	ProfileStorageService profileStorageService;

	@Autowired
	UsrStorageService usrStorageService;

	@GetMapping("/my_profile")
	public String profile(Model model, @CookieValue(value = "userpic",defaultValue="") String userpic) {
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
			model.addAttribute("project", profile.getProject());

			model.addAttribute("userpic","data:image/png;base64," + profileStorageService.getUserpic(profile.getAuthUser().getId(), profile.getUserpic()));

		} catch (ProfileNotFoundException e) {
			model.addAttribute("profile", null);
			model.addAttribute("project", null);
			e.printStackTrace();
		}

		return "profile";
	}

	@PostMapping(value = "/saveProfile/{id}", consumes = "multipart/form-data")

	public ModelAndView saveProfile(@ModelAttribute UsrProfile profile, @RequestPart MultipartFile projectFile,
			@RequestParam String projectDescription, @PathVariable String id) {

		profileStorageService.saveNewProfile(profile, projectFile, projectDescription, id);

		return new ModelAndView("redirect:/my_profile");
	}

	@PostMapping("/updateProfile/{id}")
	@ResponseBody
	public Map<String, Object> updateProfile(@ModelAttribute("profile") UsrProfile profile, @PathVariable String id) {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("status", null);

		Usr usr = usrStorageService.findById(id);

		try {
			UsrProfile profileExists = profileStorageService.findByUsr(usr);

			profileExists = profile;
			profileExists.setAuthUser(usr);

			profileStorageService.save(profileExists);
			response.compute("status", (k, v) -> v = "edit");

		} catch (ProfileNotFoundException e) {
//			profile.setAuthUser(usr);
			profileStorageService.save(profile);
			response.compute("status", (k, v) -> v = "add");
		}

		return response;

	}

	@PostMapping("/loadImage/{id}")
	@ResponseBody
	Map<String, Object> loadImage(@RequestParam("image") String imageString, @PathVariable String id) {
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
