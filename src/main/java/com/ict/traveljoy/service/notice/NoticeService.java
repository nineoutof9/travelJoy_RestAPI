package com.ict.traveljoy.service.notice;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ict.traveljoy.repository.notice.Notice;
import com.ict.traveljoy.repository.notice.NoticeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class NoticeService {
	

	private final NoticeRepository noticeRepository;
	
	@Transactional
	public NoticeDto createNotice(NoticeDto noticeDto) {
		Notice notice= noticeRepository.save(noticeDto.toEntity());
		return NoticeDto.toDto(notice);
	}
	
	@Transactional
	public List<NoticeDto> findAll(NoticeDto noticeDto) {
		return null;
	}

	@Transactional
	public NoticeDto noticeUpdate(NoticeDto noticeDto) {
		return null;
	}

	public NoticeDto noticeDelete(NoticeDto noticeDto) {
		return null;
	}
	
}
