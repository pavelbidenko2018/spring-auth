package com.pbidenko.springauth.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;

@Component
public class ImageUtils {
	
	public  byte[] resizeImage(MultipartFile file, int width, int height) {

		byte[] imageInByte = null;

		try {
			InputStream in = file.getInputStream();
			BufferedImage bImageFromConvert = ImageIO.read(in);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			Thumbnails.of(bImageFromConvert).outputFormat("jpg").size(width, height).toOutputStream(baos);

			BufferedImage thumbnail = Thumbnailator.createThumbnail(bImageFromConvert, width, height);
			System.out.println("height: " + thumbnail.getHeight() + " width: " + thumbnail.getWidth());

			imageInByte = baos.toByteArray();
			baos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return imageInByte;
	}

}
