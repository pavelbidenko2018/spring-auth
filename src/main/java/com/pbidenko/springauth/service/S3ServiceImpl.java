package com.pbidenko.springauth.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.Base64;
import com.amazonaws.util.IOUtils;

@Service
public class S3ServiceImpl implements S3ServiceInterface {

	private Logger logger = LoggerFactory.getLogger(S3ServiceImpl.class);

	@Value("${amazonProperties.bucketName}")
	private String bucketName;

	@Autowired
	private AmazonS3 s3client;

	@Override
	public String downloadFile(String keyName) {
		String encoded = "";
		try {
			System.out.println("Downloading an object.");
			S3Object s3object = s3client.getObject(new GetObjectRequest("communalka1908", keyName));
			System.out.println("Content-Type: " + s3object.getObjectMetadata().getContentType());
		
			logger.info("===================== Import File - Done! =====================");
			
			byte[] rawArray = IOUtils.toByteArray(s3object.getObjectContent());
			
			encoded = Base64.encodeAsString(rawArray);

		} catch (AmazonServiceException ase) {
			logger.info("=========================================== Bucket name: " + bucketName);
			logger.info("Caught an AmazonServiceException from GET requests, rejected reasons:");
			logger.info("Error Message:    " + ase.getMessage());
			logger.info("HTTP Status Code: " + ase.getStatusCode());
			logger.info("AWS Error Code:   " + ase.getErrorCode());
			logger.info("Error Type:       " + ase.getErrorType());
			logger.info("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			logger.info("Caught an AmazonClientException: ");
			logger.info("Error Message: " + ace.getMessage());
		} catch (IOException ioe) {
			logger.info("IOE Error Message: " + ioe.getMessage());
		}
		
		return encoded;

	}

	@Override
	public String uploadFile(String keyName, File file) {
		
		String fileStoragePath = null;
		
		try {

			fileStoragePath = s3client.putObject(new PutObjectRequest("communalka1908", keyName, file)
					.withCannedAcl(CannedAccessControlList.AuthenticatedRead)).toString();

			logger.info("===================== Upload File - Done! =====================");
			
			
		} catch (AmazonServiceException ase) {
			logger.info("Caught an AmazonServiceException from PUT requests, rejected reasons:");
			logger.info("Error Message:    " + ase.getMessage());
			logger.info("HTTP Status Code: " + ase.getStatusCode());
			logger.info("AWS Error Code:   " + ase.getErrorCode());
			logger.info("Error Type:       " + ase.getErrorType());
			logger.info("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			logger.info("Caught an AmazonClientException: ");
			logger.info("Error Message: " + ace.getMessage());
		}
		
		return fileStoragePath;

	}

	private void displayText(InputStream input) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		while (true) {
			String line = reader.readLine();
			if (line == null) {
				break;
			}
			System.out.println("    " + line);
		}

	}

}

