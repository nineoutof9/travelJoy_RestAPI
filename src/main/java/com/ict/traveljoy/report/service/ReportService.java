package com.ict.traveljoy.report.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.report.repository.Report;
import com.ict.traveljoy.report.repository.ReportCategory;
import com.ict.traveljoy.report.repository.ReportCategoryRepository;
import com.ict.traveljoy.report.repository.ReportRepository;
import com.ict.traveljoy.users.repository.UserRepository;
import com.ict.traveljoy.users.repository.Users;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {
	
	private final ReportRepository reportRepository;
	private final UserRepository userRepository;
	private final ObjectMapper objectMapper;
	private final ReportCategoryRepository reportCategoryRepository;
	
	//신고하기
	public Report createReport(String useremail, String categoryId, ReportDTO reportDTO) {
	 
	    // reportCategoryId로 ReportCategory 조회
	    
	    Users user = userRepository.findByEmail(useremail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + useremail));
	    
	    reportDTO.setUser(user);
	    Report report = reportDTO.toEntity();
	    
	    ReportCategory reportCategory = reportCategoryRepository.findById(Long.parseLong(categoryId))
	        .orElseThrow(() -> new IllegalArgumentException("Invalid report category ID: " + categoryId));

	    report.setReportCategory(reportCategory); // ReportCategory 설정

	    // Report 저장
	    Report afterSave = reportRepository.save(report);
	    return afterSave;
	}
	
	// 모든 신고건
	public List<ReportDTO> getAll(String useremail) {
	    // 권한 확인
	    Users user = userRepository.findByEmail(useremail)
	                    .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + useremail));
	    
	    if (user.getPermission().equalsIgnoreCase("ROLE_ADMIN")) {
	        List<Report> reportList = reportRepository.findAll();
	        return reportList.stream().map(report -> ReportDTO.toDTO(report)).collect(Collectors.toList());
	    } else {
	        throw new IllegalArgumentException("권한이 없습니다.");
	    }
	}

	// 특정 신고 건 가져오기
	public ReportDTO getReportById(long reportId, String useremail) {
	    Users user = userRepository.findByEmail(useremail)
	                    .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + useremail));
	    
	    Report report = reportRepository.findById(reportId)
	                    .orElseThrow(() -> new RuntimeException("Report not found with id: " + reportId));
	    
	    if (user.getPermission().equalsIgnoreCase("ROLE_ADMIN") || report.getUser().getId() == user.getId()) {
	        return ReportDTO.toDTO(report);
	    } else {
	        throw new IllegalArgumentException("권한이 없습니다.");
	    }
	}

	// 한 신고, 당사자 조회
	public List<ReportDTO> getReportAllByUserId(String useremail) {
	    Users user = userRepository.findByEmail(useremail)
	                    .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + useremail));
	    
	    List<Report> reportList = reportRepository.findAllByUser_Id(user.getId());
	    return reportList.stream().map(report -> ReportDTO.toDTO(report)).collect(Collectors.toList());
	}

	// 권한 유저만 가능
	public ReportDTO updateReport(long reportId, String useremail, ReportDTO reportDTO) {
	    Users user = userRepository.findByEmail(useremail)
	                    .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + useremail));
	    
	    if (user.getPermission().equalsIgnoreCase("ROLE_ADMIN")) {
	        Report report = reportRepository.findById(reportId)
	                        .orElseThrow(() -> new RuntimeException("Report not found with id: " + reportId));
	        
	        report.setReportHandlerId(user.getId());
	        report.setReportHandlerName(user.getName());
	        report.setReportResult(reportDTO.getReportResult());
	        report.setReportResultDate(reportDTO.getReportResultDate());
	        report.setIsAnswered(1);
	        
	        Report afterSave = reportRepository.save(report);
	        return ReportDTO.toDTO(afterSave);
	    } else {
	        throw new IllegalArgumentException("권한이 없습니다.");
	    }
	}

	// 신고 당사자만 가능
	public ReportDTO deleteById(long reportId, String useremail) {
	    Users user = userRepository.findByEmail(useremail)
	                    .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + useremail));
	    
	    Report report = reportRepository.findById(reportId)
	                    .orElseThrow(() -> new RuntimeException("Report not found with id: " + reportId));
	    
	    if (report.getUser().getId() == user.getId()) {
	        reportRepository.delete(report);
	        return ReportDTO.toDTO(report);
	    } else {
	        throw new IllegalArgumentException("신고한 본인만 취소가능합니다.");
	    }
	}



}
