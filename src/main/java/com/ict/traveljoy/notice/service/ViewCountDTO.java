package com.ict.traveljoy.notice.service;


import java.time.LocalDateTime;

import com.ict.traveljoy.notice.repository.ViewCount;

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
public class ViewCountDTO {

	private long id;
	private long noticeId;
	private long userId;
	private LocalDateTime viewDate;
	
	public ViewCount toEntity() {
		return ViewCount.builder()
				.id(id)
				.noticeId(noticeId)
				.userId(userId)
				.viewDate(viewDate)
				.build();
	}
	
	public static ViewCountDTO toDTO(ViewCount viewCount) {
		return ViewCountDTO.builder()
				.id(viewCount.getId())
				.noticeId(viewCount.getNoticeId())
				.userId(viewCount.getUserId())
				.viewDate(viewCount.getViewDate())
				.build();
	}
}
