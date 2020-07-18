package com.pbidenko.springauth.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.pbidenko.springauth.entity.UsrProfile;
import com.pbidenko.springauth.repository.UsrProfileRepo;
import com.pbidenko.springauth.repository.UsrRepo;

@Service
public class ProfileStorageService {

	@Autowired
	private UsrProfileRepo profileRepository;

	@Autowired
	private UsrRepo usrRepository;

	@Value("${image.file}")
	String fileLocation;

	public void save(UsrProfile profile) {

		profileRepository.save(profile);

	}

	@Transactional
	public String saveImage(byte[] imageBytes, int id) {

		Path path = null;
		String originalFileName = getStorageName();
	
		try {
			
			String userImageLocation = fileLocation + "/" + id + "/";
			
			path = Paths.get(fileLocation + originalFileName);

			Files.write(path, imageBytes);
			
			profileRepository.updatePhoto(originalFileName);
		
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		StringBuilder responseBuilder = new StringBuilder();
//		responseBuilder.append("<img th:src=\"@{/users/");
//		responseBuilder.append(originalFileName);
//		responseBuilder.append("}\" alt=\"profile image\">");
		
		responseBuilder.append("<img src=\"/users/");
		responseBuilder.append(originalFileName);
		responseBuilder.append("\" alt=\"profile image\">");
		
		System.out.println(responseBuilder);
		return responseBuilder.toString();	

	}

	private String getStorageName() {

		String storageFileName = UUID.randomUUID().toString() + "_"
				+ new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		return storageFileName + ".png";
	}

}
