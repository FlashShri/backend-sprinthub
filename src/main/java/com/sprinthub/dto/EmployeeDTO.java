package com.sprinthub.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class EmployeeDTO {
	private int employeeId;
	private String fullName;
	private String email;
	private long phoneNumber;
	private String city;

	
}
