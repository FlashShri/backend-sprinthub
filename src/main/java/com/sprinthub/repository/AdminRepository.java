package com.sprinthub.repository;

import java.util.Optional;

//AdminRepository.java
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sprinthub.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
 // Add custom query methods if needed
	public Optional<Admin> findByEmail(String Email);
	
	Admin findByEmailAndPassword(String email, String password);
}
