package com.sprinthub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprinthub.entity.Designation;
import com.sprinthub.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer>{

}
