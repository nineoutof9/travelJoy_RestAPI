package com.ict.traveljoy.report.service;


import java.time.LocalDateTime;

import com.ict.traveljoy.report.repository.Report;
import com.ict.traveljoy.report.repository.ReportCategory;
import com.ict.traveljoy.users.repository.Users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportDTO {

	private Long id;

	private Users user; //신고자
	private ReportCategory reportCategory; //신고대상 유형
	private Long targetId; //신고대상 id

	private Boolean isUser;
	private Boolean isNotice;
	private Boolean isBoard;
	private Boolean isComment;
	private Boolean isReview;
	

	private Boolean isAnswered;
	
	private LocalDateTime reportDate;
	private String reportContent;
	private Long reportHandlerId;
	private String reportHandlerName;
	private String reportResult;
	private LocalDateTime reportResultDate;
	
	public Report toEntity() {
		return Report.builder()
				.id(id)
				.user(user)
				.reportCategory(reportCategory)
				.targetId(targetId)
				.isUser(isUser!=null&&true?1:0)
				.isNotice(isNotice!=null&&true?1:0)
				.isBoard(isBoard!=null&&true?1:0)
				.isComment(isComment!=null&&true?1:0)
				.isReview(isReview!=null&&true?1:0)

				.isAnswered(isAnswered!=null&&true?1:0)

				.reportDate(reportDate)
				.reportContent(reportContent)
				.reportHandlerId(reportHandlerId)
				.reportHandlerName(reportHandlerName)
				.reportResult(reportResult)
				.reportResultDate(reportResultDate)
				.build();
	}
	
	public static ReportDTO toDTO(Report report) {
		return ReportDTO.builder()
				.id(report.getId())
				.user(report.getUser())
				.reportCategory(report.getReportCategory())
				.targetId(report.getTargetId())
				.isUser(report.getIsUser()==1?true:false)
				.isNotice(report.getIsNotice()==1?true:false)
				.isBoard(report.getIsBoard()==1?true:false)
				.isComment(report.getIsComment()==1?true:false)
				.isReview(report.getIsReview()==1?true:false)

				.isAnswered(report.getIsAnswered()==1?true:false)

				.reportDate(report.getReportDate())
				.reportContent(report.getReportContent())
				.reportHandlerId(report.getReportHandlerId())
				.reportHandlerName(report.getReportHandlerName())
				.reportResult(report.getReportResult())
				.reportResultDate(report.getReportResultDate())
				.build();
	}
	
	
}
