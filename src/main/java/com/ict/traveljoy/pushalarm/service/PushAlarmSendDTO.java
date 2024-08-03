package com.ict.traveljoy.pushalarm.service;

import java.time.LocalDateTime;

import com.ict.traveljoy.pushalarm.repository.PushAlarmSend;

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

	private long id;
	private long pushAlarmId;
	private long userId;
	private LocalDateTime pushAlarmSendDate;
	
	public PushAlarmSend toEntity() {
		return PushAlarmSend.builder()
				.id(id)
				.pushAlarmId(pushAlarmId)
				.userId(userId)
				.pushAlarmSendDate(pushAlarmSendDate)
				.build();
	}
	
	public static PushAlarmSendDTO toDTO(PushAlarmSend pushAlarmSend) {
		return PushAlarmSendDTO.builder()
				.id(pushAlarmSend.getId())
				.pushAlarmId(pushAlarmSend.getPushAlarmId())
				.userId(pushAlarmSend.getUserId())
				.pushAlarmSendDate(pushAlarmSend.getPushAlarmSendDate())
				.build();
	}
}
