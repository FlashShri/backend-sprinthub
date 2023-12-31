package com.sprinthub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprinthub.entity.AssignmentMapping;

public interface AssignmentMappingRepository extends JpaRepository<AssignmentMapping, Integer> {
    // Add custom query methods if needed
}
