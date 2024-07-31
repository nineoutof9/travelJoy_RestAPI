package com.ict.traveljoy.service.report;


import java.time.LocalDateTime;

import com.ict.traveljoy.repository.report.Report;

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

	private long id;
	private long userId;
	private int reportCategoryId;
	private long targetId;
	private LocalDateTime reportDate;
	private String reportContent;
	private long reportHandlerId;
	private String reportHandlerName;
	private String reportResult;
	private LocalDateTime reportResultDate;
	
	public Report toEntity() {
		return Report.builder()
				.id(id)
				.userId(userId)
				.reportCategoryId(reportCategoryId)
				.targetId(targetId)
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
				.userId(report.getUserId())
				.reportCategoryId(report.getReportCategoryId())
				.targetId(report.getTargetId())
				.reportDate(report.getReportDate())
				.reportContent(report.getReportContent())
				.reportHandlerId(report.getReportHandlerId())
				.reportHandlerName(report.getReportHandlerName())
				.reportResult(report.getReportResult())
				.reportResultDate(report.getReportResultDate())
				.build();
	}
	
	
}
