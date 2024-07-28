package com.ict.traveljoy.repository.notice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;




public interface NoticeRepository extends JpaRepository<Notice, Long>{
	// 제목으로 공지사항 조회
    List<Notice> findByTitleContaining(String title);

    // 작성자로 공지사항 조회
    List<Notice> findByWriter(String writer);

    // 활성 상태로 공지사항 조회
    List<Notice> findByIsActive(String isActive);

    // 삭제되지 않은 공지사항 조회
    List<Notice> findByIsDelete(String isDelete);

    // 특정 날짜 이후의 공지사항 조회
    List<Notice> findByDateAfter(LocalDateTime date);

    // ID로 공지사항 조회
    Optional<Notice> findById(Long id);
}
