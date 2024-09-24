package com.ict.traveljoy.pushalarm.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

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
	

	@GetMapping("/active/user") // 안읽은 알람 내역 -유저단
	public ResponseEntity<List<PushAlarmSendDTO>> getActivePushAlarms(HttpServletRequest request) {
		String useremail = checkUser.checkContainsUseremail(request);

		try {
			List<PushAlarmSendDTO> pushAlarmAll = pushAlarmService.findActiveforUser(useremail);
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
	public ResponseEntity<PushAlarmSendDTO> savePushAlarm(@RequestBody Map<String, Object> map ,HttpServletRequest request) {
		String useremail = checkUser.checkContainsUseremail(request);
		String title = (String)map.get("title");
		String content = (String)map.get("content");
		String receiveremails = (String)map.get("receiver"); //수신자의 useremail, 배열로 받기
		
		try {
			PushAlarmSendDTO savePushAlarm = pushAlarmService.savePushAlarm(title,content,receiveremails,useremail);
			if(savePushAlarm == null) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(savePushAlarm,HttpStatus.CREATED);
		} catch(NoSuchElementException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST); //400
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/scheduleAlarm") // 알람 예약 전송 - 관리자단
	public ResponseEntity<PushAlarmSendDTO> schedulePushAlarm(@RequestBody Map<String, Object> map, HttpServletRequest request) {
	    String useremail = checkUser.checkContainsUseremail(request);
	    String title = (String) map.get("title");
	    String content = (String) map.get("content");
	    String receiveremails = (String) map.get("receiver"); // 수신자의 useremail, 배열로 받기
	    String sendDate = (String) map.get("sendDate"); // 예약 전송 시간을 받음

	    try {
	        // 예약 전송 시간을 LocalDateTime으로 변환
	        LocalDateTime scheduledDate = LocalDateTime.parse(sendDate);

	        PushAlarmSendDTO savePushAlarm = pushAlarmService.schedulePushAlarm(title, content, receiveremails, useremail, scheduledDate);
	        if (savePushAlarm == null) {
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        }
	        return new ResponseEntity<>(savePushAlarm, HttpStatus.CREATED);
	    } catch (NoSuchElementException e) {
	        e.printStackTrace();
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500
	    }
	}
	
	
	
	@PostMapping("/readAll") //알람 읽기
	public ResponseEntity<PushAlarmSendDTO> readAllAlarm(HttpServletRequest request) {
		String useremail = checkUser.checkContainsUseremail(request);
		
		try {
			boolean success = pushAlarmService.readAllAlarm(useremail);
			if(success) return new ResponseEntity<>(HttpStatus.OK);
			else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			
//			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/read/{alarm_id}") //알람 읽기
	public ResponseEntity<PushAlarmSendDTO> readAlarm(@PathVariable("alarm_id") String alarm_id) {
		
		try {
			boolean success = pushAlarmService.readAlarm(Long.parseLong(alarm_id));
			if(success) return new ResponseEntity<>(HttpStatus.OK);
			else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@GetMapping("/users")
	public ResponseEntity<List<Map<String,String>>> getUsers(HttpServletRequest request){
		String adminemail = checkUser.checkContainsUseremail(request);
		
		try {
			List<Map<String,String>> response = pushAlarmService.getUsers(adminemail);
			if(response!=null) {
				 return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
			}
			else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

}