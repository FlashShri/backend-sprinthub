package com.sprinthub.controller;

import com.sprinthub.entity.AssignmentMapping;
import com.sprinthub.service.AssignmentMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/assignment-mappings")
public class AssignmentMappingController {

    private final AssignmentMappingService assignmentMappingService;

    @Autowired
    public AssignmentMappingController(AssignmentMappingService assignmentMappingService) {
        this.assignmentMappingService = assignmentMappingService;
    }

    @PostMapping
    public ResponseEntity<AssignmentMapping> createAssignmentMapping(@RequestBody AssignmentMapping assignmentMapping) {
        AssignmentMapping savedMapping = assignmentMappingService.saveAssignmentMapping(assignmentMapping);
        return new ResponseEntity<>(savedMapping, HttpStatus.CREATED);
    }


    @PostMapping("/mapping/{employeeId}/to/{projectId}")
    public ResponseEntity<AssignmentMapping> mapEmployeeToProject(
            @PathVariable("employeeId") int employeeId,
            @PathVariable("projectId") int projectId) {
        try {
        	
        	System.out.println( employeeId) ;
            AssignmentMapping assignmentMapping = assignmentMappingService.mapEmployeeToProject(employeeId, projectId);
            return new ResponseEntity<>(assignmentMapping, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Employee or Project not found
        }
    }
	
    
    
}



// AssignmentMappingController.java
/*
package com.sprinthub.controller;

import com.sprinthub.entity.AssignmentMapping;
import com.sprinthub.service.AssignmentMappingService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assignment-mappings")
public class AssignmentMappingController {

  @Autowired
    private AssignmentMappingService assignmentMappingService;


	



    @Autowired
    private AssignmentMappingService assignmentMappingService;

    @PostMapping
    public ResponseEntity<AssignmentMapping> createAssignmentMapping(@RequestBody AssignmentMapping assignmentMapping) {
        AssignmentMapping savedMapping = assignmentMappingService.saveAssignmentMapping(assignmentMapping);
        return ResponseEntity.ok(savedMapping);
    }

    @GetMapping
    public ResponseEntity<List<AssignmentMapping>> getAllAssignmentMappings() {
        List<AssignmentMapping> assignmentMappings = assignmentMappingService.getAllAssignmentMappings();
        return ResponseEntity.ok(assignmentMappings);
    
    
    }
	
}
*/