package com.pbidenko.springauth.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "usr_profile")
public class UsrProfile {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String name;
	private String surname;
	private String country;
	private String nationality;
	private int age;
	private String userpic;
	
	private String occupation;
	
	@Column(length=3000)
	private String description;

	@OneToOne
	@JoinColumn(name = "authID",unique = true, nullable = false)
	private Usr authUser;

	@ElementCollection(targetClass = ProfessionsClassified.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "professions", joinColumns = @JoinColumn(name = "profile_id"))
	private Set<ProfessionsClassified> professionSet = new HashSet<ProfessionsClassified>();

	@Embedded
	private Project project;

	public UsrProfile() {
	};

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

	public Usr getAuthUser() {
		return authUser;
	}

	public void setAuthUser(Usr authUser) {
		this.authUser = authUser;
	}
	
	public String getUserpic() {
		return userpic;
	}

	public void setUserpic(String userpic) {
		this.userpic = userpic;
	}

	public Set<ProfessionsClassified> getProfessionSet() {
		return professionSet;
	}

	public void setProfessionSet(Set<ProfessionsClassified> professionSet) {
		this.professionSet = professionSet;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
		

}
