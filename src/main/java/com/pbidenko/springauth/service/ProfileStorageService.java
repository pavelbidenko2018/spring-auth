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
	public String saveImage(MultipartFile file, int id) {

		Path path = null;
		try {
			byte[] bytes = file.getBytes();

			String userImageLocation = fileLocation + "/" + id + "/";
			String originalFileName = file.getOriginalFilename();

			path = Paths.get(fileLocation + getStorageName(originalFileName));

			Files.write(path, bytes);
			
			profileRepository.updatePhoto(getStorageName(originalFileName));
		
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return path.toString();	

	}

	private String getStorageName(String originalFileName) {

		String extention = originalFileName.substring(originalFileName.lastIndexOf("."));
		String storageFileName = UUID.randomUUID().toString() + "_"
				+ new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		return storageFileName + extention;
	}

}
