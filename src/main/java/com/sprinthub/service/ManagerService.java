package com.sprinthub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sprinthub.dto.ManagerDTO;
import com.sprinthub.entity.Admin;
import com.sprinthub.entity.Manager;
import com.sprinthub.exception.ManagerServiceException;
import com.sprinthub.repository.ManagerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ManagerService {
    @Autowired
    private ManagerRepository managerRepository;

    public ResponseEntity<?> register(Manager manager) {
        Optional<Manager> existingManager = managerRepository.findByEmail(manager.getEmail());

        if (existingManager.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ManagerServiceException("Manager already exists!"));
        } else {
            Manager savedManager = managerRepository.save(manager);
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

    public ResponseEntity<?> updateManager(Integer id, Manager updatedManager) {
        if (managerRepository.existsById(id)) {
            updatedManager.setManagerId(id);
            Manager updated = managerRepository.save(updatedManager);
            return ResponseEntity.ok().body(updated);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<?> deleteManager(Integer id) {
        if (managerRepository.existsById(id)) {
            managerRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
