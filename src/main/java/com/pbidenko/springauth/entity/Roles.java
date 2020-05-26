package com.pbidenko.springauth.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Roles {

	private int id;
	private String role;

	public Roles(String role) {
		super();
		this.role = role;
	}

	public Roles() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Roles(int id, String role) {
		super();
		this.id = id;
		this.role = role;
	}

}
