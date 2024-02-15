package com.sprinthub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprinthub.entity.Manager;
import com.sprinthub.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer>{

	List<Project> findByManager(Manager manager);

}

//List<Project> findAllProjectByManagerId(int managerId);
