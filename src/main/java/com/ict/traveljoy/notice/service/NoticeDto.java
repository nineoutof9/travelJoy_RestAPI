package com.ict.traveljoy.notice.service;

import com.ict.traveljoy.notice.repository.Notice;

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
public class NoticeDto {

	//필드
	private long id;
    private String title;
    private String content;
    private String writer;
    private boolean isDelete;
    private boolean isActive;
 
    // DTO를 엔티티로 변환
    public Notice toEntity() {
        return Notice.builder()
        		.id(id)
                .title(title)
                .content(content)
                .writer(writer)
                .isDelete(isDelete)
                .isActive(isActive)
                .build();
    }

    // 엔티티를 DTO로 변환
    public static NoticeDto toDto(Notice notice) {
        return NoticeDto.builder()
        		.id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .writer(notice.getWriter())
                .isDelete(notice.getIsDelete())
                .isActive(notice.getIsActive())

                .build();
    }
}
