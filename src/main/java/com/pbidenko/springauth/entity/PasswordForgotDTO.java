package com.pbidenko.springauth.entity;

import javax.validation.constraints.Email;

public class PasswordForgotDTO {

	@Email
	private String email;

	public PasswordForgotDTO() {
		super();
	}

	@Email
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
