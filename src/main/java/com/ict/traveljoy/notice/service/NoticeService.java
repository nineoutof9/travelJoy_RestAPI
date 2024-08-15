package com.ict.traveljoy.notice.service;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ict.traveljoy.notice.repository.Notice;
import com.ict.traveljoy.notice.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeService {
	

	private final NoticeRepository noticeRepository;
	
	@Transactional
	public NoticeDTO createNotice(NoticeDTO noticeDto) {
		Notice notice = noticeDto.toEntity();
        Notice savedNotice = noticeRepository.save(notice);
        return NoticeDTO.toDto(savedNotice);
	}
	
	@Transactional
	public List<NoticeDTO> findByTitle(String title) {
		List<Notice> notices = noticeRepository.findByTitleContaining(title);
        return notices.stream()
                      .map(NoticeDTO::toDto)
                      .collect(Collectors.toList());
	}

	public NoticeDTO findById(String notice_id) {
		if(noticeRepository.existsById(Long.parseLong(notice_id))) {
			Notice notice = noticeRepository.findById(Long.parseLong(notice_id)).get();
			return NoticeDTO.toDto(notice);
		}
		else throw new IllegalArgumentException("오류");
	}
	
	@Transactional
	public List<NoticeDTO> findAll() {
		List<Notice> notices = noticeRepository.findAll();
        return notices.stream()
                      .map(NoticeDTO::toDto)
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
	        return NoticeDTO.toDto(updatedNotice);
		}
		else throw new IllegalArgumentException("오류");
		


	}

	@Transactional
	public NoticeDTO deleteNotice(Long id,NoticeDTO deleteNoticeDTO) {
		if(noticeRepository.existsById(id)) {
			Notice notice = noticeRepository.findById(id).orElseThrow(() -> new RuntimeException("Notice not found"));
			noticeRepository.delete(notice);
			return NoticeDTO.toDto(notice);
		}
		else throw new IllegalArgumentException("오류");
		
	}



	
}
