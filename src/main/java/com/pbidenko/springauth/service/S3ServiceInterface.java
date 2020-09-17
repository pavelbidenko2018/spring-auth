package com.pbidenko.springauth.service;

import java.io.File;

public interface S3ServiceInterface {

	public void downloadFile(String keyName);

	public String uploadFile(String keyName, File file);

}
