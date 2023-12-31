// AssignmentMappingService.java

package com.sprinthub.service;

import com.sprinthub.entity.AssignmentMapping;
import com.sprinthub.entity.Employee;
import com.sprinthub.entity.Project;
import com.sprinthub.exception.AssignmentMappingServiceException;
import com.sprinthub.repository.AssignmentMappingRepository;
import com.sprinthub.repository.EmployeeRepository;
import com.sprinthub.repository.ProjectRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class AssignmentMappingService {

    @Autowired
    private AssignmentMappingRepository assignmentMappingRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public AssignmentMapping mapEmployeeToProject(int employeeId, int projectId) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        Optional<Project> projectOptional = projectRepository.findById(projectId);

        if (employeeOptional.isPresent() && projectOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            Project project = projectOptional.get();

            AssignmentMapping assignmentMapping = new AssignmentMapping();
            assignmentMapping.setEmployee(employee);
            assignmentMapping.setProject(project);

            return assignmentMappingRepository.save(assignmentMapping);
        } else {
            throw new AssignmentMappingServiceException("Employee or Project not found");
        }
    }
}

