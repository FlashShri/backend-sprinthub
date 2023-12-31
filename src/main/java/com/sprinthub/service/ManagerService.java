package com.sprinthub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sprinthub.entity.Manager;
import com.sprinthub.exception.ManagerServiceException;
import com.sprinthub.repository.ManagerRepository;

import java.util.Optional;

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

    public ResponseEntity<?> getManagerById(int id) {
        Optional<Manager> manager = managerRepository.findById(id);
        return manager.map(value -> ResponseEntity.ok().body(value))
                      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
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
