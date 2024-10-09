package com.ict.traveljoy.notice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface NoticeRepository extends JpaRepository<Notice, Long>{
	
// 이전 글 조회 (삭제되지 않은 공지사항만 조회)
    Optional<Notice> findFirstByIdLessThanAndIsDeleteFalseOrderByIdDesc(Long noticeId);

    // 다음 글 조회 (삭제되지 않은 공지사항만 조회)
    Optional<Notice> findFirstByIdGreaterThanAndIsDeleteFalseOrderByIdAsc(Long noticeId);

    // 이전 글 조회 (관리자는 삭제된 글도 조회 가능)
    Optional<Notice> findFirstByIdLessThanOrderByIdDesc(Long noticeId);

    // 다음 글 조회 (관리자는 삭제된 글도 조회 가능)
    Optional<Notice> findFirstByIdGreaterThanOrderByIdAsc(Long noticeId);
}
