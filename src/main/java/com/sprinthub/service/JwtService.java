package com.sprinthub.service;

import io.jsonwebtoken.Claims; 
import io.jsonwebtoken.Jwts; 
import io.jsonwebtoken.SignatureAlgorithm; 
import io.jsonwebtoken.io.Decoders; 
import io.jsonwebtoken.security.Keys; 
import org.springframework.security.core.userdetails.UserDetails; 
import org.springframework.stereotype.Component; 
  
import java.security.Key; 
import java.util.Date; 
import java.util.HashMap; 
import java.util.Map; 
import java.util.function.Function; 
  
@Component
public class JwtService { 
  
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437"; 
    
    public static final long CLOCK_SKEW_MS = 30000; // 30 seconds clock skew
    
    public String generateToken(String userName) { 
        Map<String, Object> claims = new HashMap<>(); 
        return createToken(claims, userName); 
    } 
  
    private String createToken(Map<String, Object> claims, String userName) { 
        return Jwts.builder() 
                .setClaims(claims) 
                .setSubject(userName) 
                .setIssuedAt(new Date(System.currentTimeMillis())) 
                .setExpiration(new Date(System.currentTimeMillis() + 1000 *  60 * 60 * 24)) 
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact(); 
    } 
  
    private Key getSignKey() { 
        byte[] keyBytes= Decoders.BASE64.decode(SECRET); 
        return Keys.hmacShaKeyFor(keyBytes); 
    } 
  
    public String extractUsername(String token) { 
        return extractClaim(token, Claims::getSubject); 
    } 
  
    public Date extractExpiration(String token) { 
        return extractClaim(token, Claims::getExpiration); 
    } 
  
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) { 
        final Claims claims = extractAllClaims(token); 
        return claimsResolver.apply(claims); 
    } 
  
    private Claims extractAllClaims(String token) { 
        return Jwts 
                .parserBuilder() 
                .setSigningKey(getSignKey()) 
                .build() 
                .parseClaimsJws(token) 
                .getBody(); 
    } 
  
    private Boolean isTokenExpired(String token) { 
        return extractExpiration(token).before(new Date()); 
    } 
//  
    public Boolean validateToken(String token, UserDetails userDetails) { 
        final String username = extractUsername(token); 
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token)); 
    } 
//    
//    // Method to check if token is expired with clock skew consideration
//    private Boolean isTokenExpired(String token) {
//        final Date expiration = extractExpiration(token);
//        return expiration.before(new Date(System.currentTimeMillis() + CLOCK_SKEW_MS));
//    }
//
//    // Method to validate token with clock skew
//    public Boolean validateToken(String token, UserDetails userDetails) {
//        final String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
//  
  
}
