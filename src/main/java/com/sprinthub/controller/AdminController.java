package com.sprinthub.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sprinthub.entity.Admin;
import com.sprinthub.exception.adminServiceException;
import com.sprinthub.service.AdminService;

@RestController
@CrossOrigin
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
    @Secured("Admin") 
    public ResponseEntity<Admin> getAdminById(@PathVariable Integer id) {
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

//    @GetMapping("/{email}/{password}")
//    public ResponseEntity<Admin> getAdminByEmail(@PathVariable String email, @PathVariable String password ) {
//        Optional<Admin> admin = adminService.getAdminByEmail(email);
//        password = admin.getPassword();
//        return admin.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
//                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
    
    @GetMapping("/admin")
    public ResponseEntity<Admin> getAdminByEmailAndPassword(
        @RequestParam String email,
        @RequestParam String password
    ) {
        Admin admin = adminService.getAdminByEmailAndPassword(email, password);
        
        if (admin != null) {
            return new ResponseEntity<>(admin, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Admin not found or invalid credentials
        }
    }

    
    //@CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/{adminId}")
    @Secured("Admin") 
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
