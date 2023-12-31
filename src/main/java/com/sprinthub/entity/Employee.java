package com.sprinthub.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="employee")
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int employeeId;

	private String fullName;
	private String email;
	private String password;
	private long phoneNumber;
	private String city;

	
	@OneToOne
	@JoinColumn(name = "designationId")
	private Designation designation;

	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
	  @JsonBackReference
	private Set<Task> tasks;
	
	@OneToMany(mappedBy = "employee", cascade =  CascadeType.ALL)
    private Set<AssignmentMapping> projectEmployeeMappings;

	
	
	public Set<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Set<AssignmentMapping> getProjectEmployeeMappings() {
		return projectEmployeeMappings;
	}

	public void setProjectEmployeeMappings(Set<AssignmentMapping> projectEmployeeMappings) {
		this.projectEmployeeMappings = projectEmployeeMappings;
	}

	public Designation getDesignation() {
		return designation;
	}

	public void setDesignation(Designation designation) {
		this.designation = designation;
	}


}
