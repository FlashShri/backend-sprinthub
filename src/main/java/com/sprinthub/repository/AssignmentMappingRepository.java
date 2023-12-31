package com.sprinthub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sprinthub.entity.AssignmentMapping;
import com.sprinthub.entity.Employee;

public interface AssignmentMappingRepository extends JpaRepository<AssignmentMapping, Integer> {

	 @Query("SELECT ae.employee FROM AssignmentMapping ae WHERE ae.project.projectId = :projectId")
	    List<Employee> findEmployeesByProjectId(@Param("projectId") int projectId);
	
	
}
