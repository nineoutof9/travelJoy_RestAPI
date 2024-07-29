package com.ict.traveljoy.service.question;

import java.time.LocalDateTime;

import com.ict.traveljoy.repository.question.Answer;

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
public class AnswerDTO {

	private long id;
	private long questionId;
	private long answerHandlerId;
	private String answerHandlerName;
	private LocalDateTime answerDate;
	private String answerContent;
	
	public Answer toEntity() {
		return Answer.builder()
				.id(id)
				.questionId(questionId)
				.answerHandlerId(answerHandlerId)
				.answerDate(answerDate)
				.answerContent(answerContent)
				.build();
	}
	
	public static AnswerDTO toDTO(Answer answer) {
		return AnswerDTO.builder()
				.id(answer.getId())
				.questionId(answer.getQuestionId())
				.answerHandlerId(answer.getAnswerHandlerId())
				.answerDate(answer.getAnswerDate())
				.answerContent(answer.getAnswerContent())
				.build();
	}
}
