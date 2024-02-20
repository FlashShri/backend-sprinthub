package com.sprinthub.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sprinthub.dto.PostProjectDTO;
import com.sprinthub.dto.ProjectDTO;
import com.sprinthub.dto.ProjectStatus;
import com.sprinthub.dto.TaskDTO;
import com.sprinthub.entity.Manager;
import com.sprinthub.entity.Project;
import com.sprinthub.exception.ProjectException;
import com.sprinthub.service.ProjectService;

@RestController
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping("/project")
public class ProjectServiceController {

    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

   
    @PostMapping
    @Secured("Admin") 
    public ResponseEntity<ProjectStatus> addDesignation(@RequestBody PostProjectDTO dto) {
        try {
            projectService.addProject(dto);

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
	
    
    
    @GetMapping
    @Secured("Admin") 
    public ResponseEntity<Object> getAllProjects() {
        try {
            List<ProjectDTO> projects = projectService.getAllProjects();

            if (!projects.isEmpty()) {
                return ResponseEntity.ok(projects);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No projects found");
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
	 
	 @PatchMapping("/{id}")
	 @Secured("Admin") 
	 public ResponseEntity<ProjectStatus> updateProject(@PathVariable int id, @RequestBody PostProjectDTO updatedProject) {
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
	 
	 @GetMapping("/employees/{employeeId}")
	 public ResponseEntity<List<Project>> getProjectsByEmployeeId(@PathVariable int employeeId) {
	        List<Project> projects = projectService.getProjectsByEmployeeId(employeeId);
	        
	        if (projects.isEmpty()) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	        
	        return new ResponseEntity<>(projects, HttpStatus.OK);
	    }    
    
	 @GetMapping("/getProject/{managerId}")
	 public ResponseEntity<List<Project>> getProjectsByManagerId(@PathVariable int managerId) {
	        List<Project> projects = projectService.getProjectsByManagerId(managerId);
	        
	        if (projects.isEmpty()) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	        
	        
	        
	        return new ResponseEntity<>(projects, HttpStatus.OK);
	    }
	 
	 @PostMapping("/{projectId}/assign-manager/{managerId}")
	    public ProjectStatus assignManagerToProject(
	            @PathVariable int projectId,
	            @PathVariable int managerId) {

	        ProjectStatus status = projectService.assignManagerToProject(projectId, managerId);
			 Optional<ProjectDTO> updatedProjectDTO = projectService.getProjectDTOById(projectId); // Fetch the updated task
			 sendProjectAssignmentUpdate(updatedProjectDTO.orElse(null));
			 return status;
	        
	    }
	 
	 
	    private void sendProjectAssignmentUpdate(ProjectDTO project) {
	        messagingTemplate.convertAndSend("/topic/projectAssignmentUpdate", project);
	    }
	 
}
