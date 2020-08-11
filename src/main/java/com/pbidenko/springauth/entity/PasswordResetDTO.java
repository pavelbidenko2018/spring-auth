package com.pbidenko.springauth.entity;

import javax.validation.constraints.NotEmpty;

public class PasswordResetDTO {
	@NotEmpty
	private String password;

	@NotEmpty
	private String confirmPassword;

	@NotEmpty
	private String token;

	public PasswordResetDTO() {
		super();
	}

	public PasswordResetDTO(String password, String confirmPassword, String token) {
		super();
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.token = token;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
