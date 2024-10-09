package com.ict.traveljoy.question.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.controller.CheckContainsUseremail;
import com.ict.traveljoy.question.service.AnswerDTO;
import com.ict.traveljoy.question.service.AnswerService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/answer")
@RequiredArgsConstructor
public class AnswerController {

	private final AnswerService answerService;
	private final CheckContainsUseremail checkUser;
	private final ObjectMapper objectMapper;
	
	@PostMapping
	public ResponseEntity<AnswerDTO> createAnswer(@RequestBody Map<String, String> map, HttpServletRequest request){
		String useremail = checkUser.checkContainsUseremail(request);
		Long questionId = Long.parseLong(map.get("qid"));
		String answerContent = (String)map.get("answer");
		String answerHandlerName = (String)map.get("handler");
		
		try {
			AnswerDTO createdAnswer = answerService.createAnswer(useremail,questionId,answerContent,answerHandlerName);
			if(createdAnswer == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			 return new ResponseEntity<>(createdAnswer, HttpStatus.CREATED);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{question_id}")
	public ResponseEntity<AnswerDTO> getAnswerByQuestionId(@PathVariable("question_id") String question_id){
		try {
			AnswerDTO answer = answerService.findByQuestionId(Long.parseLong(question_id));
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(answer);
		}
		catch(Exception e) {
			System.out.print("answer_get: ");
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/{answer_id}")
	public ResponseEntity<AnswerDTO> updateAnswer(@PathVariable("answer_id") String answer_id,@RequestBody AnswerDTO answerDTO){
		try {
			AnswerDTO updatedAnswerDTO = answerService.updateById(Long.parseLong(answer_id),answerDTO);
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(answerDTO);
		}
		catch(Exception e) {
			System.out.print("answer_update: ");
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} 
	
	@DeleteMapping("/{answer_id}")
	public ResponseEntity<AnswerDTO> deleteAnswer(@PathVariable("answer_id") String answer_id){
		try {
			AnswerDTO answerDTO = answerService.deleteById(Long.parseLong(answer_id));
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(answerDTO);
		}
		catch(Exception e) {
			System.out.print("answer_delete: ");
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
