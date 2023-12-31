package com.sprinthub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sprinthub.entity.Task;
import com.sprinthub.service.TaskService;

@RestController
@CrossOrigin
@RequestMapping("/task")
public class TaskServiceController {
	
	
	@Autowired
	private TaskService taskService;
	
	@PostMapping("/create")
	public ResponseEntity<?>  createTask( @RequestBody Task task){
		return taskService.create(task);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getTaskById( @PathVariable int id) {
		return  taskService.getTaskById(id);
	}
	
	 @PutMapping("/{id}")
	    public ResponseEntity<?> updateTask(@PathVariable Integer id, @RequestBody Task updatedTask) {
	        return taskService.updateTask(id, updatedTask);
	    }
	 
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Integer id) {
        return taskService.deleteTask(id);
    }
	
    
    @GetMapping("/projects/{projectId}/tasks")
    public ResponseEntity<?> getTasksByProjectId(@PathVariable int projectId) {
        ResponseEntity<?> tasks = taskService.getTasksByProjectId(projectId);
        return ResponseEntity.ok().body(tasks);
        
    }
    
    @GetMapping("/employees/{employeeId}/tasks")
    public ResponseEntity<?> getTasksByEmployeeId(@PathVariable int employeeId) {
        ResponseEntity<?> tasks = taskService.getTasksByEmployeeId(employeeId);
        return ResponseEntity.ok().body(tasks);
    }
    
    
    @PostMapping("/{taskId}/assign/{employeeId}")
    public ResponseEntity<?> assignTaskToEmployee(
            @PathVariable int taskId,
            @PathVariable int employeeId) {
        return taskService.assignTaskToEmployee(taskId, employeeId);
    }
    
    
    
	
}
