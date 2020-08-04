package com.pbidenko.springauth.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Project {

	@Column(name="project_descr",length=3000)
	private String projectDescription;
	private String filePath;

	public Project() {
	};

	public Project(String projectDescription, String filePath) {
		this.projectDescription = projectDescription;
		this.filePath = filePath;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
