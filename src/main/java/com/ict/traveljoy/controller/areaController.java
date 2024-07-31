package com.ict.traveljoy.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.service.users.UsersDto;
import com.ict.traveljoy.service.users.UsersService;

import lombok.RequiredArgsConstructor;


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


