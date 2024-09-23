package com.ict.traveljoy.report.repository;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.ict.traveljoy.question.repository.QuestionCategory;
import com.ict.traveljoy.users.repository.Users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="report")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Report {


	@Id
	@Column(name="REPORT_ID") //신고ID
	@SequenceGenerator(name = "seq_report",sequenceName = "seq_report",allocationSize = 1,initialValue = 1)
	@GeneratedValue(generator = "seq_report",strategy = GenerationType.SEQUENCE)
    private Long id;
    
	@ManyToOne
    @JoinColumn(name = "USER_ID") //신고자 ID
	private Users user;
    

	@ManyToOne
    @JoinColumn(name = "REPORT_CATEGORY_ID")
	private ReportCategory reportCategory;
    

	@JoinColumn(name = "TARGET_ID", nullable = false)//신고상대ID
    private Long targetId;
	
	@Column(name="IS_USER", columnDefinition = "NUMBER(1, 0)")
	@ColumnDefault("0")
	private Integer isUser;

	@Column(name="IS_NOTICE", columnDefinition = "NUMBER(1, 0)")
	@ColumnDefault("0")
	private Integer isNotice;

	@Column(name="IS_BOARD", columnDefinition = "NUMBER(1, 0)")
	@ColumnDefault("0")
	private Integer isBoard;

	@Column(name="IS_COMMENT", columnDefinition = "NUMBER(1, 0)")
	@ColumnDefault("0")
	private Integer isComment;
	
	@Column(name="IS_REVIEW", columnDefinition = "NUMBER(1, 0)")
	@ColumnDefault("0")
	private Integer isReview;

	@Column(name="IS_Answered", columnDefinition = "NUMBER(1, 0)")
	@ColumnDefault("0")
	private Integer isAnswered;

    

	@Column(name="REPORT_DATE", nullable = false)
	@ColumnDefault("SYSDATE")
	@CreationTimestamp
    private LocalDateTime reportDate;
    

	@Column(name="REPORT_CONTENT", nullable = false,length=2000)
    private String reportContent;
    
    
	@Column(name="REPORT_HANDLER_ID")
    private Long reportHandlerId;
    
	@Column(name="REPORT_HANDLER_NAME",length=50)
    private String reportHandlerName;
    

	@Column(name="REPORT_RESULT",length=2000)
    private String reportResult;
    
	@Column(name="REPORT_TITLE",length=2000)
    private String reportTitle;

    @Column(name="REPORT_RESULT_DATE")
	@ColumnDefault("SYSDATE")
	@CreationTimestamp
    private LocalDateTime reportResultDate;
    
}