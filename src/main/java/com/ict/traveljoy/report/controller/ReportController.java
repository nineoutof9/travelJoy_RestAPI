package com.ict.traveljoy.report.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.controller.CheckContainsUseremail;
import com.ict.traveljoy.report.service.ReportCategoryDTO;
import com.ict.traveljoy.report.service.ReportCategoryService;
import com.ict.traveljoy.report.service.ReportDTO;
import com.ict.traveljoy.report.service.ReportService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

	private final ReportService reportService;
	private final ReportCategoryService reportCategoryService;
	private final ObjectMapper objectMapper;
	private final CheckContainsUseremail checkUser;
	
	
	//신고 넣기
	//신고한 사람(useremail로 받음), reportCategory(is...로 구분), content,
	@PostMapping("/newreport")
	public ResponseEntity<ReportDTO> createReport(HttpServletRequest request,@RequestBody ReportDTO reportDTO){
		String useremail = checkUser.checkContainsUseremail(request);
		String category = request.getParameter("category");
		try {
			ReportDTO createReport = reportService.createReport(useremail,category,reportDTO);
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(createReport);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	
	
	//모든 신고
	@GetMapping("/all")
	public ResponseEntity<List<ReportDTO>> getReportAll(HttpServletRequest request){
		String useremail = checkUser.checkContainsUseremail(request);
		try {
			List<ReportDTO> reportList = reportService.getAll(useremail);
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(reportList);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	//신고 카테고리
	@GetMapping("/category")
	public ResponseEntity<List<ReportCategoryDTO>> getReportCategoryNames(){
		try {
			List<ReportCategoryDTO> reportCategories = reportCategoryService.getAllCategory();
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(reportCategories);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	//특정 신고가져오기
	@GetMapping("/{reportId}")
	public ResponseEntity<ReportDTO> getReportById(@PathVariable("reportId") String reportId,HttpServletRequest request){
		String useremail = checkUser.checkContainsUseremail(request);
		try {
			ReportDTO report = reportService.getReportById(Long.parseLong(reportId),useremail);
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(report);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	//특정 사용자 신고가져오기
	//당한신고
//	@GetMapping("/reported/{user_id}")
//	public ResponseEntity<List<ReportDTO>> getReportedHistory(@PathVariable String user_id){
//		try {
//			List<ReportDTO> reportList = reportService.getReportedAllByUserId(Long.parseLong(user_id));
//			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(reportList);
//		}
//		catch(Exception e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//		}
//	}
	
	//한신고
	@GetMapping("/report/{userId}")
	public ResponseEntity<List<ReportDTO>> getReportHistory(@PathVariable String userId,HttpServletRequest request){
		String useremail = checkUser.checkContainsUseremail(request);
		try {
			List<ReportDTO> reportList = reportService.getReportAllByUserId(Long.parseLong(userId),useremail);
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(reportList);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	//신고를 처리
	// reportHandlerId, reportHandlerName,reportResult
	@PutMapping("/{reportId}")
	public ResponseEntity<ReportDTO> handleReport(@PathVariable("reportId") String reportId,HttpServletRequest request){
		String useremail = checkUser.checkContainsUseremail(request);
		try {
			ReportDTO updatedReport = reportService.updateReport(Long.parseLong(reportId),useremail);
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(updatedReport);
		}
		catch(Exception e) {
			System.out.print("report_handled: ");
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//신고 삭제
	@DeleteMapping("/{reportId}")
	public ResponseEntity<ReportDTO> deleteReport(@PathVariable("reportId") String reportId,HttpServletRequest request){
		String useremail = checkUser.checkContainsUseremail(request);
		try {
			ReportDTO deletereportDTO = reportService.deleteById(Long.parseLong(reportId),useremail);
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(deletereportDTO);
		}
		catch(Exception e) {
			System.out.print("report_delete: ");
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
