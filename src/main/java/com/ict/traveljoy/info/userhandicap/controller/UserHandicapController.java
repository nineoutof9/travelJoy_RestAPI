package com.ict.traveljoy.info.userhandicap.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.traveljoy.controller.CheckContainsUseremail;
import com.ict.traveljoy.info.userhandicap.service.UserHandicapDTO;
import com.ict.traveljoy.info.userhandicap.service.UserHandicapService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/userhandicap")
@RequiredArgsConstructor
public class UserHandicapController {

    private final UserHandicapService userHandicapService;
    private final CheckContainsUseremail checkUser;

    @GetMapping
    public List<UserHandicapDTO> getAllUserHandicaps() {
        return userHandicapService.getAll();
    }

    @GetMapping("/{id}")
    public UserHandicapDTO getUserHandicapById(@PathVariable Long id) {
        return userHandicapService.getById(id);
    }
    
    @GetMapping("/users")
    public ResponseEntity<List<String>> getUserAllergyByUser(HttpServletRequest request) {
    	String useremail = checkUser.checkContainsUseremail(request);
    	
    	try {
    		List<String> userAllergyAll = userHandicapService.findHandicapsByUser(useremail);
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
    public ResponseEntity<List<UserHandicapDTO>> updateUserHandicaps(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        String useremail = checkUser.checkContainsUseremail(request);
        List<String> updatedHandicaps = (List<String>) map.get("handicap");
        try {
            List<UserHandicapDTO> userHandicapAll = userHandicapService.updateUserHandicap(useremail, updatedHandicaps);
            if (userHandicapAll == null) {
                return new ResponseEntity<>(null, HttpStatus.OK);
            }
            return new ResponseEntity<>(userHandicapAll, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
