package com.ict.traveljoy.service.notice;


import java.time.LocalDateTime;

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
    private String title;
    private String content;
    private String writer;
    private boolean isDelete;
    private boolean isActive;
 
    // DTO를 엔티티로 변환
    public NoticeDto toEntity() {
        return NoticeDto.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .isDelete(isDelete)
                .isActive(isActive)
                .build();
    }

    // 엔티티를 DTO로 변환
    public static NoticeDto fromEntity(NoticeDto entity) {
        return NoticeDto.builder()
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .isDelete(entity.getIsDelete())
                .isActive(entity.getIsActive())
                .build();
    }
}
