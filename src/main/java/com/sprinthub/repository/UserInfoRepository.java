package com.sprinthub.repository;

 
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;

import com.sprinthub.entity.UserInfo;

import java.util.Optional; 
  
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> { 
    Optional<UserInfo> findByEmail(String username); 
}
