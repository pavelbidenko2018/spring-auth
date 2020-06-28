package com.pbidenko.springauth.controller;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.pbidenko.springauth.entity.Article;
import com.pbidenko.springauth.repository.ArticleRepo;
import com.pbidenko.springauth.service.ArticleStorageService;

@Controller
public class AdminController {

	@Autowired
	private ArticleStorageService articleStorageService;

	@Autowired
	ArticleRepo articleRepo;

	@GetMapping(path = "/admin", produces = MediaType.IMAGE_JPEG_VALUE)
	public String admin(Model model) {

		Iterable<Article> articleList = articleStorageService.getArticleList();
		model.addAttribute("articleList", articleList);
		return "admin";
	}

	@ResponseBody
	@PostMapping(path = "/updateArticle")
	public boolean updateArticleState(@RequestBody String request) {

		int id = Integer.valueOf(request);
		
		articleStorageService.toggle(id);
		
		return articleRepo.findById(id).get().isState();

	}

	@PostMapping("/addArticle")
	public String addArticle(@RequestParam String title, @RequestParam String descr, @RequestParam String xdate, @RequestParam MultipartFile file)
			throws IOException {

		articleStorageService.uploadArticle(title, descr, LocalDate.parse(xdate), file, true);

		return "redirect:/admin";
	}

}
