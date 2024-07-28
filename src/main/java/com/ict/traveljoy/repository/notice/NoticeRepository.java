package com.ict.traveljoy.repository.notice;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ict.traveljoy.service.notice.NoticeDto;

public interface NoticeRepository extends JpaRepository<Notice, Long>{

	Notice save(NoticeDto entity);
	
}
