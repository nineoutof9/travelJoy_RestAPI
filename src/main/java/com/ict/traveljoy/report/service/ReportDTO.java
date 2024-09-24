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

	private Users user; // 신고자
	private ReportCategory reportCategory; // 신고대상 유형 (기존 필드)
	private Long reportCategoryId; // 신고대상 유형의 ID (프론트엔드에서 넘어올 값)
	private Long targetId; // 신고상대 id

	private Boolean isUser;
	private Boolean isNotice;
	private Boolean isBoard;
	private Boolean isComment;
	private Boolean isReview;

	private Boolean isAnswered;

	private LocalDateTime reportDate;
	private String reportContent;
	private String reportTitle;
	private Long reportHandlerId;
	private String reportHandlerName;
	private String reportResult;
	private LocalDateTime reportResultDate;

	// Entity로 변환
	public Report toEntity() {
		return Report.builder()
				.id(id)
				.user(user)
				.reportCategory(reportCategory) // reportCategoryId를 이용해 설정된 값이 들어감
				.targetId(targetId)
				.isUser(isUser != null && true ? 1 : 0)
				.isNotice(isNotice != null && true ? 1 : 0)
				.isBoard(isBoard != null && true ? 1 : 0)
				.isComment(isComment != null && true ? 1 : 0)
				.isReview(isReview != null && true ? 1 : 0)
				.isAnswered(isAnswered != null && true ? 1 : 0)
				.reportDate(reportDate)
				.reportContent(reportContent)
				.reportHandlerId(reportHandlerId)
				.reportHandlerName(reportHandlerName)
				.reportResult(reportResult)
				.reportResultDate(reportResultDate)
				.reportTitle(reportTitle)
				.build();
	}

	// Entity에서 DTO로 변환
	public static ReportDTO toDTO(Report report) {
		return ReportDTO.builder()
				.id(report.getId())
				.user(report.getUser())
				.reportCategory(report.getReportCategory()) // 기존 필드
				.reportCategoryId(report.getReportCategory().getId()) // 새로 추가한 필드
				.targetId(report.getTargetId())
				.isUser(report.getIsUser() == 1 ? true : false)
				.isNotice(report.getIsNotice() == 1 ? true : false)
				.isBoard(report.getIsBoard() == 1 ? true : false)
				.isComment(report.getIsComment() == 1 ? true : false)
				.isReview(report.getIsReview() == 1 ? true : false)
				.isAnswered(report.getIsAnswered() == 1 ? true : false)
				.reportDate(report.getReportDate())
				.reportContent(report.getReportContent())
				.reportHandlerId(report.getReportHandlerId())
				.reportHandlerName(report.getReportHandlerName())
				.reportResult(report.getReportResult())
				.reportResultDate(report.getReportResultDate())
				.reportTitle(report.getReportTitle())
				.build();
	}
}
