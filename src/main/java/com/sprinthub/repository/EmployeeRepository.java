package com.sprinthub.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sprinthub.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	 
	public Optional<Employee> findByEmail(String Email);
	public Employee findByEmployeeId(int employeeId);
	public Employee findByEmailAndPassword(String email, String password);
	
}

