package com.ict.traveljoy.notice.service;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.notice.repository.Notice;
import com.ict.traveljoy.notice.repository.NoticeRepository;
import com.ict.traveljoy.users.repository.UserRepository;
import com.ict.traveljoy.users.repository.Users;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeService {
	
	private final ViewCountService viewCountService;
	private final UserRepository userRepository;
	private final NoticeRepository noticeRepository;
	
	private final ObjectMapper objectMapper;
	
	@Transactional
	public NoticeDTO createNotice(String useremail, NoticeDTO noticeDto) {
		
		Users user = userRepository.findByEmail(useremail).get();
		
		Notice notice = noticeDto.toEntity();
		notice.setUser(user);
		Notice afterSave = noticeRepository.save(notice);
		viewCountService.createByNoticeId(afterSave.getId());
        return NoticeDTO.toDTO(afterSave);
	}
	
	@Transactional(readOnly = true)
//	public List<NoticeDTO> findByTitle(String title) {
//		List<Notice> noticeList = noticeRepository.findByTitleContaining(title);
//        return noticeList.stream()
//                      .map(NoticeDTO::toDTO)
//                      .collect(Collectors.toList());
//	}

	public NoticeDTO findById(String notice_id) {
		if(noticeRepository.existsById(Long.parseLong(notice_id))) {
			Notice notice = noticeRepository.findById(Long.parseLong(notice_id)).get();
			return NoticeDTO.toDTO(notice);
		}
		else throw new IllegalArgumentException("오류");
	}
	
	@Transactional(readOnly = true)
	public List<NoticeDTO> findAll() {
		List<Notice> noticeList = noticeRepository.findAll();
        return noticeList.stream()
                      .map(NoticeDTO::toDTO)
                      .collect(Collectors.toList());
	}
	
	@Transactional
	public NoticeDTO updateNotice(Long notice_id,NoticeDTO updateNoticeDTO) {
		if(noticeRepository.existsById(notice_id)) {
			Notice beforeNotice = noticeRepository.findById(notice_id).orElseThrow(() -> new RuntimeException("Notice not found"));
			beforeNotice.setTitle(updateNoticeDTO.getTitle());
			beforeNotice.setContent(updateNoticeDTO.getContent());
			beforeNotice.setUser(updateNoticeDTO.getUser());
			//제목, 내용만 변경
//			Notice afterNotice = noticeDTO.toEntity();
	        Notice updatedNotice = noticeRepository.save(beforeNotice);
	        return NoticeDTO.toDTO(updatedNotice);
		}
		else throw new IllegalArgumentException("오류");
		


	}

	@Transactional
	public NoticeDTO deleteNotice(Long id,NoticeDTO deleteNoticeDTO) {
		if(noticeRepository.existsById(id)) {
			Notice notice = noticeRepository.findById(id).orElseThrow(() -> new RuntimeException("Notice not found"));
			noticeRepository.delete(notice);
			return NoticeDTO.toDTO(notice);
		}
		else throw new IllegalArgumentException("오류");
		
	}



	
}
