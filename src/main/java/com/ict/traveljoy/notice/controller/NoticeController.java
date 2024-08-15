package com.ict.traveljoy.notice.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.notice.service.NoticeDTO;
import com.ict.traveljoy.notice.service.NoticeService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

	private final NoticeService noticeService;
	private final ObjectMapper objectMapper;
	
	@PostMapping("/createNotice")
	public ResponseEntity<NoticeDTO> createFavorite(@RequestBody NoticeDTO noticeDTO){ //target,targetId받아서 저장하기
		try {
			NoticeDTO createdNotice = noticeService.createNotice(noticeDTO);
			if(createdNotice == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			 return new ResponseEntity<>(createdNotice, HttpStatus.CREATED);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	//여러개
	
	//하나만
	@GetMapping("/{notice_id}")
	public ResponseEntity<NoticeDTO> getNotice(@PathVariable String notice_id){
		try {
			NoticeDTO noticeDTO = noticeService.findById(notice_id);
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(noticeDTO);
		}
		catch(Exception e) {
			System.out.print("notice_get: ");
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/{notice_id}")
	public ResponseEntity<NoticeDTO> updateNotice(@PathVariable String notice_id, @RequestBody NoticeDTO noticeDTO) {
		try {
			NoticeDTO updatedNoticeDTO = noticeService.updateNotice(Long.parseLong(notice_id),noticeDTO);
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(updatedNoticeDTO);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/{notice_id}")
	public ResponseEntity<NoticeDTO> deleteNotice(@PathVariable String notice_id, @RequestBody NoticeDTO noticeDTO){
		try {
			NoticeDTO deletedNotice = noticeService.deleteNotice(Long.parseLong(notice_id),noticeDTO);
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(deletedNotice);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
