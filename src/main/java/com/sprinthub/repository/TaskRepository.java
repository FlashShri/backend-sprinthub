package com.sprinthub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprinthub.entity.Project;
import com.sprinthub.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Integer>{

	 List<Task> findByStatus(Task.TaskStatus status);
	 
	 List<Task> findByProject(Project project);


/*	public List<Task> findByProject_ProjectId(int projectId);
	
	public List<Task> findByEmployee_EmployeeId(int employeeId);

	public List<Task> findByDomain(String domain);
	*/
}
