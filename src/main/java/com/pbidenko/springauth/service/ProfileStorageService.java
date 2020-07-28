package com.pbidenko.springauth.service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbidenko.springauth.entity.Project;
import com.pbidenko.springauth.entity.Usr;
import com.pbidenko.springauth.entity.UsrProfile;
import com.pbidenko.springauth.exception.ProfileNotFoundException;
import com.pbidenko.springauth.repository.UsrProfileRepo;

@Service
public class ProfileStorageService {

	@Autowired
	private UsrProfileRepo profileRepository;

	@Autowired
	private UsrStorageService usrService;	

	@Value("${image.file}")
	String fileLocation;

	public void save(UsrProfile profile) {

		profileRepository.save(profile);

	}

	@Transactional
	public String saveImage(String imageString, int id) {
		
		byte[] decodedBytes = null;
		String partSeparator = ",";
		if (imageString.contains(partSeparator)) {
			String encodedImg = imageString.split(partSeparator)[1];
			 decodedBytes = Base64.getDecoder().decode(encodedImg.getBytes(StandardCharsets.UTF_8));

		} else {
			decodedBytes = Base64.getDecoder().decode(imageString.getBytes(StandardCharsets.UTF_8));
		}

		Path path = null;
		String originalFileName = getStorageName();
	
		try {
			
			String userImageLocation = fileLocation + "/" + id + "/";
						
			File dir = new File(userImageLocation);
			if(!dir.exists()) dir.mkdirs();
			
			path = Paths.get(dir.toString() + "/" + originalFileName);		
			
			Files.write(path, decodedBytes);
			
			profileRepository.updatePhoto(originalFileName,usrService.findById(String.valueOf(id)));
		
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		StringBuilder responseBuilder = new StringBuilder();

		responseBuilder.append("<img src=\"/users/");
		responseBuilder.append(originalFileName);
		responseBuilder.append("\" alt=\"profile image\">");
		
		return responseBuilder.toString();	

	}

	public UsrProfile findByUsr(Usr usr) throws ProfileNotFoundException {
		
		int usrID = usr.getId();
		UsrProfile profile = profileRepository.findById(usrID).orElseThrow(()->new ProfileNotFoundException(usrID));
		return profile;
	}
	
	private String getStorageName() {

		String storageFileName = UUID.randomUUID().toString() + "_"
				+ new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		return storageFileName + ".png";
	}

	public void updateProfile(UsrProfile profileExists) {
					
		profileRepository.save(profileExists);
		
	}

	public void saveNewProfile(UsrProfile profile) {
		
		Project userProject = profile.getProject();
		String userProjectFilePath = userProject.getFilePath();
		
		if(userProject.getFilePath()!=null) {
			byte[] fileBytes;
		};
				
	}

}
