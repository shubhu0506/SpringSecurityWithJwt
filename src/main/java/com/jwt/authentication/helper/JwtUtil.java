package com.jwt.authentication.helper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

//methods -for generating token
//validate
//isExp
//Util class for JWT

@Component
public class JwtUtil {
		
	    private static final long serialVersionUID=-255018516526007488L;
	    
	    private static final long JWT_TOKEN_VALIDITY=10 * 60 * 60 * 5;
	    
	    private String SECRET_KEY = "secret";

	    public String getUsernameFromToken(String token) {return getClaimFromToken(token,Claims::getSubject);}
	    public Date getExpirationDateFromToken(String token) {return getClaimFromToken(token,Claims::getExpiration);}
	    
	    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
	        final Claims claims = getAllClaimsFromToken(token);
	        return claimsResolver.apply(claims);
	    }
	    
	    private Claims getAllClaimsFromToken(String token){
	        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	    }
	    
	    private Boolean isTokenExpired(String token) {
	    	final Date expiration=getExpirationDateFromToken(token);
	        return expiration.before(new Date());
	    }
	    
	    public String generateToken(UserDetails userDetails)
	    {
	    	Map<String, Object> claims = new HashMap<>();
	        return doGenerateToken(claims, userDetails.getUsername());
	    }
	    
	    public String doGenerateToken(Map<String, Object> claims, String subject) 
	    {
	    	return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
	                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	    }
	    
	    public Boolean validateToken(String token, UserDetails userDetails) {
	        final String username = getUsernameFromToken(token);
	        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	    } 
	   

}
