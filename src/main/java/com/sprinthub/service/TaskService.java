package com.sprinthub.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sprinthub.dto.TaskAssignmentDTO;
import com.sprinthub.dto.TaskCreateDTO;
import com.sprinthub.dto.TaskDTO;
import com.sprinthub.entity.Employee;
import com.sprinthub.entity.Project;
import com.sprinthub.entity.Task;
import com.sprinthub.entity.Task.TaskStatus;
import com.sprinthub.exception.TaskServiceException;
import com.sprinthub.repository.AssignmentMappingRepository;
import com.sprinthub.repository.EmployeeRepository;
import com.sprinthub.repository.ProjectRepository;
import com.sprinthub.repository.TaskRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private AssignmentMappingRepository assignmentMappingRepository ;
    
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
    public TaskDTO saveTask(TaskCreateDTO taskdto , int project_id) {
    	
    	 Task task = mapper.map(taskdto , Task.class);
    	 
    	 task.setStatus(Task.TaskStatus.BACKLOG);
    	 
    	  Optional<Project> project = projectRepository.findById(project_id);
    	  
    	  task.setProject( project.get());
    	 Task res = taskRepository.save(task);
    	 
    	
        return  mapper.map(res, TaskDTO.class);
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
    
    
    public List<Task> getTasksByStatus(Task.TaskStatus status){
        return taskRepository.findByStatus(status);
    }
    
	/*
	 * public Task updateTaskStatus(int taskId, TaskStatus newStatus) { Task task =
	 * taskRepository.findById(taskId) .orElseThrow(() -> new
	 * TaskServiceException("Task with ID " + taskId + " not found"));
	 * 
	 * task.setStatus(newStatus); return taskRepository.save(task); }
	 */
    
    public boolean updateTaskStatus(int taskId, TaskStatus status, int employeeId) {
    	
        Optional<Task> optionalTask = taskRepository.findById(taskId); // never be null 
        
         Optional<Employee> employee = employeeRepository.findById(employeeId);
         
        if (optionalTask.isPresent()) {
        	Task task = optionalTask.get();


        		if( status.name().equals("ACTIVE")) {
            		task.setEmployee( employee.get()); 
            		task.setStartTaskDate(LocalDate.now());
            	}
            task.setStatus(status);
            taskRepository.save(task);
            
            return true;
            
        } else {
            // Task not found
            return false;
        }
    }
    
	public List<TaskDTO> getAllTasksByEmployee(int empId) {
	
		/*
		 * Optional<Employee> employee = employeeRepository.findById(empId);
		 * 
		 * List<Project> projectList =
		 * assignmentMappingRepository.findProjectsByEmployeeId(employee.get().
		 * getEmployeeId());
		 * 
		 * 
		 * List<Task> taskList = new ArrayList<>() ; for( Project p : projectList) {
		 * 
		 * Set<Task> taskSet = p.getTasks();
		 * 
		 * for( Task t : taskSet) { taskList.add(t); } }
		 */
		  
		  
		 // return  taskList.stream().map(task -> mapper.map(task, TaskDTO.class)).collect(Collectors.toList());
		 Optional<Employee> employee = employeeRepository.findById(empId);
	        if (employee.isPresent()) {
	        	List<TaskDTO> tasklist =  assignmentMappingRepository.findProjectsByEmployeeId(employee.get().getEmployeeId())
	                    .stream()
	                    .flatMap(project -> project.getTasks().stream())
	                    .map(task -> {
	                        TaskDTO taskDTO = mapper.map(task, TaskDTO.class);
	                        taskDTO.setProjectId(task.getProject().getProjectId());
	                        taskDTO.setEmployeeId(empId);
	                        return taskDTO;
	                    }
                        )
	                    .collect(Collectors.toList());
	        	
	        	return tasklist;
	        } else {
	            return Collections.emptyList(); // return an empty list if employee is not found
	        } 
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
	 
	 
	
	public List<TaskDTO> getTasksByStatus(int empId, Predicate<Task> filter) {
	    Optional<Employee> employee = employeeRepository.findById(empId);
	    if (employee.isPresent()) {
	        List<TaskDTO> taskList = assignmentMappingRepository.findProjectsByEmployeeId(employee.get().getEmployeeId())
	                .stream()
	                .flatMap(project -> project.getTasks().stream())
	                .filter(task -> task.getEmployee() == null || task.getEmployee().getEmployeeId() == empId) // Filter tasks with no employee or matching employee ID
	                .filter(filter) // Filter tasks based on the provided predicate
	                .map(task -> {
	                    TaskDTO taskDTO = mapper.map(task, TaskDTO.class);
	                    taskDTO.setProjectId(task.getProject().getProjectId());
	                    taskDTO.setEmployeeId(empId);
	                    return taskDTO;
	                })
	                .collect(Collectors.toList());

	        return taskList;
	    } else {
	        return Collections.emptyList(); // return an empty list if employee is not found
	    }
	}
	
    public List<TaskDTO> getTasksByProjectId(int project_id) {
        // Implement the logic to fetch tasks based on project_id
    		Optional<Project> project = projectRepository.findById(project_id);
    	
        List<Task> tasks = taskRepository.findByProject(project.get());
        
        String[] employeeName = new String[tasks.size()];
        String[] projectName = new String[tasks.size()];
        int i = 0;
        for(Task t: tasks){
        	if (t.getEmployee() != null) {
                employeeName[i] = t.getEmployee().getFullName();
            } else {
                employeeName[i] = "null";
            }
            projectName[i] = t.getProject().getProjectTitle();
            i++;
        }
        
        List<TaskDTO> dtoList = tasks.stream()
        .map(task ->
        		mapper.map(task, TaskDTO.class))
        .collect(Collectors.toList());
        int j = 0;
        for (TaskDTO td : dtoList) {
        	td.setEmployeeName(employeeName[j]);
        	td.setProjectName(projectName[j]);
        }
        
        return dtoList;
    }
	 
}









