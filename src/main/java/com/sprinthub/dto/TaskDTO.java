package com.sprinthub.dto;

import java.time.LocalDate;

import com.sprinthub.entity.Task.TaskStatus;

public class TaskDTO {
	private int taskId;
    private String title;
    private String description;
    private String domain;
    private TaskStatus status;
    private LocalDate startTaskDate;
    private LocalDate deadlineTaskDate;
    private int projectId;  // assuming the ID is enough for project
    private int employeeId; // assuming the ID is enough for employee
	
    
    public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
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
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	public TaskStatus getStatus() {
		return status;
	}
	public void setStatus(TaskStatus status) {
		this.status = status;
	}
	public LocalDate getStartTaskDate() {
		return startTaskDate;
	}
	public void setStartTaskDate(LocalDate startTaskDate) {
		this.startTaskDate = startTaskDate;
	}
	public LocalDate getDeadlineTaskDate() {
		return deadlineTaskDate;
	}
	public void setDeadlineTaskDate(LocalDate deadlineTaskDate) {
		this.deadlineTaskDate = deadlineTaskDate;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
}
