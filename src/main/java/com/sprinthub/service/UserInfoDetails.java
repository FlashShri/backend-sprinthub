package com.sprinthub.service;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sprinthub.entity.UserInfo; 
  
public class UserInfoDetails implements UserDetails { 
  
    private String email; 
    private String password; 
    private List<GrantedAuthority> authorities; 
  
    public UserInfoDetails(UserInfo userInfo) { 
        email = userInfo.getEmail(); 
        password = userInfo.getPassword(); 
        authorities = Arrays.stream(userInfo.getRoles().split(",")) 
                .map(SimpleGrantedAuthority::new) 
                .collect(Collectors.toList()); 
    } 
  
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { 
        return authorities; 
    } 
  
    @Override
    public String getPassword() { 
        return password; 
    } 
  
    @Override
    public String getUsername() { 
        return email; 
    } 
  
    @Override
    public boolean isAccountNonExpired() { 
        return true; 
    } 
  
    @Override
    public boolean isAccountNonLocked() { 
        return true; 
    } 
  
    @Override
    public boolean isCredentialsNonExpired() { 
        return true; 
    } 
  
    @Override
    public boolean isEnabled() { 
        return true; 
    } 
}
