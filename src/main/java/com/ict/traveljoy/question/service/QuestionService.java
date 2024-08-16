package com.ict.traveljoy.question.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.question.repository.Question;
import com.ict.traveljoy.question.repository.QuestionCategory;
import com.ict.traveljoy.question.repository.QuestionCategoryRepository;
import com.ict.traveljoy.question.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {

	private final QuestionRepository questionRepostiory;
	private final QuestionCategoryRepository questionCategoryRepository;
	private final ObjectMapper objectMapper;
	
	
	public QuestionDTO createQuestion(QuestionDTO questionDTO) {
		Question question = questionDTO.toEntity();
		Question afterSave = questionRepostiory.save(question);
		return QuestionDTO.toDTO(afterSave);		
	}


	public List<QuestionDTO> getAll() {
		List<Question> questionList = questionRepostiory.findAll();
		return questionList.stream().map(question->QuestionDTO.toDTO(question)).collect(Collectors.toList());
	}

	public List<QuestionDTO> getAllByCategory(String questionCategory) {
		QuestionCategory category = questionCategoryRepository.findByQuestionCategoryName(questionCategory);
		List<Question> questionListByCategory = questionRepostiory.findAllByQuestionCategory_Id(category.getId());
		return questionListByCategory.stream().map(QuestionDTO::toDTO).collect(Collectors.toList());
	}
	
	//본인꺼외에는 안보이게 할지 말지
	public QuestionDTO findById(long questionId) {
		// TODO Auto-generated method stub
		return null;
	}


	public QuestionDTO updateById(long questionId,QuestionDTO questionDTO) {
		if(questionRepostiory.existsById(questionId)) {
			Question beforeQuestion = questionRepostiory.findById(questionId).orElseThrow(() -> new RuntimeException("Question not found"));
			beforeQuestion.setQuestionCategory(questionDTO.getQuestionCategory());
			beforeQuestion.setQuestionContent(questionDTO.getQuestionContent());
			Question updatedQuestion = questionRepostiory.save(beforeQuestion);
			return QuestionDTO.toDTO(updatedQuestion);
		}
		else throw new IllegalArgumentException("오류");
	}
	
	public int answerCompleted(long questionId) {
		if(questionRepostiory.existsById(questionId)) {
			Question answeredQuestion = questionRepostiory.findById(questionId).orElseThrow(() -> new RuntimeException("Question not found"));
			answeredQuestion.setIsHasAnswer(1);
			questionRepostiory.save(answeredQuestion);
			return 1;
		}
		else return 0;
	}

	public QuestionDTO deleteById(long questionId) {
		if(questionRepostiory.existsById(questionId)) {
			Question question = questionRepostiory.findById(questionId).orElseThrow(() -> new RuntimeException("Question not found"));
			questionRepostiory.delete(question);
			return QuestionDTO.toDTO(question);
		}
		else throw new IllegalArgumentException("오류");
	}
	
	





}
