package com.sprinthub.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;



@Entity
@Table(name="designation")
public class Designation {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int designationId;
	
	
	@OneToOne(mappedBy = "designation" , cascade = CascadeType.ALL)
	private Employee employee;

	private String title;

	

   // private DesignationTitle title;
    
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

    public int getDesignationId() {
        return designationId;
    }

    public void setDesignationId(int designationId) {
        this.designationId = designationId;
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

 
}
