package com.pbidenko.springauth.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class UsrProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
	private String surname;
	private String country;
	private String nationality;
	private int age;
	
	@Lob
	@Column(name = "userpic", columnDefinition = "LONGBLOB")	
	private byte[] userpic;
	
	@OneToOne
	@JoinColumn(name = "authId")	
	private Usr authUser;
	
	@ElementCollection(targetClass = ProfessionsClassified.class,fetch = FetchType.EAGER)
	@CollectionTable(name = "professions", joinColumns = @JoinColumn(name = "profile_id"))
	
	private Set<ProfessionsClassified> professionSet = new HashSet<ProfessionsClassified>();
	
	public UsrProfile() {};	
		
	public UsrProfile(String name, String surname, String country, String nationality, int age, Usr authUser) {
		super();
		this.name = name;
		this.surname = surname;
		this.country = country;
		this.nationality = nationality;
		this.age = age;
		this.authUser = authUser;
	}



	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}


	public byte[] getUserpic() {
		return userpic;
	}


	public void setPhoto(byte[] userpic) {
		this.userpic = userpic;
	}


	public Usr getAuthUser() {
		return authUser;
	}


	public void setAuthUser(Usr authUser) {
		this.authUser = authUser;
	}

	
	

}
