package com.ict.traveljoy.question.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.controller.CheckContainsUseremail;
import com.ict.traveljoy.question.service.QuestionCategoryDTO;
import com.ict.traveljoy.question.service.QuestionCategoryService;
import com.ict.traveljoy.question.service.QuestionDTO;
import com.ict.traveljoy.question.service.QuestionService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ask")
@RequiredArgsConstructor
public class QuestionController {

	private final QuestionService questionService;
	private final QuestionCategoryService questionCategoryService;
	private final ObjectMapper objectMapper;
	private final CheckContainsUseremail checkUser;
	

	@PostMapping
	public ResponseEntity<QuestionDTO> createQuestion(@RequestBody Map<String, String> map , HttpServletRequest request){
		//질문한사람 user 설정,questionCategory받기,questionContent,questionTitle
		String useremail = checkUser.checkContainsUseremail(request);
		String category = (String)map.get("category");
		String title = (String)map.get("title");
		String content = (String)map.get("content");
				
		try {
			QuestionDTO createdQuestion = questionService.createQuestion(useremail,category,title,content);
			if(createdQuestion == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			 return new ResponseEntity<>(createdQuestion, HttpStatus.CREATED);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//모두, FAQ제외
	@GetMapping("/all")
	public ResponseEntity<List<QuestionDTO>> getAllQuestion(){
		try {
			List<QuestionDTO> questionList = questionService.findAll();
			for(QuestionDTO q:questionList) {
				System.out.println(q.getQuestionTitle());
			}
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(questionList);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	//카테고리만
	@GetMapping("/category")
	public ResponseEntity<List<QuestionCategoryDTO>> getQuestionCategoryNames(){
		try {
			List<QuestionCategoryDTO> questionCategories = questionCategoryService.getAllCategory();
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(questionCategories);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	// 특정 카테고리 게시글만, FAQ는 여기를 통해
	@GetMapping("/category/{question_category}")
	public ResponseEntity<List<QuestionDTO>> getQuestionByCategory(@PathVariable("question_category") String question_category){
		System.out.println(question_category);
		try {
			List<QuestionDTO> questionList = questionService.findAllByCategory(question_category);
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(questionList);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	// 검색, 
	
	//특정 문의글
	@GetMapping("/{question_id}")
	public ResponseEntity<QuestionDTO> getQuestionById(@PathVariable("question_id") String question_id){
		try {
			QuestionDTO questionDTO = questionService.findById(Long.parseLong(question_id));
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(questionDTO);
		}
		catch(Exception e) {
			System.out.print("question_get: ");
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//문의글 수정
	@PutMapping("/{question_id}")
	public ResponseEntity<QuestionDTO> updateQuestion(@PathVariable("question_id") String question_id,@RequestBody Map<String, String> map){
		String title = (String)map.get("title");
		String content = (String)map.get("content");
		String category = (String)map.get("category");
		
		try {
			QuestionDTO updatedQuestionDTO = questionService.updateById(Long.parseLong(question_id),title,content,category);
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(updatedQuestionDTO);
		}
		catch(Exception e) {
			System.out.print("notice_put: ");
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//문의글 삭제
	@DeleteMapping("/{question_id}")
	public ResponseEntity<String> deleteQuestion(@PathVariable("question_id") String question_id){
		try {
			boolean deleted = questionService.deleteById(Long.parseLong(question_id));
			if(deleted==true) 
				return ResponseEntity.status(200).body("Success");
			else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		catch(Exception e) {
			System.out.print("question_delete: ");
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/users")
	public ResponseEntity<Map<String,Object>> getQuestionByUser(HttpServletRequest request){
		String useremail = checkUser.checkContainsUseremail(request);
		try {
			List<QuestionDTO> questionDTOs = questionService.findAllByUser(useremail);
			List<Long> questionIds = questionService.findIdsByUser(useremail);
			Map<String,Object> response = new HashMap<String,Object>();
			
			response.put("QuestionList", questionDTOs);
			response.put("AnswerIdList", questionIds);
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(response);
		}
		catch(Exception e) {
			System.out.print("question_get: ");
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
