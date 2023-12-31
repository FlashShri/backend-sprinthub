package com.sprinthub.controller;

import com.sprinthub.dto.TaskDTO;
import com.sprinthub.dto.TaskDeleteStatus;
import com.sprinthub.entity.Task;
import com.sprinthub.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskServiceController {

    private final TaskService taskService;

    @Autowired
    public TaskServiceController(TaskService taskService) {
        this.taskService = taskService;
    }

    /*    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable int taskId) {
        Optional<Task> task = taskService.getTaskById(taskId);
        return task.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
*/
    
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDTO> getTaskDTOById(@PathVariable int taskId) {
        // Call a method in your service to retrieve a single TaskDTO by ID
        Optional<TaskDTO> taskDTO = taskService.getTaskDTOById(taskId);

        return taskDTO.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    
    @PostMapping
    public ResponseEntity<Task> saveTask(@RequestBody Task task) {
        Task savedTask = taskService.saveTask(task);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<TaskDeleteStatus> deleteTask(@PathVariable int taskId) {
        try {
        	taskService.deleteTask(taskId);
            TaskDeleteStatus deleteStatus = new TaskDeleteStatus();
            deleteStatus.setStatus("Task with ID " + taskId + " successfully deleted.");
            return ResponseEntity.status(HttpStatus.OK).body(deleteStatus);
        } catch (Exception e) {
            // Log the exception or handle it as needed
            e.printStackTrace();
            throw new RuntimeException("Error deleting task with ID: " + taskId, e);
        }
    }

    
    @PostMapping("/{taskId}/assign/{employeeId}")
    public ResponseEntity<?> assignTaskToEmployee(
            @PathVariable int taskId,
            @PathVariable int employeeId) {
        return taskService.assignTaskToEmployee(taskId, employeeId);
    }
}





/*package com.sprinthub.controller;

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
    */
    
    
	

