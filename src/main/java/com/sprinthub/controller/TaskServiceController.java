package com.sprinthub.controller;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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

import com.sprinthub.dto.TaskCreateDTO;
import com.sprinthub.dto.TaskDTO;
import com.sprinthub.dto.TaskDeleteStatus;
import com.sprinthub.entity.Task;
import com.sprinthub.entity.Task.TaskStatus;
import com.sprinthub.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3000/employee-dashboard"})
public class TaskServiceController {

    private final TaskService taskService;

    @Autowired
    public TaskServiceController(TaskService taskService) {
        this.taskService = taskService;
    }
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

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
    

@GetMapping("/employee")
public ResponseEntity<List<TaskDTO>> getAllTasksByEmployee(@RequestParam int empId) {
			  List<TaskDTO> taskDTOs = taskService.getAllTasksByEmployee(empId); 
			  return new 
			  ResponseEntity<>(taskDTOs, HttpStatus.OK); 
			  
} 
			 
			     
			     @GetMapping("/{taskId}")
    public ResponseEntity<TaskDTO> getTaskDTOById(@PathVariable int taskId) {
        // Call a method in your service to retrieve a single TaskDTO by ID
        Optional<TaskDTO> taskDTO = taskService.getTaskDTOById(taskId);

        return taskDTO.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    
    @PostMapping("/{project_id}")
    public ResponseEntity<TaskDTO> saveTask(@RequestBody TaskCreateDTO task , @PathVariable int project_id) {
    	TaskDTO savedTask = taskService.saveTask(task , project_id);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }
    
    @GetMapping("/project/{project_id}")
    public ResponseEntity<List<TaskDTO>> getTasksByProjectId(@PathVariable int project_id) {
        List<TaskDTO> tasks = taskService.getTasksByProjectId(project_id);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
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
            @PathVariable int employeeId){
        return taskService.assignTaskToEmployee(taskId, employeeId);
    }


	//getting the tasks based on the status :
		@GetMapping("/status/{status}/{employeeId}")
		public List<TaskDTO> getTasksByStatus(@PathVariable Task.TaskStatus status , @PathVariable int employeeId) {

			List<TaskDTO> tasks  = null ;
			switch(status.name()){
			case "BACKLOG": 
				Predicate<Task> backlogFilter = task -> task.getStatus() == Task.TaskStatus.BACKLOG;
				tasks = taskService.getTasksByStatus(employeeId, backlogFilter);
				break;
			case "ACTIVE" :
				Predicate<Task> activeFilter = task -> task.getStatus() == Task.TaskStatus.ACTIVE;
				tasks = taskService.getTasksByStatus(employeeId, activeFilter);
				break;
			case "REVIEWING" :
				Predicate<Task> reviewingFilter = task -> task.getStatus() == Task.TaskStatus.REVIEWING;
				tasks = taskService.getTasksByStatus(employeeId, reviewingFilter);
				break;
			case "DONE" :
				Predicate<Task> doneFilter = task -> task.getStatus() == Task.TaskStatus.DONE;
				tasks = taskService.getTasksByStatus(employeeId, doneFilter);
				break;
			}
			return tasks;
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
		
		
		@PatchMapping("/{taskId}/{status}/{employeeId}")
	    public ResponseEntity<String> updateTaskStatus(
	            @PathVariable int taskId,
	            @PathVariable TaskStatus status,
	            @PathVariable int employeeId 
	    ) {
			 boolean updated = taskService.updateTaskStatus(taskId, status, employeeId);
			 
		        if (updated) {
		            Optional<TaskDTO> updatedTaskDTO = taskService.getTaskDTOById(taskId); // Fetch the updated task
		            sendTaskStatusUpdate(updatedTaskDTO.orElse(null));
		            return ResponseEntity.ok("Task status updated successfully.");

		        } else {
		            return ResponseEntity.notFound().build();
		        }
	    }
		
	    // Send WebSocket notification for task status update
	    private void sendTaskStatusUpdate(TaskDTO task) {
	        messagingTemplate.convertAndSend("/topic/taskStatusUpdates", task);
	    }
}





