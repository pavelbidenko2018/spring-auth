package com.pbidenko.springauth.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pbidenko.springauth.entity.Article;

@Repository
public interface ArticleRepo extends CrudRepository<Article, Integer> {
	
	public Optional<Article> findById(int id);
	public Collection<Article> findByState(boolean state);
	
}
