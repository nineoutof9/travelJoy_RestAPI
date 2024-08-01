package com.ict.traveljoy.service.question;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.repository.question.QuestionRepository;
import com.ict.traveljoy.service.notice.NoticeDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {

	private final QuestionRepository questionRepostiory;
	private final ObjectMapper objectMapper;
	
	
	public QuestionDTO createQuestion(QuestionDTO questionDTO) {
		return questionDTO;
		// TODO Auto-generated method stub
		
	}
}
