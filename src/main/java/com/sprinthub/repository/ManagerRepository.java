package com.sprinthub.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sprinthub.entity.Admin;
import com.sprinthub.entity.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {
	public Optional<Manager> findByEmail(String Email);
	
	Manager findByEmailAndPassword(String email, String password);

}


