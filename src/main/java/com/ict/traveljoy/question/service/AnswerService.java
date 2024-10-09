package com.ict.traveljoy.question.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.question.repository.Answer;
import com.ict.traveljoy.question.repository.AnswerRepository;
import com.ict.traveljoy.question.repository.Question;
import com.ict.traveljoy.question.repository.QuestionRepository;
import com.ict.traveljoy.users.repository.UserRepository;
import com.ict.traveljoy.users.repository.Users;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {
	private final AnswerRepository answerRepository;
	private final QuestionRepository questionRepository;
	private final UserRepository userRepository;
	private final ObjectMapper objectMapper;
	

	// 질문 답변달기
	public AnswerDTO createAnswer(String useremail,long questionId,String answerContent,String answerHandlerName) {
		
		Users user = userRepository.findByEmail(useremail).get();
		Question question = questionRepository.findById(questionId).get();
		Answer answer = Answer.builder()
				.answerContent(answerContent).answerHandlerName(answerHandlerName)
				.user(user).question(question)
				.build();
		Answer aftersave = answerRepository.save(answer);
		question.setIsHasAnswer(1);
		questionRepository.save(question);
		return AnswerDTO.toDTO(aftersave);
	}
	
	//질문으로 답변찾기
	public AnswerDTO findByQuestionId(long questionId) {
		if(questionRepository.existsById(questionId)) {
			Question question = questionRepository.findById(questionId).get();
			Answer answer = answerRepository.findByQuestion_Id(question.getId());
			return AnswerDTO.toDTO(answer);	
		}
		else throw new IllegalArgumentException("오류");
	}

	//답변 수정
	public AnswerDTO updateById(long answerId,AnswerDTO answerDTO) {
		if(answerRepository.existsById(answerId)) {
			Answer beforeAnswer = answerRepository.findById(answerId).orElseThrow(() -> new RuntimeException("Answer not found"));
			beforeAnswer.setAnswerContent(answerDTO.getAnswerContent());
			beforeAnswer.setAnswerHandlerName(answerDTO.getAnswerHandlerName());
			beforeAnswer.setUser(answerDTO.getUser());
			Answer updatedAnswer = answerRepository.save(beforeAnswer);
			return AnswerDTO.toDTO(updatedAnswer);
		}
		else throw new IllegalArgumentException("오류");
	}

	//답변 삭제
	public AnswerDTO deleteById(long answerId) {
		if(answerRepository.existsById(answerId)) {
			Answer answer = answerRepository.findById(answerId).orElseThrow(() -> new RuntimeException("Answer not found"));
			answerRepository.delete(answer);
			return AnswerDTO.toDTO(answer);
		}
		else throw new IllegalArgumentException("오류");
	}

	public boolean deleteByQuestionId(long questionId) {
		if(answerRepository.existsByQuestion_Id(questionId)) {
			Answer answer = answerRepository.findByQuestion_Id(questionId);
		}
		return false;
	}



	
	
}
