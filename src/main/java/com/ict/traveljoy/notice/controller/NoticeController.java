package com.ict.traveljoy.notice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ict.traveljoy.controller.CheckContainsUseremail;
import com.ict.traveljoy.notice.service.NoticeDTO;
import com.ict.traveljoy.notice.service.NoticeService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class NoticeController {

    private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);
    private final NoticeService noticeService;
    private final CheckContainsUseremail checkUser;

    @PostMapping
    public ResponseEntity<NoticeDTO> createNotice(@RequestBody NoticeDTO noticeDTO, HttpServletRequest request) {
        String useremail = checkUser.checkContainsUseremail(request);

        try {
            if (useremail == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            NoticeDTO createdNotice = noticeService.createNotice(useremail, noticeDTO);
            return new ResponseEntity<>(createdNotice, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating notice", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<NoticeDTO>> getAllNoticesForAdmin() {
        try {
            List<NoticeDTO> noticeList = noticeService.findAllForAdmin();
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/json").body(noticeList);
        } catch (Exception e) {
            logger.error("Error fetching all notices for admin", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all/user")
    public ResponseEntity<List<NoticeDTO>> getAllNoticesForUser() {
        try {
            List<NoticeDTO> noticeList = noticeService.findAllForUser();
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/json").body(noticeList);
        } catch (Exception e) {
            logger.error("Error fetching all notices for user", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{notice_id}")
    public ResponseEntity<NoticeDTO> getNotice(@PathVariable("notice_id") long notice_id) {
        try {
            NoticeDTO noticeDTO = noticeService.findById(notice_id);
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/json").body(noticeDTO);
        } catch (Exception e) {
            logger.error("Error fetching notice with ID: " + notice_id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{notice_id}")
    public ResponseEntity<NoticeDTO> updateNotice(@PathVariable("notice_id") long notice_id, @RequestBody NoticeDTO noticeDTO) {
        try {
            NoticeDTO updatedNoticeDTO = noticeService.updateNotice(notice_id, noticeDTO);
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/json").body(updatedNoticeDTO);
        } catch (Exception e) {
            logger.error("Error updating notice with ID: " + notice_id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{notice_id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<NoticeDTO> deleteNotice(@PathVariable("notice_id") long notice_id, HttpServletRequest request) {
        String useremail = checkUser.checkContainsUseremail(request);

        try {
            if (useremail == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            NoticeDTO deletedNotice = noticeService.deleteNotice(useremail, notice_id);
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/json").body(deletedNotice);
        } catch (Exception e) {
            logger.error("Error deleting notice with ID: " + notice_id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{notice_id}/prev")
    public ResponseEntity<NoticeDTO> getPreviousNotice(@PathVariable("notice_id") long notice_id, HttpServletRequest request) {
        boolean isAdmin = checkUser.checkContainsUseremail(request) != null; // 관리자 여부 확인

        try {
            NoticeDTO previousNotice = noticeService.findPreviousNotice(notice_id, isAdmin);
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/json").body(previousNotice);
        } catch (Exception e) {
            logger.error("Error fetching previous notice for ID: " + notice_id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{notice_id}/next")
    public ResponseEntity<NoticeDTO> getNextNotice(@PathVariable("notice_id") long notice_id, HttpServletRequest request) {
        boolean isAdmin = checkUser.checkContainsUseremail(request) != null; // 관리자 여부 확인

        try {
            NoticeDTO nextNotice = noticeService.findNextNotice(notice_id, isAdmin);
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/json").body(nextNotice);
        } catch (Exception e) {
            logger.error("Error fetching next notice for ID: " + notice_id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
