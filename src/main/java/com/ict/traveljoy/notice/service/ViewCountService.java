package com.ict.traveljoy.notice.service;

import java.util.NoSuchElementException;
import java.util.Optional;

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
	
	
	public ViewCountDTO createByNotice(Notice notice) {
		if(noticeRepository.existsById(notice.getId())) {
			
			ViewCount viewCountEntity = new ViewCount();
			viewCountEntity.setNotice(notice);
			viewCountEntity.setCount((long)0);
			
			return ViewCountDTO.toDTO(viewCountRepository.save(viewCountEntity));
		}
		else throw new IllegalArgumentException("오류");
	}
	
	// 단순 횟수 받아오기
	@Transactional(readOnly = true) //notice id별 sum(count), datetime에 따라 증가
	public ViewCount findbyNoticeId(Long noticeId) {
        if (noticeId == null || noticeId <= 0) {
            throw new IllegalArgumentException("Invalid noticeId: " + noticeId);
        }

        // NoticeId로 조회하는 로직
        // 예: 데이터베이스에서 조회
        Optional<ViewCount> viewCount = viewCountRepository.findById(noticeId);
        
        if (viewCount.isPresent()) {
            return viewCount.get();
        } else {
            // 필요한 경우, 공지사항 ID에 해당하는 뷰 카운트가 없을 때의 처리
            throw new NoSuchElementException("No view count found for noticeId: " + noticeId);
        }
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
		if(viewCountRepository.existsByNotice_Id(noticeId)) {
			
			// 추가적으로 check NoticeView
			ViewCount viewCount = viewCountRepository.findByNotice_Id(noticeId);
			System.out.println(viewCount.getCount());
			long update = viewCount.getCount()+1;
			viewCount.setCount(update);
			System.out.println("==============="+viewCount.getCount());
			ViewCount updatedViewCount = viewCountRepository.save(viewCount);
			return ViewCountDTO.toDTO(updatedViewCount);
		}
		else throw new NullPointerException("오류");
	}
	
}
