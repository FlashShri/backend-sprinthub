package com.sprinthub.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.sprinthub.entity.Manager;
import com.sprinthub.entity.Task.TaskStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class ProjectDTO {


	private int id;
	private String projectTitle;

	private String projectDescription;
	private LocalDate createDate;
	
	
	private Manager manager;

	public ProjectDTO(int id, String projectTitle, String projectDescription, LocalDate createDate, Manager manager) {
		super();
		this.id = id;
		this.projectTitle = projectTitle;
		this.projectDescription = projectDescription;
		this.createDate = createDate;
		this.manager = manager;
	}


	public ProjectDTO() {
		super();
	}


	@Override
	public String toString() {
		return "ProjectDTO [id=" + id + ", projectTitle=" + projectTitle + ", projectDescription=" + projectDescription
				+ ", createDate=" + createDate + ", manager=" + manager + "]";
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
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


	public Manager getManager() {
		return manager;
	}


	public void setManager(Manager manager) {
		this.manager = manager;
	}



	
	
	
	
}
