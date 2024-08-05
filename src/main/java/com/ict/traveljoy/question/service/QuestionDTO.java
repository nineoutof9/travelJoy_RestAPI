package com.ict.traveljoy.question.service;

import java.time.LocalDateTime;

import com.ict.traveljoy.question.repository.Question;

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
public class QuestionDTO {

	private long id;
	private long userId;
	private long questionCategoryId;
	private LocalDateTime questionDate;
	private String questionContent;
	private boolean isHasAnswer;
	
	public Question toEntity() {
		return Question.builder()
				.id(id)
				.userId(userId)
				.questionCategoryId(questionCategoryId)
				.questionDate(questionDate)
				.questionContent(questionContent)
				.isHasAnswer(isHasAnswer==true?'T':'F')
				.build();
	}
	
	public static QuestionDTO toDTO(Question question) {
		return QuestionDTO.builder()
				.id(question.getId())
				.userId(question.getUserId())
				.questionCategoryId(question.getQuestionCategoryId())
				.questionDate(question.getQuestionDate())
				.questionContent(question.getQuestionContent())
				.isHasAnswer(question.getIsHasAnswer()=='T'?true:false)
				.build();
	}
}
