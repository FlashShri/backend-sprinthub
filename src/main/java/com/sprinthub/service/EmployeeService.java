package com.sprinthub.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprinthub.dto.EmployeeDTO;
import com.sprinthub.dto.PostEmpDTO;
import com.sprinthub.entity.Designation;
import com.sprinthub.entity.Employee;
import com.sprinthub.exception.customerServiceException;
import com.sprinthub.repository.AssignmentMappingRepository;
import com.sprinthub.repository.EmployeeRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private AssignmentMappingRepository assignmentMappingRepository;
    
    @Autowired
    private ModelMapper mapper;

    //1. Create Employee (Sign Up)
    public Employee register(PostEmpDTO employee) {
    	
    	
        // Check if the employee with the given email already exists
        Optional<Employee> existingEmployee = employeeRepository.findByEmail(employee.getEmail());
        
        if (existingEmployee.isPresent()) {
        	throw new customerServiceException("Employee already exists!");
        } else {
        	Employee emp =  mapper.map(employee, Employee.class);
        	
            return employeeRepository.save(emp);
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
    public EmployeeDTO getEmployeeById(Integer id) {
        
    	 Optional<Employee> emp = employeeRepository.findById(id);
    	 Employee emp1 = emp.get();
    	 EmployeeDTO res = mapper.map(emp1, EmployeeDTO.class);
    	return res ;
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
    public EmployeeDTO updateEmployeeById(Integer id, EmployeeDTO employeeDetails) {
    	
    	if( employeeRepository.existsById(id)) {
    		Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new customerServiceException("Employee not found with id: " + id));
            employeeDetails.setEmployeeId(id);
            
          Employee emp =   mapper.map( employeeDetails , Employee.class);
            emp.setPassword( employee.getPassword());
             Employee empFinal= employeeRepository.save(emp);
            
              EmployeeDTO dto = mapper.map( empFinal , EmployeeDTO.class) ;
    		return dto ;
    	}
        

        
        return null;
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

    //8. fetch all employees by projectID
    public List<Employee> getEmployeesByProjectId(int projectId) {
        return assignmentMappingRepository.findEmployeesByProjectId(projectId);
    }

	public Employee getEmployeeByEmailAndPassword(String email, String password) {
		Employee emp  = employeeRepository.findByEmailAndPassword(email , password);
		return emp;
		
	}
}
