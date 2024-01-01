package com.sprinthub.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sprinthub.dto.DesignationStatus;
import com.sprinthub.entity.Designation;
import com.sprinthub.exception.DesignationException;
import com.sprinthub.service.DesignationService;

@RestController
@CrossOrigin
public class DesignationController {

	@Autowired
	private DesignationService designationService;
	
	@PostMapping("/designation")
    public ResponseEntity<DesignationStatus> addDesignation(@RequestBody Designation designation) {
        try {
            designationService.addDesignation(designation);

            DesignationStatus status = new DesignationStatus();
            status.setStatus("Designation added successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(status);
        } catch (Exception e) {
            // Handle other exceptions if needed
            DesignationStatus status = new DesignationStatus();
            status.setStatus("Error adding designation");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(status);
        }
    }

    @GetMapping("/designation/{id}")
    public ResponseEntity<Object> getDesignationById(@PathVariable int id) {
        try {
            Optional<Designation> designation = designationService.getDesignationById(id);

            if (designation.isPresent()) {
                return ResponseEntity.ok(designation.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Designation not found");
            }
        } catch (DesignationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
		
	 @DeleteMapping("/designation/{id}")
	    public ResponseEntity<String> deleteDesignation(@PathVariable int id) {
	        try {
	            designationService.deleteDesignation(id);
	            return ResponseEntity.ok("Designation deleted successfully");
	        } catch (DesignationException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Designation not found");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
	        }
	    }
	
	 @PutMapping("/designation/{id}")
	    public ResponseEntity<DesignationStatus> updateDesignation(@PathVariable int id, @RequestBody Designation updatedDesignation) {
	        try {
	            designationService.updateDesignation(id, updatedDesignation);

	            DesignationStatus status = new DesignationStatus();
	            status.setStatus("Designation updated successfully");
	            return ResponseEntity.ok(status);
	        } catch (DesignationException e) {
	        	  DesignationStatus status = new DesignationStatus();
		            status.setStatus("Designation not found");
	        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(status);
	        } catch (Exception e) {
	            // Handle other exceptions if needed
	            DesignationStatus status = new DesignationStatus();
	            status.setStatus("Error updating designation");
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(status);
	        }
	    }
}