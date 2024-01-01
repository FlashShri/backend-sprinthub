package com.sprinthub.controller;

import com.sprinthub.dto.TaskDTO;
import com.sprinthub.dto.TaskDeleteStatus;
import com.sprinthub.entity.Task;
import com.sprinthub.entity.Task.TaskStatus;
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
    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<TaskDTO> taskDTOs = taskService.getAllTasks();
        return new ResponseEntity<>(taskDTOs, HttpStatus.OK);
    }
    
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


	//getting the tasks based on the status :
		@GetMapping("/status/{status}")
		public ResponseEntity<List<TaskDTO>> getTasksByStatus(@PathVariable Task.TaskStatus status) {
		    List<Task> tasks = taskService.getTasksByStatus(status);
		    List<TaskDTO> taskDTOs = tasks.stream()
		            .map(this::convertToDTO)
		            .collect(Collectors.toList());
		
		    return new ResponseEntity<>(taskDTOs, HttpStatus.OK);
		}

		private TaskDTO convertToDTO(Task task) {
		    TaskDTO taskDTO = new TaskDTO();
		    taskDTO.setTaskId(task.getTaskId());
		    taskDTO.setTitle(task.getTitle());
		    taskDTO.setDescription(task.getDescription());
		    taskDTO.setDomain(task.getDomain());
		    taskDTO.setStatus(task.getStatus());
		    taskDTO.setStartTaskDate(task.getStartTaskDate());
		    taskDTO.setDeadlineTaskDate(task.getDeadlineTaskDate());
		    taskDTO.setProjectId(task.getProject().getProjectId());
		    taskDTO.setEmployeeId(task.getEmployee().getEmployeeId());
		
		    return taskDTO;
		}
		
		
		@PutMapping("/{taskId}/{status}")
	    public ResponseEntity<String> updateTaskStatus(
	            @PathVariable int taskId,
	            @PathVariable TaskStatus status
	    ) {
	        Task updatedTask = taskService.updateTaskStatus(taskId, status);
	        return ResponseEntity.ok("Task status updated to: " + updatedTask.getStatus());
	    }

}





