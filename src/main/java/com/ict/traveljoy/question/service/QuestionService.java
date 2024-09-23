package com.ict.traveljoy.question.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.question.repository.AnswerRepository;
import com.ict.traveljoy.question.repository.Question;
import com.ict.traveljoy.question.repository.QuestionCategory;
import com.ict.traveljoy.question.repository.QuestionRepository;
import com.ict.traveljoy.users.repository.UserRepository;
import com.ict.traveljoy.users.repository.Users;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {

	private final QuestionRepository questionRepostiory;
	private final QuestionCategoryService questionCategoryService;
	private final AnswerService answerService;
	private final AnswerRepository answerRepository;
	private final UserRepository userRepository;
	private final ObjectMapper objectMapper;
	
	
	public QuestionDTO createQuestion(String useremail,String category, String title,String content) {
				
		Users user = userRepository.findByEmail(useremail).get();
		QuestionDTO newQuestion = QuestionDTO.builder().user(user).questionTitle(title).questionContent(content).isHasAnswer(false).build();
		QuestionCategory questionCategory = questionCategoryService.findCategoryByCategoryName(category);
		if(questionCategory!=null) {
			newQuestion.setQuestionCategory(questionCategory);
			Question question = newQuestion.toEntity();
			Question afterSave = questionRepostiory.save(question);
			return QuestionDTO.toDTO(afterSave);
		}
		else return null;
		
	}


	public List<QuestionDTO> findAll() {
		List<Question> questionList = questionRepostiory.findAll();
		List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();
		for(Question question:questionList) {
			if(!question.getQuestionCategory().getQuestionCategoryName().equalsIgnoreCase("FAQ")) {
				questionDTOList.add(QuestionDTO.toDTO(question));
			}
		}
		
		return questionDTOList;
	}

	public List<QuestionDTO> findAllByCategory(String category) {
		// category name으로 해당 category question 다 받아오기
		// category 객체 받아와서 그걸로 찾기
		QuestionCategory questionCategory = questionCategoryService.findCategoryByCategoryName(category);
		if(questionCategory!=null) {
			List<Question> questionList = questionRepostiory.findAllByQuestionCategory_Id(questionCategory.getId());
			return questionList.stream().map(question->QuestionDTO.toDTO(question)).collect(Collectors.toList());
		}
		else throw new IllegalArgumentException("오류");
		
		
//		QuestionCategory category = questionRepostiory.findByQuestionCategoryName(questionCategory);
//		List<Question> questionListByCategory = questionRepostiory.findAllByQuestionCategory_Id(category.getId());
//		return questionListByCategory.stream().map(QuestionDTO::toDTO).collect(Collectors.toList());
	}
	
	
	
	//본인꺼외에는 안보이게 할지 말지
	public QuestionDTO findById(long questionId) {
		if(questionRepostiory.existsById(questionId)) {
			Question question = questionRepostiory.findById(questionId).get();
			return QuestionDTO.toDTO(question);
		}
		return null;
	}


	public QuestionDTO updateById(long questionId,QuestionDTO questionDTO) {
		if(questionRepostiory.existsById(questionId)) {
			Question beforeQuestion = questionRepostiory.findById(questionId).get();
			beforeQuestion.setQuestionContent(questionDTO.getQuestionContent());
			
			QuestionCategory updateCategory = questionCategoryService.findCategoryByCategoryName(questionDTO.getQuestionCategory().getQuestionCategoryName());
			beforeQuestion.setQuestionCategory(updateCategory);
			
			
			Question updatedQuestion = questionRepostiory.save(beforeQuestion);
			return QuestionDTO.toDTO(updatedQuestion);
		}
		else throw new IllegalArgumentException("해당 id와 일치하는 question 없음");
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

	public Boolean deleteById(long questionId) {
		if(questionRepostiory.existsById(questionId)) {
			Question question = questionRepostiory.findById(questionId).orElseThrow(() -> new RuntimeException("Question not found"));
			boolean questionDeleteProceed = false;
			if(question.getIsHasAnswer()==1) {
				questionDeleteProceed = answerService.deleteByQuestionId(questionId);
			}
			else {
				questionDeleteProceed=true;
			}
			if(questionDeleteProceed) {
				questionRepostiory.delete(question);
				System.out.println("지웟따.");
				return true;
			}
		}
		else throw new IllegalArgumentException("삭제 실패");
		return false;
	}


	public List<QuestionDTO> findAllByUser(String useremail) {
		Users user = userRepository.findByEmail(useremail).get();
		
		List<Question> questions = questionRepostiory.findAllByUser_Id(user.getId());
		return questions.stream().map(question->QuestionDTO.toDTO(question)).collect(Collectors.toList());
	}


	public List<Long> findIdsByUser(String useremail) {
		Users user = userRepository.findByEmail(useremail).get();
		List<Question> questions = questionRepostiory.findAllByUser_Id(user.getId());
		List<Long> qids = new ArrayList<>(); 
		for(Question question:questions) {
			long tempId=0;
			if(question.getIsHasAnswer()==1) {
				tempId = answerRepository.findByQuestion_Id(question.getId()).getId();
				qids.add(tempId);
			}
		}
		return qids;
	}

	





}
