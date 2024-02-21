package com.sprinthub.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.sprinthub.filter.JwtAuthFilter;
import com.sprinthub.service.UserInfoService; 
  
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig { 
  
    @Autowired
    private JwtAuthFilter authFilter; 
  
    // User Creation 
    @Bean
    public UserDetailsService userDetailsService() { 
        return new UserInfoService(); 
    } 
  
    // Configuring HttpSecurity 
    
  //  @SuppressWarnings(value = { "" })
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { 
        return http.csrf().disable() 
                .authorizeHttpRequests() 
                .requestMatchers("/auth/welcome", "/auth/register", "/auth/generateToken", "/ws/**").permitAll() 
                .and() 
                .authorizeHttpRequests().requestMatchers("/auth/user/**").authenticated() 
                .and() 
                .authorizeHttpRequests().requestMatchers("/auth/admin/**").authenticated() 
                .and() 
                .authorizeHttpRequests().requestMatchers("/admins/**").authenticated()
                .and()
                .authorizeHttpRequests().requestMatchers("/project/**").authenticated()
                .and()
                .authorizeHttpRequests().requestMatchers("/managers/**").authenticated()
                .and()
                .authorizeHttpRequests().requestMatchers("/employees/**").authenticated()
                .and()
                .authorizeHttpRequests().requestMatchers("/api/**").authenticated()
                .and()
                .authorizeHttpRequests().requestMatchers("/assignment-mappings/mapping/**").authenticated()
                .and()
                .sessionManagement() 
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) 
                .and() 
                .authenticationProvider(authenticationProvider()) 
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class) 
                .build(); 
    } 
  
    // Password Encoding 
    @Bean
    public PasswordEncoder passwordEncoder() { 
        return new BCryptPasswordEncoder(); 
    } 
  
    @Bean
    public AuthenticationProvider authenticationProvider() { 
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(); 
        authenticationProvider.setUserDetailsService(userDetailsService()); 
        authenticationProvider.setPasswordEncoder(passwordEncoder()); 
        return authenticationProvider; 
    } 
  
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception { 
        return config.getAuthenticationManager(); 
    } 
  
    @Bean
    public FilterRegistrationBean coresFilter() {
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    
	    CorsConfiguration corsConfiguration = new CorsConfiguration();
	    corsConfiguration.setAllowCredentials(true);
	    corsConfiguration.addAllowedOriginPattern("*");
	    corsConfiguration.addAllowedHeader( "Authorization");
	    corsConfiguration.addAllowedHeader( "ContentType");
	    corsConfiguration.addAllowedHeader( "Accept" );
	    corsConfiguration.addAllowedHeader("POST");
	    corsConfiguration.addAllowedHeader("GET");
	    corsConfiguration.addAllowedHeader( "DELETE") ;
	    corsConfiguration.addAllowedHeader("PUT") ;
	    corsConfiguration.addAllowedHeader("OPTIONS") ;
	    corsConfiguration.setMaxAge (3600L) ;
	    
	    source.registerCorsConfiguration ( "/",corsConfiguration) ;
	    source.registerCorsConfiguration("/ws/**", corsConfiguration); // Allow CORS for WebSocket endpoint
	    
	    
	    FilterRegistrationBean bean = new FilterRegistrationBean<>(new CorsFilter(source));
		return bean;
		
    }
    
    //socket is working now
} 


