package com.jwt.authentication.controller;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.authentication.helper.JwtUtil;
import com.jwt.authentication.model.JwtRequest;
import com.jwt.authentication.model.JwtResponse;
import com.jwt.authentication.services.CustomUserDetailService;

@RestController
public class JwtController {
    
	//private static Logger log = LoggerFactory.getLogger(JwtController.class);
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("/token")
	public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception
	{
		System.out.println(jwtRequest);
		try 
		{
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(),jwtRequest.getPassword()));
		}catch(UsernameNotFoundException e) 
		{
			e.printStackTrace();
			throw new Exception("Bad Credentials");
		}catch(BadCredentialsException e)
		{
			e.printStackTrace();
			throw new Exception("Bad Credentials");
		}
		UserDetails userDetails=this.customUserDetailService.loadUserByUsername(jwtRequest.getUsername());
         String token=this.jwtUtil.generateToken(userDetails);
         
         System.out.println(token);
         return ResponseEntity.ok(new JwtResponse(token));
	}
 }
