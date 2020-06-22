package com.pbidenko.springauth.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.pbidenko.springauth.entity.Article;
import com.pbidenko.springauth.service.ArticleStorageService;

@Controller
public class AdminController {
	
	@Autowired
	private ArticleStorageService articleStorageService;

	@GetMapping(path = "/admin",produces = MediaType.IMAGE_JPEG_VALUE)
	public String admin(Model model) {
		
		Iterable<Article> articleList = articleStorageService.getArticleList();
		articleList.forEach(n->System.out.println(n.getBase64imageFile()));
		model.addAttribute("articleList",articleList);
		return "admin";
	}
	
	@PostMapping("/togglearticle")
	public String toggleArticle(@RequestParam String article_id, Model model) {
		articleStorageService.toggle(Integer.valueOf(article_id));
		return "redirect:admin";
	}

	@PostMapping("/addArticle")
	public String addArticle(@RequestParam String title, @RequestParam String descr, @RequestParam MultipartFile file) throws IOException {
		
		articleStorageService.uploadArticle(title, descr, file, true);
		
		return "redirect:admin";
	}

}
