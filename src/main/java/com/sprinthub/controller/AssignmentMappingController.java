// AssignmentMappingController.java

package com.sprinthub.controller;

import com.sprinthub.entity.AssignmentMapping;
import com.sprinthub.service.AssignmentMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class AssignmentMappingController {

    @Autowired
    private AssignmentMappingService assignmentMappingService;

    @PostMapping("/api/project-employee-mapping")
    public ResponseEntity<AssignmentMapping> mapEmployeeToProject(
            @RequestParam("employeeId") int employeeId,
            @RequestParam("projectId") int projectId) {
        try {
            AssignmentMapping assignmentMapping = assignmentMappingService.mapEmployeeToProject(employeeId, projectId);
            return new ResponseEntity<>(assignmentMapping, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Employee or Project not found
        }
    }
}
