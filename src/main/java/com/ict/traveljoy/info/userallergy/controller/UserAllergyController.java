package com.ict.traveljoy.info.userallergy.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.traveljoy.controller.CheckContainsUseremail;
import com.ict.traveljoy.info.userallergy.service.UserAllergyDTO;
import com.ict.traveljoy.info.userallergy.service.UserAllergyService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/userallergy")
@RequiredArgsConstructor
public class UserAllergyController {

	@Autowired
    private final UserAllergyService userAllergyService;
    private final CheckContainsUseremail checkUser;

    @GetMapping
    public List<UserAllergyDTO> getAllUserAllergies() {
        return userAllergyService.getAll();
    }

    @GetMapping("/{id}")
    public UserAllergyDTO getUserAllergyById(@PathVariable Long id) {
        return userAllergyService.getById(id);
    }
    
    @GetMapping("/users")
    public ResponseEntity<List<String>> getUserAllergyByUser(HttpServletRequest request) {
    	String useremail = checkUser.checkContainsUseremail(request);
    	
    	try {
    		List<String> userAllergyAll = userAllergyService.findAllergiesByUser(useremail);
			if(userAllergyAll == null) {
				return new ResponseEntity<>(null,HttpStatus.OK);
			}
			return new ResponseEntity<>(userAllergyAll,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }
    
    @PostMapping("/users")
    public ResponseEntity<List<UserAllergyDTO>> updateUserAllergies(@RequestBody Map<String, Object> map,HttpServletRequest request) {
    	String useremail = checkUser.checkContainsUseremail(request);
    	List<String> updatedAllergies = (List<String>)map.get("allergy");
    	try {
    		List<UserAllergyDTO> userAllergyAll = userAllergyService.updateUserAllergy(useremail,updatedAllergies);
			if(userAllergyAll == null) {
				return new ResponseEntity<>(null,HttpStatus.OK);
			}
			return new ResponseEntity<>(userAllergyAll,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }
    
    
}