package com.sprinthub.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sprinthub.dto.ManagerDTO;
import com.sprinthub.dto.PostManagerDTO;
import com.sprinthub.entity.Manager;
import com.sprinthub.entity.Project;
import com.sprinthub.exception.ManagerServiceException;
import com.sprinthub.repository.ManagerRepository;

@Service
public class ManagerService {
    @Autowired
    private ManagerRepository managerRepository;
    
    @Autowired
    private ModelMapper mapper;

    public ResponseEntity<?> register(PostManagerDTO dto) {
        Optional<Manager> existingManager = managerRepository.findByEmail(dto.getEmail());

        if (existingManager.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ManagerServiceException("Manager already exists!"));
        } else {
        	 Manager newManager = mapper.map(dto, Manager.class);
            Manager savedManager = managerRepository.save(newManager);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedManager);
        }
    }
    
    public Manager getManagerByEmailAndPassword(String email, String password) {
        return managerRepository.findByEmailAndPassword(email, password);
    }

    public ResponseEntity<?> getManagerById(int id) {
        Optional<Manager> manager = managerRepository.findById(id);
        return manager.map(value -> ResponseEntity.ok().body(value))
                      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public List<ManagerDTO> getAllManagers() {
        List<Manager> managers = managerRepository.findAll();
        return managers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ManagerDTO convertToDTO(Manager manager) {
        ManagerDTO dto = new ManagerDTO();
        dto.setManagerId(manager.getManagerId());
        dto.setFullName(manager.getFullName());
        dto.setEmail(manager.getEmail());
        dto.setPhoneNumber(manager.getPhoneNumber());
        dto.setCity(manager.getCity());
        return dto;
    }
    
    public ResponseEntity<?> getManagerByEmail(String email) {
        Optional<Manager> manager = managerRepository.findByEmail(email);
        return manager.map(value -> ResponseEntity.ok().body(value))
                      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity<?> updateManager(Integer id, ManagerDTO updatedManager) {
        if (managerRepository.existsById(id)) {
        	
        	 Optional<Manager> manager = managerRepository.findById(id);

            updatedManager.setManagerId(id);
            
            Manager upManager = mapper.map(updatedManager, Manager.class);
            upManager.setPassword(manager.get().getPassword());
            Manager updated = managerRepository.save(upManager);
            return ResponseEntity.ok().body(updated);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    
    
    
    public ResponseEntity<?> deleteManager(Integer id) {
		/*
		 * if (managerRepository.existsById(id)) { managerRepository.deleteById(id);
		 * return ResponseEntity.noContent().build(); } return
		 * ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		 */
    	
    	Optional<Manager> managerOptional = managerRepository.findById(id);
        if (managerOptional.isPresent()) {
            Manager manager = managerOptional.get();
            // Disassociate manager from projects
            for (Project project : manager.getProjects()) {
                project.setManager(null);
            }
            managerRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
