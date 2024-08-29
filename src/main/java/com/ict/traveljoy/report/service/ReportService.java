package com.ict.traveljoy.report.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.report.repository.Report;
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
	
	//신고하기
	public ReportDTO createReport(String useremail,String category,ReportDTO reportDTO) {
		Users user = userRepository.findByEmail(useremail).get();
		
		reportDTO.setUser(user);
		Report report = reportDTO.toEntity();
		
		Report afterSave = reportRepository.save(report);
		return ReportDTO.toDTO(afterSave);
	}
	
	//모든 신고건
	public List<ReportDTO> getAll(String useremail) {
		//권한 확인하기
		List<Report> reportList = reportRepository.findAll();
		return reportList.stream().map(report->ReportDTO.toDTO(report)).collect(Collectors.toList());
	}
	
	// 특정 신고 건 가져오기
	public ReportDTO getReportById(long reportId,String useremail) {
		
		if(reportRepository.existsById(reportId)) {
			Report report = reportRepository.findById(reportId).orElseThrow(() -> new RuntimeException("Question not found"));
			return ReportDTO.toDTO(report);
		}
		else throw new IllegalArgumentException("오류");
	}
//	public List<ReportDTO> getReportedAllByUserId(long userId) {
//		List<Report> reportedList = reportRepository.findAllByUser_Id(userId);
//		return reportedList.stream().map(report->ReportDTO.toDTO(report)).collect(Collectors.toList());
//	}
	
	
	//한 신고, 당사자 조회
	public List<ReportDTO> getReportAllByUserId(long userId,String useremail) {
		List<Report> reportList = reportRepository.findAllByUser_Id(userId);
		return reportList.stream().map(report->ReportDTO.toDTO(report)).collect(Collectors.toList());
	}
	

	// 권한 유저만 가능
	public ReportDTO updateReport(long reportId, String useremail) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//신고 당사자만 가능
	public ReportDTO deleteById(long reportId, String useremail) {
		if(reportRepository.existsById(reportId)) {
			Report report = reportRepository.findById(reportId).orElseThrow(() -> new RuntimeException("Question not found"));
			reportRepository.delete(report);
			return ReportDTO.toDTO(report);
		}
		else throw new IllegalArgumentException("오류");
	}



}
