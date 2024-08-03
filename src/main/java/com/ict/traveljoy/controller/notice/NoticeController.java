package com.ict.traveljoy.controller.notice;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.service.notice.NoticeDto;
import com.ict.traveljoy.service.notice.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Tag(name = "공지사항 관리", description = "공지사항 Rest Api 컨트롤러 입니다")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NoticeController {
	
	private final NoticeService noticeService;
	private final ObjectMapper objectMapper;
	    
    @CrossOrigin
    @PostMapping("/notice")
    @Operation(summary = "공지사항 생성하기", description = "공지사항 생성 컨트롤러입니다")
    public ResponseEntity<NoticeDto> createNotice(@RequestBody NoticeDto noticeDto){
    	try{
	    	NoticeDto createdNotice = noticeService.createNotice(noticeDto);
	        return ResponseEntity.ok(createdNotice);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    	}
    }
        
    @CrossOrigin
    @GetMapping("/noticeview")
    @Operation(summary = "공지사항 조회하기", description = "공지사항 조회 컨트롤러입니다")
    public ResponseEntity<List<NoticeDto>> getAllNotices() {
    	try {
	        List<NoticeDto> noticeView = noticeService.findAll();
	        return ResponseEntity.ok(noticeView);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    	}
    }

    @CrossOrigin
    @PutMapping("/noticeUpdate")
    @Operation(summary = "공지사항 수정하기", description = "공지사항 수정 컨트롤러입니다")
    public ResponseEntity<NoticeDto> noticeUpdate(@RequestBody NoticeDto noticeDto) {
        try {
        	NoticeDto updatedDto=noticeService.noticeUpdate(noticeDto);
        	return ResponseEntity.ok(updatedDto);
        }
        catch(Exception e) {
        	e.printStackTrace();
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    
    @CrossOrigin
    @DeleteMapping("/noticeUpdate")
    @Operation(summary = "공지사항 삭제하기", description = "공지사항 삭제 컨트롤러입니다")
    public ResponseEntity<NoticeDto> noticeDelete(@PathVariable Long id) {
        try {
        	NoticeDto deleteDto=noticeService.noticeDelete(id);
        	return ResponseEntity.ok(deleteDto);
        }
        catch(Exception e) {
        	e.printStackTrace();
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    
}
