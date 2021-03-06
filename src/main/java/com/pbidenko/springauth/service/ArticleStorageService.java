package com.pbidenko.springauth.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pbidenko.springauth.entity.Article;
import com.pbidenko.springauth.exception.ArticleNotFoundByIdException;
import com.pbidenko.springauth.repository.ArticleRepo;
import com.pbidenko.springauth.utils.ImageUtils;

@Service
public class ArticleStorageService {

	@Autowired
	private ArticleRepo articleRepo;
	
	@Autowired
	private ImageUtils imageUtils;

	public void uploadArticle(String title, String description, LocalDate xdate, MultipartFile file, boolean state) {

		int width = 354;
		int height = 450;
		byte[] imageInByte = imageUtils.resizeImage(file, width, height);

		articleRepo.save(new Article(title, description, xdate, imageInByte, state));

	}
	
	public Iterable<Article> getArticleList() {

		Iterable<Article> articleList = articleRepo.findAll();

		return getImageBase64String(articleList);

	}

	private Iterable<Article> getImageBase64String(Iterable<Article> articleList) {
		articleList.forEach(n -> {
			try {
				n.setBase64imageFile(new String(Base64.getEncoder().encode(n.getAttachment()), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		});

		return articleList;
	}

	public void toggle(int id) {
		Article article = articleRepo.findById(id).orElseThrow(() -> new ArticleNotFoundByIdException(id));
		article.setState(!article.isState());
		articleRepo.save(article);
	}

	public Iterable<Article> getActiveArticleList() {

		return getImageBase64String(articleRepo.findByState(true));

	}

}
