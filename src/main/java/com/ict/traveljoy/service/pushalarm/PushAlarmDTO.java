package com.ict.traveljoy.service.pushalarm;

import java.time.LocalDateTime;

import com.ict.traveljoy.repository.pushalarm.PushAlarm;

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
public class PushAlarmDTO {
	
	private long id;
	private String title;
	private String pushAlarmContent;
	private boolean isActive;
	private boolean isDelete;
	private LocalDateTime deleteDate;
	
	public PushAlarm toEntity() {
		return PushAlarm.builder()
				.id(id)
				.title(title)
				.pushAlarmContent(pushAlarmContent)
				.isActive(isActive==true?'T':'F')
				.isDelete(isDelete==true?'T':'F')
				.deleteDate(deleteDate)
				.build();
	}
	
	public static PushAlarmDTO toDTO(PushAlarm pushAlarm) {
		return PushAlarmDTO.builder()
				.id(pushAlarm.getId())
				.title(pushAlarm.getTitle())
				.pushAlarmContent(pushAlarm.getPushAlarmContent())
				.isActive(pushAlarm.getIsActive()=='T'?true:false)
				.isDelete(pushAlarm.getIsDelete()=='T'?true:false)
				.deleteDate(pushAlarm.getDeleteDate())
				.build();
	}
}
