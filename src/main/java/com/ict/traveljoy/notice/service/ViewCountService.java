package com.ict.traveljoy.notice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
			ViewCount viewCountEntity = new ViewCount();
			viewCountEntity.setNotice(noticeEntity);
//			viewCountEntity.setUser(user);
			return ViewCountDTO.toDTO(viewCountRepository.save(viewCountEntity));
		}
		else throw new IllegalArgumentException("오류");
	}
	
	// 단순 횟수 받아오기
	@Transactional(readOnly = true) //notice id별 sum(count), datetime에 따라 증가
	public long findbyNoticeId(long noticeId) {
		if(noticeRepository.existsById(noticeId)) {
			
		}
//		if(noticeRepository.existsById(noticeId)) {
//			ViewCount viewCountEntity = viewCountRepository.findbyNoticeId
//		return ViewCountDTO.builder()
//				.notice(viewCountEntity)
////					.user()
//				.build();
//		}
//		else throw new IllegalArgumentException("오류");
		
		return 4;
	}
	
	// 횟수 증가
//	 //notice id별, user id별 방문횟수 증가
//	public long findbyNoticeId(long noticeId,long userId) {
//		if(noticeRepository.existsById(noticeId)) {
//			
//		}
//
//		return 4;
//	}
	
	

	public ViewCountDTO updateViewCount(long noticeId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
