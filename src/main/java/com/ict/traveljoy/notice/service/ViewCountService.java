package com.ict.traveljoy.notice.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.notice.repository.Notice;
import com.ict.traveljoy.notice.repository.NoticeRepository;
import com.ict.traveljoy.notice.repository.ViewCount;
import com.ict.traveljoy.notice.repository.ViewCountRepository;
import com.ict.traveljoy.users.repository.UserRepository;
import com.ict.traveljoy.users.repository.Users;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ViewCountService {

	private final ViewCountRepository viewCountRepository;
	private final NoticeRepository noticeRepository;
	private final UserRepository userRepository;
	private final ObjectMapper objectMapper;
	
	
	public ViewCountDTO createByNoticeId(long noticeId) {
		if(noticeRepository.existsById(noticeId)) {
			Notice noticeEntity = noticeRepository.findById(noticeId).get();
			// user_id 받아오기
//			Users user = noticeRepository.findById(notice_id).orElseThrow(() -> new RuntimeException("Notice not found"));
			return ViewCountDTO.builder()
				.notice(noticeEntity)
//				.user()
				.build();
		}
		else throw new IllegalArgumentException("오류");
	}
	
	public ViewCountDTO findbyId(long noticeId) {
//		if(noticeRepository.existsById(noticeId)) {
//			ViewCount viewCountEntity = viewCountRepository.findbyNoticeId
//		return ViewCountDTO.builder()
//				.notice(viewCountEntity)
////					.user()
//				.build();
//		}
//		else throw new IllegalArgumentException("오류");
		return null;
	}
	

	public ViewCountDTO updateViewCount(long noticeId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
