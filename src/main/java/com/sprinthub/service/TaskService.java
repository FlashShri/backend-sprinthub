package com.sprinthub.service;

import com.sprinthub.dto.TaskAssignmentDTO;
import com.sprinthub.dto.TaskDTO;
import com.sprinthub.entity.Employee;
import com.sprinthub.entity.Task;
import com.sprinthub.exception.TaskServiceException;
import com.sprinthub.repository.EmployeeRepository;
import com.sprinthub.repository.TaskRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

/*    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    */
    
    public Optional<TaskDTO> getTaskDTOById(int taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        return task.map(this::convertToDTO);
    }

    private TaskDTO convertToDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setTaskId(task.getTaskId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setDomain(task.getDomain());
        dto.setStatus(task.getStatus());
        dto.setDeadlineTaskDate(task.getDeadlineTaskDate());
        dto.setStartTaskDate(task.getStartTaskDate());
        dto.setProjectId(task.getProject() != null ? task.getProject().getProjectId() : 0);
        dto.setEmployeeId(task.getEmployee() != null ? task.getEmployee().getEmployeeId() : 0);
        return dto;
    }

    
    public List<TaskDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private TaskDTO convertAllTaskToDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setTaskId(task.getTaskId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setDomain(task.getDomain());
        dto.setStatus(task.getStatus());
        dto.setStartTaskDate(task.getStartTaskDate());
        dto.setDeadlineTaskDate(task.getDeadlineTaskDate());
        dto.setProjectId(task.getProject() != null ? task.getProject().getProjectId() : 0);
        dto.setEmployeeId(task.getEmployee() != null ? task.getEmployee().getEmployeeId() : 0);
        return dto;
    }

    
/*
    public Optional<Task> getTaskById(int taskId) {
        return taskRepository.findById(taskId);
    }
*/
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

 
    public ResponseEntity<String> deleteTask(int taskId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);

        if (taskOptional.isPresent()) {
            Task deletedTask = taskOptional.get();
            taskRepository.deleteById(taskId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Task with ID " + taskId + " (" + deletedTask.getTitle() + ") successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Task with ID " + taskId + " not found.");
        }
    }


    public ResponseEntity<?> assignTaskToEmployee(int taskId, int employeeId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);

        if (taskOptional.isPresent() && employeeOptional.isPresent()) {
            Task task = taskOptional.get();
            Employee employee = employeeOptional.get();

            task.setEmployee(employee);
            Task updatedTask = taskRepository.save(task);

            TaskAssignmentDTO taskAssignmentDTO = convertToEmployeeDTO(updatedTask);
            return ResponseEntity.ok().body(taskAssignmentDTO);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new TaskServiceException("Task or Employee not found"));
        }
    }

    private TaskAssignmentDTO convertToEmployeeDTO(Task task) {
        TaskAssignmentDTO dto = new TaskAssignmentDTO();
        dto.setTaskId(task.getTaskId());
        dto.setTitle(task.getTitle());
        dto.setEmployeeId(task.getEmployee() != null ? task.getEmployee().getEmployeeId() : 0);
        return dto;
    }
    
    
    
    
  /*  public ResponseEntity<?> assignTaskToEmployee(int taskId, int employeeId) {
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
    }*/
}





/*
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
        Task savedTask = taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
      /* if (existingTask.isEmpty()) {
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
            List<Task> tasks = taskRepository.findByProject_ProjectId(projectId);
            return ResponseEntity.ok().body(tasks);
        } else {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new TaskServiceException("Project with ID " + projectId + " not found"));
        }
    }
    
    
    

    public ResponseEntity<?> getTasksByEmployeeId(int employeeId) {
    	
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isPresent()) {
            List<Task> tasks = taskRepository.findByEmployee_EmployeeId(employeeId);
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
 */ 

