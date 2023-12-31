package com.sprinthub.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.sprinthub.dto.ProjectStatus;
import com.sprinthub.entity.Project;
import com.sprinthub.exception.ProjectException;
import com.sprinthub.service.ProjectService;

@RestController
@CrossOrigin
public class ProjectServiceController {

    @Autowired
    private ProjectService projectService;

   
    @PostMapping("/project")
    public ResponseEntity<ProjectStatus> addDesignation(@RequestBody Project project) {
        try {
            projectService.addProject(project);

            ProjectStatus status = new ProjectStatus();
            status.setStatus("Project added successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(status);
        } catch (Exception e) {
        	 ProjectStatus status = new ProjectStatus();
            status.setStatus("Error adding project");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(status);
        }
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<Object> getProjectById(@PathVariable int id) {
        try {
            Optional<Project> project = projectService.getProjectById(id);

            if (project.isPresent()) {
                return ResponseEntity.ok(project.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found");
            }
        } catch (ProjectException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
		
	 @DeleteMapping("/project/{id}")
	    public ResponseEntity<String> deleteProject(@PathVariable int id) {
	        try {
	        		projectService.deleteProject(id);
	            return ResponseEntity.ok("Project deleted successfully");
	        } catch (ProjectException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
	        }
	    }
	 
	 @PutMapping("/project/{id}")
	 public ResponseEntity<ProjectStatus> updateProject(@PathVariable int id, @RequestBody Project updatedProject) {
	     try {
	         projectService.updateProject(id, updatedProject);

	         ProjectStatus status = new ProjectStatus();
	         status.setStatus("Project updated successfully");
	         return ResponseEntity.ok(status);
	     } catch (ProjectException e) {
	         
	         e.printStackTrace();

	         ProjectStatus status = new ProjectStatus();
	         status.setStatus("Project not found");
	         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(status);
	     } catch (Exception e) {
	        
	         e.printStackTrace();

	         ProjectStatus status = new ProjectStatus();
	         status.setStatus("Error updating project");
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(status);
	     }
	 }
	 
	 @GetMapping("/employee/{employeeId}")
	 public ResponseEntity<List<Project>> getProjectsByEmployeeId(@PathVariable int employeeId) {
	        List<Project> projects = projectService.getProjectsByEmployeeId(employeeId);
	        
	        if (projects.isEmpty()) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	        
	        return new ResponseEntity<>(projects, HttpStatus.OK);
	    }    
    
}
