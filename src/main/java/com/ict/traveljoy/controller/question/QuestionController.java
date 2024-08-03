package com.ict.traveljoy.controller.question;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.service.question.QuestionDTO;
import com.ict.traveljoy.service.question.QuestionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "문의 사항관리", description = "문의사항을 관리하기 위한 엔드포인트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class QuestionController {

	private final QuestionService questionService;
	private final ObjectMapper objectMapper;
	
	@CrossOrigin
	@PostMapping("/question")
	@Operation(summary = "문의사항 생성하기", description = "문의사항 게시글 생성 컨트롤러입니다")
	public ResponseEntity<QuestionDTO> createQuestion(@RequestBody QuestionDTO questionDTO){
		try {
            
			QuestionDTO createQuestion = questionService.createQuestion(questionDTO);
            return ResponseEntity.ok(createQuestion);
        }
        catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
	}
	
	@CrossOrigin
	@PostMapping("/questionView")
	@Operation(summary = "문의사항 조회하기", description = "문의사항 게시글 조회 컨트롤러입니다")
	public ResponseEntity<String> QuestionView(){
		try {
			return null;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@CrossOrigin
	@PostMapping("/questionEdit")
	@Operation(summary = "문의사항 수정하기", description = "문의사항 게시글 수정 컨트롤러입니다")
	public ResponseEntity<String> QuestionEdit(){
		try {
			return null;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@CrossOrigin
	@PostMapping("/questionDelete")
	@Operation(summary = "문의사항 삭제하기", description = "문의사항 게시글 삭제 컨트롤러입니다")
	public ResponseEntity<String> createQuestionDelete(){
		try {
			return null;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
