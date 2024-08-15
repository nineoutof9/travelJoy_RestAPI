package com.ict.traveljoy.question.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.question.repository.Question;
import com.ict.traveljoy.question.repository.QuestionRepository;

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


	public List<QuestionDTO> getAll() {
		List<Question> questionList = questionRepostiory.findAll();
		return questionList.stream().map(question->QuestionDTO.toDTO(question)).collect(Collectors.toList());
	}
	
	//본인꺼외에는 안보이게 할지 말지
	public QuestionDTO findById(String question_id) {
		// TODO Auto-generated method stub
		return null;
	}



}
