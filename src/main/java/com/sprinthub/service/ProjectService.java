package com.sprinthub.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sprinthub.entity.AssignmentMapping;
import com.sprinthub.entity.Employee;
import com.sprinthub.entity.Manager;
import com.sprinthub.entity.Project;
import com.sprinthub.exception.ProjectException;
import com.sprinthub.repository.EmployeeRepository;
import com.sprinthub.repository.ProjectRepository;
import com.sprinthub.service.ProjectService;

@Service
@Transactional
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
    private EmployeeRepository employeeRepository;

	
	public void addProject(Project project) {
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
	
    
    public void updateProject(int id, Project updatedProject) throws ProjectException {
        Optional<Project> existingProjectOptional = projectRepository.findById(id);

        if (existingProjectOptional.isPresent()) {
        	Project existingProject = existingProjectOptional.get();

            // Update title field of existingDesignation with data from updatedDesignation
        	existingProject.setProjectTitle(updatedProject.getProjectTitle());
        	existingProject.setProjectDiscription(updatedProject.getProjectDiscription());
        	existingProject.setManager(updatedProject.getManager());
        	existingProject.setCreateDate(updatedProject.getCreateDate());
        	existingProject.setProjectEmployeeMappings(updatedProject.getProjectEmployeeMappings());

            projectRepository.save(existingProject);
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

	public List<Project> getAllProjects() {
		return projectRepository.findAll();
	}

}

