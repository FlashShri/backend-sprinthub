package com.sprinthub.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name="project")
public class Project {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int projectId;
	
	private String projectTitle;
	private String projectDiscription;
	private LocalDate createDate;

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Set<AssignmentMapping> projectEmployeeMappings;
	
	@ManyToOne
	 @JoinColumn(name = "manager_id")
	private Manager manager;
	
	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	 private Set<Task> tasks;
	

	public Set<Task> getTasks() {
		return tasks;
	}


	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}


	public Manager getManager() {
		return manager;
	}


	public void setManager(Manager manager) {
		this.manager = manager;
	}


	public int getProjectId() {
		return projectId;
	}


	public Set<AssignmentMapping> getProjectEmployeeMappings() {
		return projectEmployeeMappings;
	}


	public void setProjectEmployeeMappings(Set<AssignmentMapping> projectEmployeeMappings) {
		this.projectEmployeeMappings = projectEmployeeMappings;
	}


	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}


	public String getProjectTitle() {
		return projectTitle;
	}


	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}


	public String getProjectDiscription() {
		return projectDiscription;
	}


	public void setProjectDiscription(String projectDiscription) {
		this.projectDiscription = projectDiscription;
	}


	public LocalDate getCreateDate() {
		return createDate;
	}


	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

}
