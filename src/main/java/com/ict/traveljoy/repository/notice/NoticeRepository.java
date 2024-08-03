package com.ict.traveljoy.repository.notice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface NoticeRepository extends JpaRepository<Notice, Long>{
	
	List<Notice> findByTitleContaining(String title);
}
