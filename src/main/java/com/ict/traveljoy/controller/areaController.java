package com.ict.traveljoy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/area")
public class areaController{
	
	
	@GetMapping("/tourspot")
	public String tourspot(@RequestParam String param) {
		return " ";
		
	}
	@GetMapping("/accomdation")
	public String accomdation(@RequestParam String param) {
		return " ";
	}
	@GetMapping("/restaurant")
	public String restaurant(@RequestParam String param) {
		return " ";
	}
	
	
}


