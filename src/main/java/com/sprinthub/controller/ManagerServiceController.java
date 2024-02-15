package com.sprinthub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sprinthub.dto.ManagerDTO;
import com.sprinthub.dto.PostManagerDTO;
import com.sprinthub.entity.Manager;
import com.sprinthub.service.ManagerService;

@RestController
@CrossOrigin
@RequestMapping("/managers")
public class ManagerServiceController {

	@Autowired
	private ManagerService managerService;

	
    @PostMapping("/register")
    public ResponseEntity<?> registerManager(@RequestBody PostManagerDTO manager) {
        return managerService.register(manager);
    }

    
    @GetMapping("/manager")
    public ResponseEntity<Manager> getAdminByEmailAndPassword(
        @RequestParam String email,
        @RequestParam String password
    ) {
        Manager manager = managerService.getManagerByEmailAndPassword(email, password);
        
        if (manager != null) {
            return new ResponseEntity<>(manager, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Manager not found or invalid credentials
        }
    }

 
   	@GetMapping("/manager/{id}")
    public ResponseEntity<?> getManagerById(@PathVariable int id) {
        return managerService.getManagerById(id);
    }
   	
   	@GetMapping
    public ResponseEntity<List<ManagerDTO>> getAllManagers() {
        List<ManagerDTO> managerDTOs = managerService.getAllManagers();
        return new ResponseEntity<>(managerDTOs, HttpStatus.OK);
    }
   	
    @GetMapping("/{email}")
    public ResponseEntity<?> getManagerByEmail(@PathVariable String email) {
        return managerService.getManagerByEmail(email);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateManager(@PathVariable Integer id, @RequestBody ManagerDTO updatedManager) {
        return managerService.updateManager(id, updatedManager);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteManager(@PathVariable Integer id) {
        return managerService.deleteManager(id);
    }
}
