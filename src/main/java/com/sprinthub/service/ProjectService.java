package com.sprinthub.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sprinthub.dto.PostProjectDTO;
import com.sprinthub.dto.ProjectDTO;
import com.sprinthub.dto.ProjectStatus;
import com.sprinthub.dto.TaskDTO;
import com.sprinthub.entity.AssignmentMapping;
import com.sprinthub.entity.Employee;
import com.sprinthub.entity.Manager;
import com.sprinthub.entity.Project;
import com.sprinthub.entity.Task;
import com.sprinthub.exception.ProjectException;
import com.sprinthub.repository.EmployeeRepository;
import com.sprinthub.repository.ManagerRepository;
import com.sprinthub.repository.ProjectRepository;

@Service
@Transactional
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
    private EmployeeRepository employeeRepository;
	
	
    @Autowired
    private ManagerRepository managerRepository;
    
    @Autowired
    private ModelMapper mapper;

	
	public void addProject(PostProjectDTO dto) {
		
		// map dto to project class
		Project project= mapper.map(dto, Project.class);
		project.setCreateDate(LocalDate.now());
		
		projectRepository.save(project);
	}
	
	
	public Optional<Project> getProjectById(int id) {
        Optional<Project> project = projectRepository.findById(id);
        if(project.isPresent()) {
        	return project;
        }
        else {
        	throw new ProjectException("Id is not found");
        }
    }

    public void deleteProject(int id) {
  
        Optional<Project> project = projectRepository.findById(id);
        if(project.isPresent()) {
        	 projectRepository.deleteById(id);
        }
        else {
        	throw new ProjectException("Id is not found");
        }
    }
	
    
    public void updateProject(int id, PostProjectDTO dto) throws ProjectException {
    	
    	
        Optional<Project> existingProjectOptional = projectRepository.findById(id);

        if (existingProjectOptional.isPresent()) {
        	Project existingProject = existingProjectOptional.get();

            // Update title field of existingDesignation with data from updatedDesignation
        	// map dto to project class
			Project updatedProject= mapper.map(dto, Project.class);
        	
			/*
			 * existingProject.setProjectTitle(updatedProject.getProjectTitle());
			 * 
			 * existingProject.setManager(updatedProject.getManager());
			 * existingProject.setCreateDate(updatedProject.getCreateDate());
			 * existingProject.setProjectEmployeeMappings(updatedProject.
			 * getProjectEmployeeMappings());
			 */
			updatedProject.setProjectId(id);
			 
            projectRepository.save(updatedProject);
        } else {
            throw new ProjectException("Project not found for id: " + id);
        }
    }
    
    
    public List<Project> getProjectsByEmployeeId(int employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        if (employee != null) {
            return new ArrayList<>(employee.getProjectEmployeeMappings()
                .stream()
                .map(AssignmentMapping::getProject)
                .collect(Collectors.toSet()));
        }
        return Collections.emptyList();
    }

	public List<ProjectDTO
	> getAllProjects() {
		
		
		  List<Project> proj  =projectRepository.findAll();
		  for( Project p : proj) {
			  System.out.println( p.getProjectId());
		  }
		List<ProjectDTO> pdtoList = new ArrayList<>();
		
		for( Project p : proj ) {
			 ProjectDTO dto= mapper.map(p , ProjectDTO.class);
			 
			 dto.setId(p.getProjectId());
			 pdtoList.add(dto);
		}
		return pdtoList;
	}






    public ProjectStatus assignManagerToProject(int projectId, int managerId) {
        Project project = projectRepository.findById(projectId).orElse(null);
        Manager manager = managerRepository.findById(managerId).orElse(null);

        ProjectStatus projectStatus = new ProjectStatus();
        
        if (project == null || manager == null) {
            projectStatus.setStatus("Project or Manager not found");
            return projectStatus;
        }

        project.setManager(manager);
        projectRepository.save(project);
        
        projectStatus.setStatus("Manager assigned successfully to the project.");
        return projectStatus;
       
    }


	public List<Project> getProjectsByManagerId(int managerId) {
		// TODO Auto-generated method stub
		
		 Optional<Manager> manager = managerRepository.findById(managerId);
		 
		 
		 
		   List<Project> projectList = projectRepository.findByManager(manager.get());
		 
		return projectList;
		
	}


	public Optional<ProjectDTO> getProjectDTOById(int projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        return project.map(this::convertToDTO);
	}
	
    private ProjectDTO convertToDTO(Project project) {
    	ProjectDTO dto = new ProjectDTO();
        dto.setId(project.getProjectId());
        dto.setProjectTitle(project.getProjectTitle());
        dto.setProjectDescription(project.getProjectDescription());
        dto.setCreateDate(project.getCreateDate());
        dto.setManager(project.getManager());
        return dto;
    }
}
	

