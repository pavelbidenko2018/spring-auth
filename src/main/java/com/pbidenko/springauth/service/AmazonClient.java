package com.pbidenko.springauth.service;

import java.io.File;
import java.net.URL;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class AmazonClient {

	private AmazonS3 s3client;

	@Value("${amazonProperties.endPointURL}")
	private String endpointUrl;

	@Value("${amazonProperties.bucketName}")
	private String bucketName;

	@Value("${amazonProperties.accessKey}")
	private String accessKey;
	@Value("${amazonProperties.secretKey}")
	private String secretKey;

	@PostConstruct
	private void initialize() {
		AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

		this.s3client = AmazonS3ClientBuilder.standard().withRegion(Regions.EU_CENTRAL_1)
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
		System.out.println(this.s3client);
	}

	public URL uploadFileTos3bucket(String fileName, File file) {

		s3client.putObject(new PutObjectRequest("communalka", fileName + "/" + file.getName(), file)
				.withCannedAcl(CannedAccessControlList.PublicRead));

		URL resURL = s3client.getUrl(bucketName, fileName + "/" + file.getName());

		return resURL;
	}

}
