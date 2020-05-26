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
import javax.persistence.JoinTable;
import javax.persistence.Table;

@Entity
@Table(name = "usr")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "usr_id")
	private int id;
	private String name;

	@ElementCollection(targetClass = Role.class, fetch=FetchType.EAGER)
	@CollectionTable(name="roles",joinColumns = @JoinColumn(name="usr_id"))
	@Enumerated(EnumType.STRING)
	private Set<Role> roleSet = new HashSet<Role>();

	public User(String name, Set<Role> roleSet) {
		super();
		this.name = name;
		this.roleSet = roleSet;
	}

	public User() {
	};

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

}
