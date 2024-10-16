package com.ict.traveljoy.report.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.controller.CheckContainsUseremail;
import com.ict.traveljoy.report.repository.Report;
import com.ict.traveljoy.report.repository.ReportCategory;
import com.ict.traveljoy.report.repository.ReportCategoryRepository;
import com.ict.traveljoy.report.repository.ReportRepository;
import com.ict.traveljoy.report.service.ReportCategoryDTO;
import com.ict.traveljoy.report.service.ReportCategoryService;
import com.ict.traveljoy.report.service.ReportDTO;
import com.ict.traveljoy.report.service.ReportService;
import com.ict.traveljoy.users.repository.Users;
import com.ict.traveljoy.users.service.UserDTO;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {

	private final ReportService reportService;
	private final ReportRepository reportRepository;
	private final ReportCategoryService reportCategoryService;
	private final ReportCategoryRepository reportCategoryRepository;
	private final ObjectMapper objectMapper;
	private final CheckContainsUseremail checkUser;
	
	// 신고 넣기
	@PostMapping
	public ResponseEntity<ReportDTO> createReport(@RequestBody ReportDTO reportDTO, HttpServletRequest request) {
		System.out.println("==========Report Category ID: " + reportDTO.getReportCategoryId());
	    Report report = new Report();
	    System.out.println("==========Report Category ID: " + reportDTO.getReportCategoryId());
	    String email = checkUser.checkContainsUseremail(request);
	    
	    
	 // reportCategoryId로 ReportCategory 객체를 조회
	   // ReportCategory reportCategory = reportCategoryRepository.findById(reportDTO.getReportCategoryId())
	     //   .orElseThrow(() -> new IllegalArgumentException("Invalid report category ID: " + reportDTO.getReportCategoryId()));
	    ReportCategory reportCategory = reportCategoryRepository.findById(reportDTO.getReportCategoryId())
	    	    .orElseThrow(() -> new IllegalArgumentException("Invalid report category ID: " + reportDTO.getReportCategoryId()));
	    	System.out.println("Report Category: " + reportCategory); // 여기서 reportCategory가 잘 출력되는지 확인
	    	
	    	
	    // 조회한 ReportCategory 객체를 Report에 설정
	    report.setReportCategory(reportCategory);
	    System.out.println("리포트카테고리:"+reportCategory.getId().toString());
	    System.out.println("==========Report Category ID: " + reportDTO.getReportCategoryId());
	    report.setReportContent(reportDTO.getReportContent());
	    System.out.println("내용:"+reportDTO.getReportContent());
	    report.setReportDate(reportDTO.getReportDate());
	    System.out.println("날짜:"+reportDTO.getReportDate());
	    report.setTargetId(reportDTO.getTargetId());
	    System.out.println("타겟:"+reportDTO.getTargetId());
	    System.out.println("메일:"+email);
	    
	    // Report 저장

	    Report savedReport = reportService.createReport(email, reportCategory.getId().toString(), reportDTO);
	    
	    // 저장된 Report를 ReportDTO로 변환하여 반환
	    return ResponseEntity.ok(ReportDTO.toDTO(savedReport));
	    
	    
	}


	// 모든 신고 조회
	@GetMapping("/all")
	public ResponseEntity<List<ReportDTO>> getReportAll(HttpServletRequest request) {
		String useremail = checkUser.checkContainsUseremail(request);
		try {
			List<ReportDTO> reportList = reportService.getAll(useremail);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/json").body(reportList);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	// 신고 카테고리 조회
	@GetMapping("/category")
	public ResponseEntity<List<ReportCategoryDTO>> getReportCategoryNames() {
		try {
			List<ReportCategoryDTO> reportCategories = reportCategoryService.getAllCategory();
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/json").body(reportCategories);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	// 특정 신고 조회
	@GetMapping("/{id}")
	public ResponseEntity<ReportDTO> getReportById(@PathVariable("id") Long id, HttpServletRequest request) {
		String useremail = checkUser.checkContainsUseremail(request);
		try {
			ReportDTO report = reportService.getReportById(id, useremail);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/json").body(report);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	// 한 신고 조회
	@GetMapping("/myreport")
	public ResponseEntity<List<ReportDTO>> getReportHistory(HttpServletRequest request) {
		String useremail = checkUser.checkContainsUseremail(request);
		try {
			List<ReportDTO> reportList = reportService.getReportAllByUserId(useremail);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/json").body(reportList);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	// 신고 처리
	@PutMapping("/{id}")
	public ResponseEntity<ReportDTO> handleReport(@PathVariable("id") Long id, HttpServletRequest request, @RequestBody ReportDTO reportDTO) {
		String useremail = checkUser.checkContainsUseremail(request);
		try {
			ReportDTO updatedReport = reportService.updateReport(id, useremail, reportDTO);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/json").body(updatedReport);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 신고 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<ReportDTO> deleteReport(@PathVariable("id") Long id, HttpServletRequest request) {
		String useremail = checkUser.checkContainsUseremail(request);
		try {
			ReportDTO deletereportDTO = reportService.deleteById(id, useremail);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/json").body(deletereportDTO);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
