package com.sprinthub.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sprinthub.entity.Manager;
import com.sprinthub.entity.Project;
import com.sprinthub.exception.ProjectException;
import com.sprinthub.repository.ProjectRepository;
import com.sprinthub.service.ProjectService;

@Service
@Transactional
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	
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


}

