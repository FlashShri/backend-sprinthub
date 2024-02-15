package com.sprinthub.dto;

import java.time.LocalDate;

public class PostProjectDTO {
	
	
	private String projectTitle;

	private String projectDescription;
	private LocalDate createDate;
	public PostProjectDTO(String projectTitle, String projectDescription, LocalDate createDate) {
		super();
		this.projectTitle = projectTitle;
		this.projectDescription = projectDescription;
		this.createDate = createDate;
	}
	@Override
	public String toString() {
		return "PostProjectDTO [projectTitle=" + projectTitle + ", projectDescription=" + projectDescription
				+ ", createDate=" + createDate + "]";
	}
	public PostProjectDTO() {
		super();
	}
	public String getProjectTitle() {
		return projectTitle;
	}
	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}
	public String getProjectDescription() {
		return projectDescription;
	}
	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}
	public LocalDate getCreateDate() {
		return createDate;
	}
	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}
	
	
}
