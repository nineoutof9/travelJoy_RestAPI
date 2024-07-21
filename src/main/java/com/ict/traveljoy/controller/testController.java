package com.ict.traveljoy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {

	
	@GetMapping("/api")
	public String test() {
		return "success";
	}
	
	@GetMapping("/api/hello")
	public String test2() {
		return "success hello";
	}
}
