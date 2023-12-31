package com.sprinthub.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sprinthub.entity.Employee;
import com.sprinthub.entity.Project;
import com.sprinthub.entity.Task;
import com.sprinthub.exception.TaskServiceException;
import com.sprinthub.repository.EmployeeRepository;
import com.sprinthub.repository.ProjectRepository;
import com.sprinthub.repository.TaskRepository;
//
//@Service
//public class TaskService {
//	
//	
//	@Autowired
//	private TaskRepository taskRepository;
//	
//	public ResponseEntity<?> create(Task task) {
//		Optional<Task> existingTask = taskRepository.findById(task.getTaskId());
//		
//		if( existingTask.isPresent()) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(new TaskServiceException("Task already exists!"));
//		}else {
//		Task savedTask = taskRepository.save(task);
//		
//			return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
//		}
//		
//		
//	}
//
//	public  ResponseEntity<Task> getTaskById(int id) {
//			Optional<Task> task = taskRepository.findById(id);
//			
//			return task.map( value -> ResponseEntity.ok().body(value))
//                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
//	}
//	
//	
//    public ResponseEntity<?> updateTask(Integer id, Task updatedTask) {
//        if (taskRepository.existsById(id)) {
//            updatedTask
//            .setTaskId(id);
//            Task updated = taskRepository.save(updatedTask);
//            return ResponseEntity.ok().body(updated);
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//    }
//    
//    public ResponseEntity<?> deleteTask(Integer id) {
//        if (taskRepository.existsById(id)) {
//            taskRepository.deleteById(id);
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//    }
//	
//
//	
//}



@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;  // Assuming you have a ProjectRepository

    @Autowired
    private EmployeeRepository employeeRepository;  // Assuming you have an EmployeeRepository

    public ResponseEntity<?> create(Task task) {
        Optional<Task> existingTask = taskRepository.findById(task.getTaskId());

        if (existingTask.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new TaskServiceException("Task already exists!"));
        } else {
            Task savedTask = taskRepository.save(task);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
        }
    }

    
    public ResponseEntity<Task> getTaskById(int id) {
        Optional<Task> task = taskRepository.findById(id);

        return task.map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity<?> updateTask(Integer id, Task updatedTask) {
        if (taskRepository.existsById(id)) {
            updatedTask.setTaskId(id);
            Task updated = taskRepository.save(updatedTask); // save or update
            return ResponseEntity.ok().body(updated);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    
    
    // backlog in kanban board
    public ResponseEntity<?> getTasksByProjectId(int projectId) {
    	
        Optional<Project> project = projectRepository.findById(projectId);

        if (project.isPresent()) {
            List<Task> tasks = taskRepository.findByProjectId(projectId);
            return ResponseEntity.ok().body(tasks);
        } else {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new TaskServiceException("Project with ID " + projectId + " not found"));
        }
    }
    
    
    

    public ResponseEntity<?> getTasksByEmployeeId(int employeeId) {
    	
        Optional<Employee> employee = employeeRepository.findById(employeeId);

        if (employee.isPresent()) {
            List<Task> tasks = taskRepository.findByEmployeeId(employeeId);
            return ResponseEntity.ok().body(tasks);
        } else {
            
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new TaskServiceException("Employee with ID " + employeeId + " not found"));
        	
        }
    }

    public ResponseEntity<List<Task>> getTasksByDomain(String domain) {
        List<Task> tasks = taskRepository.findByDomain(domain);
        return ResponseEntity.ok().body(tasks);
    }

    public ResponseEntity<?> deleteTask(Integer id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    
    
    public ResponseEntity<?> assignTaskToEmployee(int taskId, int employeeId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);

        if (taskOptional.isPresent() && employeeOptional.isPresent()) {
            Task task = taskOptional.get();
            Employee employee = employeeOptional.get();

            task.setEmployee(employee);
            Task updatedTask = taskRepository.save(task);

            return ResponseEntity.ok().body(updatedTask);
        } else {
//            throw new NotFoundException("Task or Employee not found");
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new TaskServiceException("Task or Employee not found"));
        	
        }
    }
}


