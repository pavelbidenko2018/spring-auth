package com.pbidenko.springauth.entity;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "_tokens")
public class PasswordResetToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String token;
	private Date expireDate;

	@OneToOne(targetEntity = Usr.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "usr_id")
	private Usr usr;
	
	public PasswordResetToken() {

	}

	public PasswordResetToken(String token, Usr usr, Date expireDate) {
		super();
		this.token = token;
		this.usr = usr;
		this.expireDate = expireDate;
	}

	public int getId() {
		return id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Usr getUsr() {
		return usr;
	}

	public void setUsr(Usr usr) {
		this.usr = usr;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public void setExpireDate(int minutes) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MINUTE, minutes);
		this.expireDate = now.getTime();
	}

	public boolean isExpired() {
		return new Date().after(this.expireDate);
	}

}
