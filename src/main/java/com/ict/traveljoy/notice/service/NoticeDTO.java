package com.ict.traveljoy.notice.service;

import java.time.LocalDateTime;

import com.ict.traveljoy.notice.repository.Notice;
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
public class NoticeDTO {

	//필드
	private Long id;
	private LocalDateTime noticeDate;
    private String title;
    private String content;
    private Boolean isDelete;
    private Boolean isActive;
    private String writer;
    private Users user;
 
    // DTO를 엔티티로 변환
    public Notice toEntity() {
        return Notice.builder()
        		.id(id)
                .title(title)
                .content(content)
                .writer(writer)
                .isDelete(isDelete == null||false ? 0 : 1)
                .isActive(isActive == null||false ? 0 : 1)
                .build();
    }

    // 엔티티를 DTO로 변환
    public static NoticeDTO toDto(Notice notice) {
        return NoticeDTO.builder()
        		.id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .writer(notice.getWriter())
                .isDelete(notice.getIsDelete() == 1 ? true : false)
                .isActive(notice.getIsActive() == 1 ? true : false)
                .build();
    }
}
