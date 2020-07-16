package com.pbidenko.springauth.exception;

public class UsrNotFoundException extends RuntimeException{
 
	private String userField;
	
	public UsrNotFoundException(String userField) {
		this.userField = userField;
		
	}

	@Override
	public String toString() {
		return "User with " + userField + " not found in DB";
	}
	
}
