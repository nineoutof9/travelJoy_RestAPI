package com.ict.traveljoy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("area/transport")
public class transportController{
	
	@GetMapping("/view")
	public String view(@RequestParam String param) {
		return "";
	}	
}