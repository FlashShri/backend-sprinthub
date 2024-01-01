package com.sprinthub.controller;

import com.sprinthub.entity.Admin;
import com.sprinthub.exception.adminServiceException;
import com.sprinthub.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<Object> registerAdmin(@RequestBody Admin admin) {
        try {
            Admin registeredAdmin = adminService.register(admin);
            return new ResponseEntity<>(registeredAdmin, HttpStatus.CREATED);
        } catch (adminServiceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/getBy/{id}")
    public ResponseEntity<Admin> getAdminByEmail(@PathVariable Integer id) {
        Optional<Admin> admin = adminService.getAdminById(id);
        return admin.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{email}")
    public ResponseEntity<Admin> getAdminByEmail(@PathVariable String email) {
        Optional<Admin> admin = adminService.getAdminByEmail(email);
        return admin.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{adminId}")
    public ResponseEntity<Object> updateAdmin(@PathVariable int adminId, @RequestBody Admin updatedAdmin) {
        Admin admin = adminService.updateAdmin(adminId, updatedAdmin);
        return admin != null ?
                new ResponseEntity<>(admin, HttpStatus.OK) :
                new ResponseEntity<>("Admin not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{adminId}")
    public ResponseEntity<Object> deleteAdmin(@PathVariable int adminId) {
        adminService.deleteAdmin(adminId);
        return new ResponseEntity<>("Admin deleted successfully", HttpStatus.OK);
    }
}
