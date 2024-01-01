package com.sprinthub.service;

//AdminService.java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprinthub.entity.Admin;
import com.sprinthub.exception.adminServiceException;
import com.sprinthub.repository.AdminRepository;

import java.util.Optional;

@Service
public class AdminService {
 @Autowired
 private AdminRepository adminRepository;
 
 public Admin register(Admin admin) {
     // Check if the employee with the given email already exists
     Optional<Admin> existingEmployee = adminRepository.findByEmail(admin.getEmail());
     
     if (existingEmployee.isPresent()) {
     	throw new adminServiceException("Admin already exists!");
     } else {
         return adminRepository.save(admin);
     }
 }

 
 public Admin getAdminByEmailAndPassword(String email, String password) {
     return adminRepository.findByEmailAndPassword(email, password);
 }
 

 public Optional<Admin> getAdminById(int adminId) {
     return adminRepository.findById(adminId);
 }
 
 public Optional<Admin> getAdminByEmail(String email) {
     return adminRepository.findByEmail(email);
 }

 public Admin updateAdmin(int adminId, Admin updatedAdmin) {
     if (adminRepository.existsById(adminId)) {
         updatedAdmin.setAdminId(adminId);
         return adminRepository.save(updatedAdmin);
     }
     return null; // or throw an exception
 }

 public void deleteAdmin(int adminId) {
     adminRepository.deleteById(adminId);
 }
}
