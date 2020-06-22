package com.pbidenko.springauth.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pbidenko.springauth.entity.Article;
import com.pbidenko.springauth.exception.ArticleNotFoundByIdException;
import com.pbidenko.springauth.repository.ArticleRepo;

@Service
public class ArticleStorageService {

	@Autowired
	private ArticleRepo articleRepo;

	public void uploadArticle(String title, String description, MultipartFile file, boolean state) {

		try {
			byte[] b = file.getBytes();
			articleRepo.save(new Article(title, description, b, state));
		} catch (IOException e) {
			e.printStackTrace();
		}

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
		Article article = articleRepo.findById(id).orElseThrow(()->new ArticleNotFoundByIdException(id));
		article.setState(!article.isState());
		articleRepo.save(article);
	}

	public Iterable<Article> getActiveArticleList() {
		
		return getImageBase64String(articleRepo.findByState(true));
		
	}
	
	
}
