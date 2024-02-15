package com.sprinthub.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sprinthub.dto.EmployeeDTO;
import com.sprinthub.dto.PostEmpDTO;
import com.sprinthub.entity.Designation;
import com.sprinthub.entity.Employee;
import com.sprinthub.exception.customerServiceException;
import com.sprinthub.service.EmployeeService;

@RestController
@CrossOrigin
@RequestMapping("/employees")
public class EmployeeServiceController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Employee> registerEmployee(@RequestBody PostEmpDTO employee) {
        try {
            Employee createdEmployee = employeeService.register(employee);
            return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
        } catch (customerServiceException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
 // Get all Employees
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        if (employees.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }
    
    @GetMapping("/employee")
    public ResponseEntity<Employee> getEmployee(
    		  @RequestParam String email,
    	        @RequestParam String password
    		) {
   
    	Employee emp =  employeeService.getEmployeeByEmailAndPassword( email , password);
    	 if (emp != null) {
             return new ResponseEntity<>(emp, HttpStatus.OK);
         } else {
             return new ResponseEntity<>(HttpStatus.NOT_FOUND); // emp not found or invalid credentials
         }
    
    }
    
    @GetMapping("/{email}")
    public ResponseEntity<Employee> getEmployeeByEmail(@PathVariable String email) {
        Optional<Employee> employee = employeeService.getEmployeeByEmail(email);
        return employee.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/getBy/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Integer id) {
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK); }
    
    
    
    
    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Integer id, @RequestBody EmployeeDTO employeeDetails) {
    	EmployeeDTO updatedEmployee = employeeService.updateEmployeeById(id, employeeDetails);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Integer id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/designation/{employeeId}")
    public ResponseEntity<Employee> updateEmployeeDesignation(@PathVariable Integer employeeId, @RequestBody Designation newDesignation) {
        Employee updatedEmployee = employeeService.updateEmployeeDesignation(employeeId, newDesignation);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }
    
    
    @GetMapping("/byProject/{projectId}")
    public ResponseEntity<List<Employee>> getEmployeesByProjectId(@PathVariable int projectId) {
        List<Employee> employees = employeeService.getEmployeesByProjectId(projectId);

        if (employees.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // No employees found for the given project
        } else {
            return new ResponseEntity<>(employees, HttpStatus.OK);
        }
    }
    
}
