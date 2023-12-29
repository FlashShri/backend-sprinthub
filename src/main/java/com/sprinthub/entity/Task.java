package com.sprinthub.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;



@Entity
@Table(name="task")
public class Task {
	
	  	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int taskId;

	    private String title;
	    private String description;
	    private String domain;

	    @Enumerated(EnumType.STRING)
	    @Column(name = "status")
	    private TaskStatus status; 
	    
	    public enum TaskStatus {
	    	BACKLOG,
	        ACTIVE,
	        REVIEWING,
	        DONE
	    }
	    
	    private LocalDate startTaskDate;
	    private LocalDate deadlineTaskDate;

	    @ManyToOne
	    @JoinColumn(name = "project_id")
	    private Project project;

	    
	    @OneToOne
	    @JoinColumn(name = "employeeId")
	    private Employee employee;
	    
	    

		public String getDomain() {
			return domain;
		}

		public void setDomain(String domain) {
			this.domain = domain;
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

		public int getTaskId() {
			return taskId;
		}

		public void setTaskId(int taskId) {
			this.taskId = taskId;
		}

		public Project getProject() {
			return project;
		}

		public void setProject(Project project) {
			this.project = project;
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

		public TaskStatus getStatus() {
			return status;
		}

		public void setStatus(TaskStatus status) {
			this.status = status;
		}

		public Employee getEmployee() {
			return employee;
		}

		public void setEmployee(Employee employee) {
			this.employee = employee;
		}

}
