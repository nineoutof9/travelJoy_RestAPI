package com.ict.traveljoy.comment.service;

import java.time.LocalDateTime;

import com.ict.traveljoy.comment.repository.Comment;
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
public class CommentDTO {
	private Long id;
	private String target;
	private Long targetId;
	private Users user;
	private String contents;
	private LocalDateTime createDate;
	private Boolean isActive;
	private Boolean isDelete;
	private LocalDateTime deleteDate;
	
	public Comment toEntity() {
		return Comment.builder()
				.id(id)
				.target(target)
				.targetId(targetId)
				.user(user)
				.contents(contents)
				.createDate(createDate)
				.isActive(isActive?1:0)
				.isDelete(isDelete?1:0)
				.deleteDate(deleteDate)
				.build();
	}
	
	public static CommentDTO toDTO(Comment comment) {
		return CommentDTO.builder()
				.id(comment.getId())
				.target(comment.getTarget())
				.targetId(comment.getTargetId())
				.user(comment.getUser())
				.contents(comment.getContents())
				.createDate(comment.getCreateDate())
				.isActive(comment.getIsActive()== 1 ? true : false)
				.isDelete(comment.getIsDelete()== 1 ? true : false)
				.deleteDate(comment.getDeleteDate())
				.build();
	}

}
