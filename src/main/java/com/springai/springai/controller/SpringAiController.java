package com.springai.springai.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringAiController {
	
	
	@GetMapping("/hello")
	public String hello() {
		return " Hello spring boot developer";
	}
	
	@GetMapping("/spring")
	public String springdev() {
		return " Hello spring boot developer springdev";
	}

}
