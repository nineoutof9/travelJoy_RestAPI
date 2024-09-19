package com.ict.traveljoy.pushalarm.service;

import java.time.LocalDateTime;

import com.ict.traveljoy.pushalarm.repository.PushAlarm;
import com.ict.traveljoy.pushalarm.repository.PushAlarmSend;
import com.ict.traveljoy.users.repository.Users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PushAlarmSendDTO {

	private Long id;
	private PushAlarm pushAlarm;
	private String sender;	//admin 이름
	private String receiveUseremail;  //useremail
	private Users receiver;
	private LocalDateTime pushAlarmSendDate;
	
	public PushAlarmSend toEntity() {
		return PushAlarmSend.builder()
				.id(id)
				.pushAlarm(pushAlarm)
				.pushAlarmSendDate(pushAlarmSendDate)
				.build();
		//sender, receiver,receiveUser는 service단에서 처리
	}
	
	public static PushAlarmSendDTO toDTO(PushAlarmSend pushAlarmSend) {
		return PushAlarmSendDTO.builder()
				.id(pushAlarmSend.getId())
				.pushAlarm(pushAlarmSend.getPushAlarm())
				.pushAlarmSendDate(pushAlarmSend.getPushAlarmSendDate())
				.build();
		//sender, receiver는 service단에서 처리
	}
}
