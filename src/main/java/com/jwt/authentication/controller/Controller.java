package com.jwt.authentication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	@GetMapping("/welcome")
	public String welcome()
	{
		String text="this is private page";
		text +="the page is not allowed to unaunthenticated users";
		return text;
	}
	
	@GetMapping("/getusers")
	public String getUsers()
	{
		return "{\"name\":\"Subrat\"}";
	}
}
