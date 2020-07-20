package com.pbidenko.springauth.exception;

public class ArticleNotFoundByIdException extends RuntimeException {

	
	private static final long serialVersionUID = 1L;
	private int id;

	public ArticleNotFoundByIdException(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Article with id " + id + " not found in DB";
	}

}
