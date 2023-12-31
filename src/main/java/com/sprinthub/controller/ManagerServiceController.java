package com.sprinthub.controller;

import com.sprinthub.entity.Manager;
import com.sprinthub.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/managers")
public class ManagerServiceController {

	@Autowired
	private ManagerService managerService;

	
    @PostMapping("/register")
    public ResponseEntity<?> registerManager(@RequestBody Manager manager) {
        return managerService.register(manager);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getManagerById(@PathVariable int id) {
        return managerService.getManagerById(id);
    }

    @GetMapping("/email")
    public ResponseEntity<?> getManagerByEmail(@RequestParam String email) {
        return managerService.getManagerByEmail(email);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateManager(@PathVariable Integer id, @RequestBody Manager updatedManager) {
        return managerService.updateManager(id, updatedManager);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteManager(@PathVariable Integer id) {
        return managerService.deleteManager(id);
    }
}
