package com.ict.traveljoy.question.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.question.service.QuestionCategoryDTO;
import com.ict.traveljoy.question.service.QuestionCategoryService;
import com.ict.traveljoy.question.service.QuestionDTO;
import com.ict.traveljoy.question.service.QuestionService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ask")
@RequiredArgsConstructor
public class QuestionController {

	private final QuestionService questionService;
	private final QuestionCategoryService questionCategoryService;
	private final ObjectMapper objectMapper;
	

	@PostMapping("/createAsk")
	public ResponseEntity<QuestionDTO> createQuestion(@RequestBody QuestionDTO questionDTO){
		try {
			QuestionDTO createdQuestion = questionService.createQuestion(questionDTO);
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
	
	//모두
	@GetMapping("/all")
	public ResponseEntity<List<QuestionDTO>> getQuestionAll(){
		try {
			List<QuestionDTO> questionList = questionService.getAll();
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
	
	// 특정 카테고리
	@GetMapping("/{question_category}")
	public ResponseEntity<List<QuestionDTO>> getQuestionByCategory(@PathVariable String question_category){
		try {
			List<QuestionDTO> questionList = questionService.getAllByCategory(question_category);
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(questionList);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	// 검색, 
	
	//특정 문의글
	@GetMapping("/{question_id}")
	public ResponseEntity<QuestionDTO> getQuestionById(@PathVariable String question_id){
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
	public ResponseEntity<QuestionDTO> updateQuestion(@PathVariable String question_id,@RequestBody QuestionDTO questionDTO){
		try {
			QuestionDTO updatedQuestionDTO = questionService.updateById(Long.parseLong(question_id),questionDTO);
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
	public ResponseEntity<QuestionDTO> deleteQuestion(@PathVariable String question_id){
		try {
			QuestionDTO deletedQuestionDTO = questionService.deleteById(Long.parseLong(question_id));
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(deletedQuestionDTO);
		}
		catch(Exception e) {
			System.out.print("question_delete: ");
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
