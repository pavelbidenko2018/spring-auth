package com.pbidenko.springauth.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private Logger logger = LoggerFactory.getLogger(ProfileStorageService.class);

	@Autowired
	private UsrProfileRepo profileRepository;

	@Autowired
	private UsrStorageService usrService;

	@Autowired
	S3ServiceImpl s3Services;

	@Value("${amazonProperties.bucketName}")
	private String bucketName;

	@Value("${image.folder}")
	String userpicsLocation;

	@Value("${profile.folder}")
	String profileLocation;

	public void save(UsrProfile profile) {

		profileRepository.save(profile);

	}

	@Transactional
	public void saveNewProfile(UsrProfile profile, MultipartFile projectFile, String projectDescription, String id) {

		String keyString = null;
		String resURL = null;

		if (projectFile.getSize() != 0) {

			byte[] decodedBytes;

			try {
				decodedBytes = projectFile.getBytes();
				keyString = profileLocation + id + "/" + projectFile.getOriginalFilename();

				if (writeFile(id, decodedBytes, keyString) != null) {

					Project project = new Project(projectDescription, projectFile.getOriginalFilename());

					if (project != null) {

						project.setFilePath(resURL);
						profile.setProject(project);
					}

					Usr usr = usrService.findById(id);

					profile.setAuthUser(usr);
					save(profile);

					logger.info("File added sucessfully to the user profile with id = " + id);
				} else {
					logger.info("Error updating profile with id = " + id);
				}

			} catch (IOException e) {

				e.printStackTrace();
			}

		}

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
		
		String keyString = userpicsLocation + id + "/" + originalFileName;

		
		try {

			if (writeFile(id, decodedBytes, keyString) != null) {

				profileRepository.updatePhoto(originalFileName, usrService.findById(String.valueOf(id)));
				logger.info("Userpic updated sucessfully");
			} else {
				logger.info("Error saving userpic: ");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		StringBuilder responseBuilder = new StringBuilder();

		responseBuilder.append("<img src=\"/users/");
		responseBuilder.append(originalFileName);
		responseBuilder.append("\" alt=\"profile image\">");

		return responseBuilder.toString();

	}

	private String writeFile(String id, byte[] decodedBytes, String keyString) throws IOException {

		File dir = new File(keyString);
		if (!dir.exists())
			dir.mkdirs();

		Path path = Paths.get(keyString);
		
				
		File resFile = Files.write(path, decodedBytes).toFile();

		String fileStoragePath = s3Services.uploadFile(keyString, resFile);

		return fileStoragePath;
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

}
