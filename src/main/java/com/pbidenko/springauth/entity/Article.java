package com.pbidenko.springauth.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="article")
public class Article {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String title;
	private String description;
	
	@Column(name="start_date")
	private LocalDate xdate;
	
	@Column(name="publish_date")
	private LocalDate pdate;
	
	@Transient
	private String base64imageFile;
	
	@Lob
	@Column(name = "attachment", columnDefinition = "LONGBLOB")	
	private byte[] attachment;
	
	private boolean state;	
	
	public Article() {}

	public Article(String title, String description, LocalDate xdate, byte[] attachment, boolean state) {
		super();
		this.title = title;
		this.description = description;
		this.attachment = attachment;
		this.state = state;
		this.xdate = xdate;
		this.pdate = LocalDate.now();
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getAttachment() {
		return attachment;
	}

	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public String getBase64imageFile() {
		return base64imageFile;
	}

	public void setBase64imageFile(String base64imageFile) {
		this.base64imageFile = base64imageFile;
	}
	
	public LocalDate getXdate() {
		return xdate;
	}

	public void setXdate(LocalDate xdate) {
		this.xdate = xdate;
	}

	public LocalDate getPdate() {
		return pdate;
	}

	public void setPdate(LocalDate pdate) {
		this.pdate = pdate;
	}
	
}