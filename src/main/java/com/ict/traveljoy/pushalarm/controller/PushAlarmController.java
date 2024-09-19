package com.ict.traveljoy.pushalarm.controller;

import java.util.List;
import java.util.Map;

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

import com.ict.traveljoy.controller.CheckContainsUseremail;
import com.ict.traveljoy.pushalarm.service.PushAlarmDTO;
import com.ict.traveljoy.pushalarm.service.PushAlarmSendDTO;
import com.ict.traveljoy.pushalarm.service.PushAlarmService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Tag(name = "pushAlarm", description = "알림")
@RestController
@RequestMapping("/pushalarm")
@CrossOrigin
@RequiredArgsConstructor
public class PushAlarmController {

	@Autowired
	private PushAlarmService pushAlarmService;
	private final CheckContainsUseremail checkUser;

	@GetMapping("/all") //모든 전송내역 - 관리자단
	public ResponseEntity<List<PushAlarmSendDTO>> getAllPushAlarmsSent() {
		try {
			List<PushAlarmSendDTO> pushAlarmAll = pushAlarmService.findAll();
			if(pushAlarmAll == null) {
				return new ResponseEntity<>(null,HttpStatus.OK);
			}
			return new ResponseEntity<>(pushAlarmAll,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/all/user") // 모든 알람 내역 -유저단
	public ResponseEntity<List<PushAlarmSendDTO>> getAllPushAlarms(HttpServletRequest request) {
		String useremail = checkUser.checkContainsUseremail(request);

		try {
			List<PushAlarmSendDTO> pushAlarmAll = pushAlarmService.findAllforUser(useremail);
			if(pushAlarmAll == null) {
				return new ResponseEntity<>(null,HttpStatus.OK);
			}
			return new ResponseEntity<>(pushAlarmAll,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
	
	
	@PostMapping // 알람 전송 - 관리자단
	public ResponseEntity<PushAlarmSendDTO> savePushAlarm(@RequestBody Map<String, String> map ,HttpServletRequest request) {
		String useremail = checkUser.checkContainsUseremail(request);
		String title = (String)map.get("title");
		String content = (String)map.get("content");
		String receiveremail = (String)map.get("receiver"); //수신자의 useremail
		System.out.println("==============="+receiveremail);
		
		try {
			PushAlarmSendDTO savePushAlarm = pushAlarmService.savePushAlarm(title,content,receiveremail,useremail);
			if(savePushAlarm == null) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(savePushAlarm,HttpStatus.CREATED);
//			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


}