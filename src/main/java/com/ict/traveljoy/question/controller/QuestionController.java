package com.ict.traveljoy.question.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.question.service.QuestionDTO;
import com.ict.traveljoy.question.service.QuestionService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ask")
@RequiredArgsConstructor
public class QuestionController {

	private final QuestionService questionService;
	private final ObjectMapper objectMapper;
	
	//여러개
	
	
	//하나만
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
	
	@GetMapping("/{question_id}")
	public ResponseEntity<QuestionDTO> getQuestionbyId(@PathVariable String question_id){
		try {
			QuestionDTO questionDTO = questionService.findById(question_id);
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(questionDTO);
		}
		catch(Exception e) {
			System.out.print("notice_get: ");
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
