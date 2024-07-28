package com.ict.traveljoy.controller.question;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.service.notice.NoticeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "문의 사항관리", description = "문의사항을 관리하기 위한 엔드포인트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class QuestionController {

	private final NoticeService noticeService;
	private final ObjectMapper objectMapper;
	
	@CrossOrigin
	@PostMapping("/question")
	@Operation(summary = "문의사항 생성하기", description = "문의사항 게시글 생성 컨트롤러입니다")
	public ResponseEntity<String> createQuestion(){
		try {
			return null;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
