package com.ict.traveljoy.info.userinterest.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.traveljoy.controller.CheckContainsUseremail;
import com.ict.traveljoy.info.userinterest.service.UserInterestDTO;
import com.ict.traveljoy.info.userinterest.service.UserInterestService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/userinterest")
@RequiredArgsConstructor
public class UserInterestController {

    private final UserInterestService userInterestService;
    private final CheckContainsUseremail checkUser;

    @PostMapping
    public UserInterestDTO createUserInterest(@RequestBody UserInterestDTO dto) {
        return userInterestService.saveUserInterest(dto);
    }

    @GetMapping("/{id}")
    public UserInterestDTO getUserInterestById(@PathVariable Long id) {
        return userInterestService.findById(id);
    }

    @GetMapping
    public List<UserInterestDTO> getAllUserInterests() {
        return userInterestService.findAll();
    }

//    @PutMapping("/{id}")
//    public UserInterestDTO updateUserInterest(@PathVariable Long id, @RequestBody UserInterestDTO dto) {
//        return userInterestService.updateUserInterest(id, dto);
//    }
    
    @GetMapping("/users")
    public ResponseEntity<List<String>> getUserAllergyByUser(HttpServletRequest request) {
    	String useremail = checkUser.checkContainsUseremail(request);
    	
    	try {
    		List<String> userAllergyAll = userInterestService.findInterestsByUser(useremail);
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
    public ResponseEntity<List<UserInterestDTO>> updateUserInterests(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        String useremail = checkUser.checkContainsUseremail(request);
        List<String> updatedInterests = (List<String>) map.get("interest");
        try {
            List<UserInterestDTO> userInterestAll = userInterestService.updateUserInterest(useremail, updatedInterests);
            if (userInterestAll == null) {
                return new ResponseEntity<>(null, HttpStatus.OK);
            }
            return new ResponseEntity<>(userInterestAll, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteUserInterest(@PathVariable Long id) {
        userInterestService.deleteById(id);
    }
}