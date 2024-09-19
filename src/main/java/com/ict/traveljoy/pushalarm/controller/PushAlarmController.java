package com.ict.traveljoy.pushalarm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.ict.traveljoy.pushalarm.service.PushAlarmDTO;
import com.ict.traveljoy.pushalarm.service.PushAlarmService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "pushAlarm", description = "알림")
@RestController
@RequestMapping("/pushalarm")
@CrossOrigin
@RequiredArgsConstructor
public class PushAlarmController {

	@Autowired
    private PushAlarmService pushAlarmService;

    @PostMapping
    public ResponseEntity<PushAlarmDTO> savePushAlarm(@RequestBody PushAlarmDTO dto) {
		try {
			PushAlarmDTO savePushAlarm = pushAlarmService.savePushAlarm(dto);
	    	if(savePushAlarm == null) {
	    		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    	}
	    	return new ResponseEntity<>(savePushAlarm,HttpStatus.CREATED);
	    	} catch(Exception e) {
	    	    e.printStackTrace();
	    	    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    	}
    }

    	
    //특정 알람 조회
    @GetMapping("/{id}")
    public ResponseEntity<PushAlarmDTO> getPushAlarmById(@PathVariable("id") Long id) {
      
    	try {
    		PushAlarmDTO pushAlarm = pushAlarmService.findById(id);
    		if(pushAlarm == null) {
    			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    		}
    		return new ResponseEntity<>(pushAlarm,HttpStatus.OK);
    	}catch(Exception e) {
    		e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    }

    //모든 알람 보기
    @GetMapping("/active")
    public ResponseEntity<List<PushAlarmDTO>> getActivePushAlarms() {
    	try {
    		List<PushAlarmDTO> pushAlarmAll = pushAlarmService.findAllActive();
    		if(pushAlarmAll == null) {
    			return new ResponseEntity<>(null,HttpStatus.OK);
    		}
    		return new ResponseEntity<>(pushAlarmAll,HttpStatus.OK);
    	}catch(Exception e) {
    		e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<PushAlarmDTO>> getAllPushAlarms() {
    	try {
    		List<PushAlarmDTO> pushAlarmAll = pushAlarmService.findAll();
    		if(pushAlarmAll == null) {
    			return new ResponseEntity<>(null,HttpStatus.OK);
    		}
    		return new ResponseEntity<>(pushAlarmAll,HttpStatus.OK);
    	}catch(Exception e) {
    		e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    }

    @PutMapping("/{id}")
    public ResponseEntity<PushAlarmDTO> updatePushAlarm(@PathVariable("id") Long id, @RequestBody PushAlarmDTO dto) {
      
    	try {
    		
    		PushAlarmDTO updateAlarm = pushAlarmService.updatePushAlarm(id, dto);
    		if(updateAlarm == null) {
    			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    		}
    		return new ResponseEntity<>(updateAlarm,HttpStatus.OK);
    		
    	}catch(Exception e) {
    		e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
    	}
    
    	
    }

    @DeleteMapping("/{id}")
    public String deletePushAlarm(@PathVariable("id") Long id) {
        pushAlarmService.deleteAlarm(id);
           return "알림 삭제 성공";
        
    }
}