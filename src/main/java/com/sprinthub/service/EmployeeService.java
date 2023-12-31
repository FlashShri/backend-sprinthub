package com.sprinthub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprinthub.entity.Designation;
import com.sprinthub.entity.Employee;
import com.sprinthub.exception.customerServiceException;
import com.sprinthub.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    //1. Create Employee (Sign Up)
    public Employee register(Employee employee) {
        // Check if the employee with the given email already exists
        Optional<Employee> existingEmployee = employeeRepository.findByEmail(employee.getEmail());
        
        if (existingEmployee.isPresent()) {
        	throw new customerServiceException("Employee already exists!");
        } else {
            return employeeRepository.save(employee);
        }
    }
    
    //2. Fetch all Employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    //3. Get Employee by Email
    public Optional<Employee> getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }
    
    //3. Get Employee by ID
    public Optional<Employee> getEmployeeById(Integer id) {
        return employeeRepository.findById(id);
    }

    //4. Update Employee by Email
    public Employee updateEmployeeByEmail(String email, Employee employeeDetails) {
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new customerServiceException("Employee not found with email: " + email));
        
        employee.setFullName(employeeDetails.getFullName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setPhoneNumber(employeeDetails.getPhoneNumber());
        employee.setCity(employeeDetails.getCity());
        
        return employeeRepository.save(employee);
    }
    
    //4. Update employee by Id
    public Employee updateEmployeeById(Integer id, Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new customerServiceException("Employee not found with id: " + id));
        
        employee.setFullName(employeeDetails.getFullName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setPhoneNumber(employeeDetails.getPhoneNumber());
        employee.setCity(employeeDetails.getCity());
        
        return employeeRepository.save(employee);
    }

    //5. Delete Employee
    public void deleteEmployee(Integer id) {
        employeeRepository.deleteById(id);
    }

    //6. Assign Employee to Project
    public void assignEmployeeToProject(Long employeeId, Long projectId) {
        // Logic to handle assigning employee to a project
        // Assuming a separate service method handles this operation
    }

    
    //7. Update Designation for an Employee
    public Employee updateEmployeeDesignation(Integer employeeId, Designation newDesignation) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new customerServiceException("Employee not found with id: " + employeeId));
        
        // Update employee designation
        employee.setDesignation(newDesignation);
        
        return employeeRepository.save(employee);
    }
}
