package com.pbidenko.springauth.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "usr")
public class Usr {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "usr_id")
	private int id;

	@Column(name = "username")
	private String username;
	
	@Column(name = "email")
	private String email;

	@Column(name = "pwd")
	private String pwd;
	
	@OneToOne(mappedBy = "authUser")
	private UsrProfile profile;
		
	@ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "usr_id"))
	@Enumerated(EnumType.STRING)
	private Set<Role> roleSet = new HashSet<Role>();

	public Usr(String username, String pwd, String email, Set<Role> roleSet) {
		super();
		this.username = username;
		this.pwd = pwd;
		this.email = email;

		this.roleSet = roleSet;
	}

	public Usr(String username, String email, String pwd) {
		this.username = username;
		this.email = email;
		this.pwd = pwd;
	};
	
	public Usr() {
	};

	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Role> getRoleSet() {
		return roleSet;
	}

	public void setRoleSet(Set<Role> roleSet) {
		this.roleSet = roleSet;
	}

	public Set<Role> getRolesSet() {
		return roleSet;
	}

	public void setRolesSet(Set<Role> roles) {
		this.roleSet = roles;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public UsrProfile getProfile() {
		return profile;
	}

	public void setProfile(UsrProfile profile) {
		this.profile = profile;
	}
	
	

}
