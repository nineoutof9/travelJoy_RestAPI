package com.ict.traveljoy.pushalarm.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.pushalarm.repository.PushAlarm;
import com.ict.traveljoy.pushalarm.repository.PushAlarmRepository;
import com.ict.traveljoy.pushalarm.repository.PushAlarmSend;
import com.ict.traveljoy.pushalarm.repository.PushAlarmSendRepository;
import com.ict.traveljoy.users.repository.UserRepository;
import com.ict.traveljoy.users.repository.Users;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PushAlarmService {

	private final PushAlarmRepository pushAlarmRepository;
	private final PushAlarmSendRepository pushAlarmSendRepository;
	private final UserRepository userRepository;

	// 모든 PushAlarm을 조회하는 메서드 - 관리자단
	public List<PushAlarmSendDTO> findAll() {
		List<PushAlarmSend> pushAlarms = pushAlarmSendRepository.findAll();
		List<PushAlarmSendDTO> alarmDTOs = new ArrayList<PushAlarmSendDTO>();
		String sender="";

		try {
			for(PushAlarmSend alarm:pushAlarms) {
				PushAlarmSendDTO dto = PushAlarmSendDTO.toDTO(alarm);
				dto.setReceiveUseremail(alarm.getReceiver().getEmail());
				if(alarm.getSender().equalsIgnoreCase("SYSTEM")) { // 시스템이 전송
					sender = "SYSTEM";
				}
				else { //관리자가 전송
					Users user = userRepository.findByEmail(alarm.getSender()).get();
					sender = user.getEmail();
				}
				dto.setSender(sender);
				alarmDTOs.add(dto);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return alarmDTOs;
	}

	public List<PushAlarmSendDTO> findAllforUser(String useremail) {

		Users user = userRepository.findByEmail(useremail).get();

		List<PushAlarmSend> pushAlarms = pushAlarmSendRepository.findAllByReceiver_Id(user.getId());
		List<PushAlarmSendDTO> alarmDTOs = new ArrayList<PushAlarmSendDTO>();
		String sender="";

		try {
			for(PushAlarmSend alarm:pushAlarms) {
				PushAlarmSendDTO dto = PushAlarmSendDTO.toDTO(alarm);
				dto.setReceiveUseremail(alarm.getReceiver().getEmail());
				if(alarm.getSender().equalsIgnoreCase("SYSTEM")) { // 시스템이 전송
					sender = "SYSTEM";
				}
				else { //관리자가 전송
					sender = "관리자";
				}
				dto.setSender(sender);
				dto.setPushAlarmSendDate(alarm.getPushAlarmSendDate());
				alarmDTOs.add(dto);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return alarmDTOs;
	}

	public List<PushAlarmSendDTO> findActiveforUser(String useremail) {

		Users user = userRepository.findByEmail(useremail).get();

		List<PushAlarmSend> pushAlarms = pushAlarmSendRepository.findAllByReceiver_Id(user.getId());
		List<PushAlarmSendDTO> alarmDTOs = new ArrayList<PushAlarmSendDTO>();
		String sender="";

		try {
			for(PushAlarmSend alarm:pushAlarms) {
				if(alarm.getPushAlarm().getIsActive()==1) {
					PushAlarmSendDTO dto = PushAlarmSendDTO.toDTO(alarm);
					dto.setReceiveUseremail(alarm.getReceiver().getEmail());
					if(alarm.getSender().equalsIgnoreCase("SYSTEM")) { // 시스템이 전송
						sender = "SYSTEM";
					}
					else { //관리자가 전송
						sender = "관리자";
					}
					dto.setSender(sender);
					dto.setPushAlarmSendDate(alarm.getPushAlarmSendDate());
					alarmDTOs.add(dto);
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return alarmDTOs;
	}



	//알람 전송하기 - mqtt연결하기 필요
	public PushAlarmSendDTO savePushAlarm(String title, String content, String receiveremails, String senderemail) {
		String[] emailsToSend = receiveremails.split(",");
		String sender =senderemail;
		LocalDateTime now = LocalDateTime.now();
		PushAlarmSendDTO responseDTO = new PushAlarmSendDTO();

		//알람 Database에 저장
		PushAlarmDTO dto = PushAlarmDTO.builder()
				.title(title).pushAlarmContent(content).isActive(true).isDelete(false).build();

		if(dto.getTitle().trim().length()==0 || dto.getPushAlarmContent().trim().length()==0) {
			throw new IllegalArgumentException("알림의 제목과 내용을 넣어주세요");
		}
		PushAlarm pushAlarm = dto.toEntity();
		pushAlarm = pushAlarmRepository.save(pushAlarm);
    
		Users receiver = new Users();
		for(int i=0; i<emailsToSend.length;i++) {
			if(userRepository.existsByEmail(emailsToSend[i])) {
				receiver = userRepository.findByEmail(emailsToSend[i]).get();
			}
			else {	throw new NoSuchElementException("존재하지 않는 수신자가 있습니다.");	}

			PushAlarmSendDTO alarmSendDTO = PushAlarmSendDTO.builder()
					.pushAlarm(pushAlarm).receiver(receiver).sender(sender).build();
			PushAlarmSend alarmSend = alarmSendDTO.toEntity();
			alarmSend.setReceiver(receiver);
			alarmSend.setSender(sender);
			alarmSend.setPushAlarmSendDate(now);

			alarmSend = pushAlarmSendRepository.save(alarmSend);
			
			if(i==emailsToSend.length-1) {
				responseDTO = PushAlarmSendDTO.toDTO(alarmSend);
				responseDTO.setSender(alarmSend.getSender());
				responseDTO.setReceiveUseremail(alarmSend.getReceiver().getEmail());
				//전송내역 돌려주기
			}
		}
		
		//Fast-API로 pub요청
		AlarmPublish pub = new AlarmPublish();
		boolean success = pub.sendAlarm(sender,emailsToSend, title,now);
		
		//System.out.println("did it work?? "+success);
		return responseDTO;

	}

	public PushAlarmSendDTO sendScheduledPushAlarm(String title, String content, String receiveremails, String senderemail, LocalDateTime sendDate) {
		
		String[] emailsToSend = receiveremails.split(",");
		String sender =senderemail;
		PushAlarmSendDTO responseDTO = new PushAlarmSendDTO();
		
		LocalDateTime now = LocalDateTime.now();

	    // 유효성 검사: sendDate가 현재 시간보다 미래여야 함
	    if (sendDate.isBefore(now)) {
	        throw new IllegalArgumentException("sendDate");	
	    }

		//알람 Database에 저장
		PushAlarmDTO dto = PushAlarmDTO.builder()
				.title(title).pushAlarmContent(content).isActive(true).isDelete(false).build();

		if(dto.getTitle().trim().length()==0 || dto.getPushAlarmContent().trim().length()==0) {
			throw new IllegalArgumentException("알림의 제목과 내용을 넣어주세요");
		}
		PushAlarm pushAlarm = dto.toEntity();
		pushAlarm = pushAlarmRepository.save(pushAlarm);
    
		Users receiver = new Users();
		for(int i=0; i<emailsToSend.length;i++) {
			if(userRepository.existsByEmail(emailsToSend[i])) {
				receiver = userRepository.findByEmail(emailsToSend[i]).get();
			}
			else {	throw new NoSuchElementException("존재하지 않는 수신자가 있습니다.");	}

			PushAlarmSendDTO alarmSendDTO = PushAlarmSendDTO.builder()
					.pushAlarm(pushAlarm).receiver(receiver).sender(sender).build();
			PushAlarmSend alarmSend = alarmSendDTO.toEntity();
			alarmSend.setReceiver(receiver);
			alarmSend.setSender(sender);
			alarmSend.setPushAlarmSendDate(sendDate);

			alarmSend = pushAlarmSendRepository.save(alarmSend);
			
			if(i==emailsToSend.length-1) {
				responseDTO = PushAlarmSendDTO.toDTO(alarmSend);
				responseDTO.setSender(alarmSend.getSender());
				responseDTO.setReceiveUseremail(alarmSend.getReceiver().getEmail());
				//전송내역 돌려주기
			}
		}
		
		//Fast-API로 pub요청
		AlarmPublish pub = new AlarmPublish();
		boolean success = pub.scheduledAlarm(sender,emailsToSend, title,sendDate);
		
		//System.out.println("did it work?? "+success);
		return responseDTO;
	}


	//모든 알람읽기 처리
	public boolean readAllAlarm(String useremail) {

		Users user = userRepository.findByEmail(useremail).get();

		List<PushAlarmSend> pushAlarms = pushAlarmSendRepository.findAllByReceiver_Id(user.getId());
		PushAlarm readAlarm = new PushAlarm();
		String sender="";

		try {
			for(PushAlarmSend alarm:pushAlarms) {
				readAlarm = alarm.getPushAlarm();
				readAlarm.setIsActive(0);
				System.out.println(pushAlarmRepository.save(readAlarm));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	public List<Map<String,String>> getUsers(String adminemail) {
		List<Map<String,String>> responses = new ArrayList<Map<String,String>>();
		Users user = userRepository.findByEmail(adminemail).get(); //관리자 권한확인
		
		if(user.getPermission().equals("ROLE_ADMIN")) { //관리자 권한확인
			
			List<Users> userlist = userRepository.findAll(); //모든 사용자 가져오기
			for(int i = 0; i < userlist.size(); i++) {
				if(userlist.get(i).getPermission().equalsIgnoreCase("ROLE_USER")) { //유저인경우에만 돌려주기
					Map<String,String> response = new HashMap<>();
					// name, email, loginType, lastLogin
					response.put("name", userlist.get(i).getName());
					response.put("email", userlist.get(i).getEmail());
					response.put("loginType", userlist.get(i).getLoginType());
					response.put("lastLogin", userlist.get(i).getLastLogin()!=null?userlist.get(i).getLastLogin().toString():userlist.get(i).getSignInDate().toString());
					responses.add(response);
				}	
			}
			return responses;
		}
		else throw new IllegalArgumentException("관리자가 아닙니다.접근권한이 없습니다.");
	}

	public boolean readAlarm(long alarmId) {
		PushAlarm success = new PushAlarm();
		try {
			PushAlarm alarm = pushAlarmRepository.findById(alarmId).get();
			alarm.setIsActive(0);
			success = pushAlarmRepository.save(alarm);
			System.out.println("=============================="+success.getTitle()+success.getIsActive());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return success==null?false:true;
		
		
	}




}