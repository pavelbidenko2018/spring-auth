package com.pbidenko.springauth.exception;

public class ProfileNotFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	
	public ProfileNotFoundException(int id) {
		this.id= id;
	}
	
	@Override
	public String toString() {
		return "Profile with " + id + " not found in db";
	}

}
