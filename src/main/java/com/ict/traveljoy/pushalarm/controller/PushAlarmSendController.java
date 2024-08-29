package com.ict.traveljoy.pushalarm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.traveljoy.pushalarm.service.PushAlarmSendDTO;
import com.ict.traveljoy.pushalarm.service.PushAlarmSendService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pushalarm")
@RequiredArgsConstructor
public class PushAlarmSendController {

	@Autowired
    private PushAlarmSendService pushAlarmSendService;

    @PostMapping("/send")
    public ResponseEntity<PushAlarmSendDTO> savePushAlarmSend(@RequestBody PushAlarmSendDTO dto) {
     
    	try {
    		PushAlarmSendDTO saveAlarmSend = pushAlarmSendService.savePushAlarmSend(dto);
    		if(saveAlarmSend == null) {
    			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    		}
    		return new ResponseEntity<>(saveAlarmSend,HttpStatus.CREATED);
    	} catch(Exception e) {
    		e.printStackTrace();
    		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    }

    @GetMapping("/send/{id}")
    public ResponseEntity<PushAlarmSendDTO> getPushAlarmSendById(@PathVariable("id") Long id) {
    	
    	try {
	    	PushAlarmSendDTO getAlarmSend = pushAlarmSendService.findById(id);
	    	if(getAlarmSend == null) {
	    		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    	}
    	return new ResponseEntity<>(getAlarmSend,HttpStatus.OK);
    	} catch (Exception e) {
    		e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	
    }

    @GetMapping("/send/all")
    public ResponseEntity<List<PushAlarmSendDTO>> getAllPushAlarmSends() {
    
    	try {
	    	List<PushAlarmSendDTO> allAlarmSend = pushAlarmSendService.findAll();
	    	if(allAlarmSend == null) {
	    		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    	}
	    	return new ResponseEntity<>(allAlarmSend, HttpStatus.OK);
    	}catch(Exception e) {
    		e.printStackTrace();
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
    	}
    }

    @DeleteMapping("/send/{id}")
    public ResponseEntity<String> deletePushAlarmSend(@PathVariable("id") Long id) {
       try {
    	pushAlarmSendService.deleteById(id);
    	return new ResponseEntity<>("알림이 삭제되었습니다",HttpStatus.OK);
       }catch(Exception e) {
    	   e.printStackTrace();
			return new ResponseEntity<>("알림삭제에 실패하였습니다",HttpStatus.NOT_FOUND);	
       }
    }
}
