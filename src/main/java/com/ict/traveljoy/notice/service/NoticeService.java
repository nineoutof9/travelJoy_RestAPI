package com.ict.traveljoy.notice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public NoticeDTO createNotice(String useremail, NoticeDTO noticeDTO) {
        System.out.println("Creating notice for user: " + useremail); // Log user email
        System.out.println("Notice DTO: " + noticeDTO); // Log notice DTO

        Notice notice = noticeDTO.toEntity();

        Users user = userRepository.findByEmail(useremail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        notice.setUser(user);
        Notice afterSave = noticeRepository.save(notice);

        viewCountService.createByNotice(afterSave);
        return NoticeDTO.toDTO(afterSave);
    }

    @Transactional(readOnly = true)
    public NoticeDTO findById(long notice_id) {
        Notice notice = noticeRepository.findById(notice_id)
                .orElseThrow(() -> new IllegalArgumentException("Notice not found"));
        NoticeDTO noticeDTO = NoticeDTO.toDTO(notice);
        ViewCountDTO viewCountDTO = viewCountService.updateViewCount(notice_id);
        noticeDTO.setViewCount(viewCountDTO.getCount());
        return noticeDTO;
    }

    @Transactional(readOnly = true)
    public List<NoticeDTO> findAllForAdmin() {
        List<Notice> noticeList = noticeRepository.findAll();
        return noticeList.stream().map(notice -> {
            long count = viewCountService.findbyNoticeId(notice.getId()).getCount();
            NoticeDTO noticeDTO = NoticeDTO.toDTO(notice);
            noticeDTO.setViewCount(count);
            return noticeDTO;
        }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<NoticeDTO> findAllForUser() {
        List<Notice> noticeList = noticeRepository.findAll()
                .stream()
                .filter(notice -> !notice.isDeleted()) // Filter out deleted notices
                .collect(Collectors.toList());

        return noticeList.stream().map(notice -> {
            long count = viewCountService.findbyNoticeId(notice.getId()).getCount();
            NoticeDTO noticeDTO = NoticeDTO.toDTO(notice);
            noticeDTO.setViewCount(count);
            return noticeDTO;
        }).collect(Collectors.toList());
    }

    @Transactional
    public NoticeDTO updateNotice(Long notice_id, NoticeDTO updateNoticeDTO) {
        Notice beforeNotice = noticeRepository.findById(notice_id)
                .orElseThrow(() -> new RuntimeException("Notice not found"));
        beforeNotice.setTitle(updateNoticeDTO.getTitle());
        beforeNotice.setContent(updateNoticeDTO.getContent());
        beforeNotice.setUser(updateNoticeDTO.getUser());
        Notice updatedNotice = noticeRepository.save(beforeNotice);
        return NoticeDTO.toDTO(updatedNotice);
    }

    @Transactional
    public NoticeDTO deleteNotice(String useremail, Long notice_id) {
        Users user = userRepository.findByEmail(useremail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!"0".equals(user.getPermission())) {
            Notice notice = noticeRepository.findById(notice_id)
                    .orElseThrow(() -> new RuntimeException("Notice not found"));
            notice.setIsDelete(1); // Mark as deleted
            notice.setIsActive(0); // Optionally mark as inactive
            Notice deletedNotice = noticeRepository.save(notice);
            return NoticeDTO.toDTO(deletedNotice);
        } else {
            return NoticeDTO.builder().title("수정불가").content("권한없음").build();
        }
    }
    @Transactional(readOnly = true)
    public NoticeDTO findPreviousNotice(long notice_id, boolean isAdmin) {
        if (isAdmin) {
            return noticeRepository.findFirstByIdLessThanOrderByIdDesc(notice_id)
                    .map(NoticeDTO::toDTO)
                    .orElseThrow(() -> new RuntimeException("이전 공지사항이 없습니다."));
        } else {
            return noticeRepository.findFirstByIdLessThanAndIsDeleteFalseOrderByIdDesc(notice_id)
                    .map(NoticeDTO::toDTO)
                    .orElseThrow(() -> new RuntimeException("이전 공지사항이 없습니다."));
        }
    }

    @Transactional(readOnly = true)
    public NoticeDTO findNextNotice(long notice_id, boolean isAdmin) {
        if (isAdmin) {
            return noticeRepository.findFirstByIdGreaterThanOrderByIdAsc(notice_id)
                    .map(NoticeDTO::toDTO)
                    .orElseThrow(() -> new RuntimeException("다음 공지사항이 없습니다."));
        } else {
            return noticeRepository.findFirstByIdGreaterThanAndIsDeleteFalseOrderByIdAsc(notice_id)
                    .map(NoticeDTO::toDTO)
                    .orElseThrow(() -> new RuntimeException("다음 공지사항이 없습니다."));
        }
    }

}