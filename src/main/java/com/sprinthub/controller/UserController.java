package com.sprinthub.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sprinthub.entity.Admin;
import com.sprinthub.entity.AuthRequest;
import com.sprinthub.entity.UserInfo;
import com.sprinthub.exception.adminServiceException;
import com.sprinthub.service.JwtService;
import com.sprinthub.service.UserInfoService; 
 
@RestController
@RequestMapping("/auth") 
@CrossOrigin(origins = "http://localhost:3000")
public class UserController { 
  
    @Autowired
    private UserInfoService service; 
  
    @Autowired
    private JwtService jwtService; 
  
    @Autowired
    private AuthenticationManager authenticationManager; 
  
    @GetMapping("/welcome") 
    public String welcome() { 
        return "Welcome this endpoint is not secure"; 
    } 
  
    @PostMapping("/register") 
    public  ResponseEntity<Object> addNewUser(@RequestBody UserInfo userInfo) { 
    	Object obj = null ;
        try {
        	 obj = service.addUser(userInfo); 
            
            return new ResponseEntity<>(obj, HttpStatus.CREATED);
        } catch (adminServiceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    	
       
    } 
  
    @GetMapping("/manager/managerProfile") 
    @PreAuthorize("hasAuthority('MANAGER')") 
    public String managerProfile() { 
        return "Welcome to Manger Profile"; 
    } 
  
    @GetMapping("/employee/employeeProfile") 
    @PreAuthorize("hasAuthority('EMPLOYEE')") 
    public String employeeProfile() { 
        return "Welcome to employee Profile"; 
    } 
    
    @GetMapping("/admin/adminProfile") 
    @PreAuthorize("hasAuthority('ADMIN')") 
    public String adminProfile() { 
        return "Welcome to Admin Profile"; 
    } 
  
    @PostMapping("/generateToken") 
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) { 
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())); 
        if (authentication.isAuthenticated()) { 
            return jwtService.generateToken(authRequest.getUsername()); 
        } else { 
            throw new UsernameNotFoundException("invalid user request !"); 
        } 
    } 
  
}
