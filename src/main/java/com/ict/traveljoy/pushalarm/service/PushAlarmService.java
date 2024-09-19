package com.ict.traveljoy.pushalarm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
	public PushAlarmSendDTO savePushAlarm(String title, String content, String receiveremail, String useremail) {
		PushAlarmDTO dto = PushAlarmDTO.builder()
				.title(title).pushAlarmContent(content).isActive(true).isDelete(false).build();

		if(dto.getTitle().trim().length()==0 || dto.getPushAlarmContent().trim().length()==0) {
			throw new IllegalArgumentException("알림의 제목과 내용을 넣어주세요");
		}
		PushAlarm pushAlarm = dto.toEntity();
		pushAlarm = pushAlarmRepository.save(pushAlarm);

		Users receiver = userRepository.findByEmail(receiveremail).get();

		String sender ="";
		if(userRepository.existsByEmail(useremail)) {
			sender = useremail;
		}
		else {sender = "SYSTEM";}


		PushAlarmSendDTO alarmSendDTO = PushAlarmSendDTO.builder()
				.pushAlarm(pushAlarm).receiver(receiver).sender(sender).build();
		PushAlarmSend alarmSend = alarmSendDTO.toEntity();
		alarmSend.setReceiver(receiver);
		alarmSend.setSender(sender);

		alarmSend = pushAlarmSendRepository.save(alarmSend);
		System.out.println("hmmmm"+alarmSend.getPushAlarmSendDate()==null?"null":alarmSend.getPushAlarmSendDate());
		PushAlarmSendDTO responseDTO = PushAlarmSendDTO.toDTO(alarmSend);
		responseDTO.setSender(alarmSend.getSender());
		responseDTO.setReceiveUseremail(alarmSend.getReceiver().getEmail());
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


}