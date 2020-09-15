package com.pbidenko.springauth.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
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
import org.springframework.web.multipart.MultipartFile;

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

	@Autowired
	AmazonClient s3ClientService;

	@Value("${amazonProperties.bucketName}")
	private String bucketName;

	@Value("${image.file}")
	String fileLocation;

	public void save(UsrProfile profile) {

		profileRepository.save(profile);

	}

	@Transactional
	public void saveNewProfile(UsrProfile profile, MultipartFile projectFile, String projectDescription, String id) {

		String fileName = "";
		String path = null;

		/* getting the File from MultipartFile */
		File resultFile = new File(projectFile.getOriginalFilename());

		try {
			resultFile = convertMultiPartToFile(projectFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		/* *********************** */

		if (projectFile.getSize() != 0) {

			byte[] bytes;

			try {
				bytes = projectFile.getBytes();
				fileName = projectFile.getOriginalFilename();
				path = writeFile(id, bytes, fileName);
			} catch (IOException e) {

				e.printStackTrace();
			}

		}

		Project project = new Project(projectDescription, fileName);

		if (project != null) {
			if (!fileName.isEmpty()) {
				project.setFilePath(fileName);
			}

			profile.setProject(project);
		}

		Usr usr = usrService.findById(id);

		profile.setAuthUser(usr);

		save(profile);

	}

	@Transactional
	public String saveImage(String imageString, String id) {

		byte[] decodedBytes = null;
		String partSeparator = ",";
		if (imageString.contains(partSeparator)) {
			String encodedImg = imageString.split(partSeparator)[1];
			decodedBytes = Base64.getDecoder().decode(encodedImg.getBytes(StandardCharsets.UTF_8));

		} else {
			decodedBytes = Base64.getDecoder().decode(imageString.getBytes(StandardCharsets.UTF_8));
		}

		String originalFileName = getStorageName();

		try {

			String downloadResult = writeFile(id, decodedBytes, originalFileName);

			profileRepository.updatePhoto(downloadResult, usrService.findById(String.valueOf(id)));

		} catch (Exception e) {
			e.printStackTrace();
		}

		StringBuilder responseBuilder = new StringBuilder();

		responseBuilder.append("<img src=\"/users/");
		responseBuilder.append(originalFileName);
		responseBuilder.append("\" alt=\"profile image\">");

		return responseBuilder.toString();

	}

	private String writeFile(String id, byte[] decodedBytes, String originalFileName) throws IOException {

		String userImageLocation = "userpics/"  + id + "/";
		
		File dir = new File(userImageLocation);
		if (!dir.exists())
			dir.mkdirs();

		Path path = Paths.get(dir.toString()  + originalFileName);

		File resFile = Files.write(path, decodedBytes).toFile();
		
		URL res = s3ClientService.uploadFileTos3bucket(userImageLocation, resFile);
				
		return res.toString();
	}

	public UsrProfile findByUsr(Usr usr) throws ProfileNotFoundException {

		UsrProfile profile = profileRepository.findByAuthUser(usr)
				.orElseThrow(() -> new ProfileNotFoundException(usr.getId()));
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

	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}
	
		
}
