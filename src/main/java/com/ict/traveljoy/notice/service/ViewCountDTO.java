package com.ict.traveljoy.notice.service;


import java.time.LocalDateTime;

import com.ict.traveljoy.notice.repository.Notice;
import com.ict.traveljoy.notice.repository.ViewCount;
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
public class ViewCountDTO {

	private Long id;
	private Notice notice;
	private Users user;
	private LocalDateTime viewDate;
	private Long count;
	
	public ViewCount toEntity() {
		return ViewCount.builder()
				.id(id)
				.notice(notice)
				.user(user)
				.viewDate(viewDate)
				.count(count)
				.build();
	}
	
	public static ViewCountDTO toDTO(ViewCount viewCount) {
		return ViewCountDTO.builder()
				.id(viewCount.getId())
				.notice(viewCount.getNotice())
				.user(viewCount.getUser())
				.viewDate(viewCount.getViewDate())
				.count(viewCount.getCount())
				.build();
	}
}
