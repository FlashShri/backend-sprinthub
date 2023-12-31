package com.sprinthub.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sprinthub.entity.Designation;
import com.sprinthub.exception.DesignationException;
import com.sprinthub.repository.DesignationRepository;

@Service
@Transactional
public class DesignationService {
	
	@Autowired
	private DesignationRepository designationRepository;
	
	public void addDesignation(Designation designation) {
		designationRepository.save(designation);
	}
	
	public Optional<Designation> getDesignationById(int id) {
        Optional<Designation> designation = designationRepository.findById(id);
        if(designation.isPresent()) {
        	return designation;
        }
        else {
        	throw new DesignationException("Id is not found");
        }
    }

    public void deleteDesignation(int id) {
  
        Optional<Designation> designation = designationRepository.findById(id);
        if(designation.isPresent()) {
        	 designationRepository.deleteById(id);
        }
        else {
        	throw new DesignationException("Id is not found");
        }
    }
	
    
    public void updateDesignation(int id, Designation updatedDesignation) throws DesignationException {
        Optional<Designation> existingDesignationOptional = designationRepository.findById(id);

        if (existingDesignationOptional.isPresent()) {
            Designation existingDesignation = existingDesignationOptional.get();

            // Update title field of existingDesignation with data from updatedDesignation
            existingDesignation.setTitle(updatedDesignation.getTitle());

            designationRepository.save(existingDesignation);
        } else {
            throw new DesignationException("Designation not found for id: " + id);
        }
    }

	
}