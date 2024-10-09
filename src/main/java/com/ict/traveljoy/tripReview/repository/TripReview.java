package com.ict.traveljoy.tripReview.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.ict.traveljoy.newplan.NewPlan;
import com.ict.traveljoy.plan.repository.Plan;
import com.ict.traveljoy.users.repository.Users;

@Entity
@Table(name = "trip_review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripReview {

    @Id
    @SequenceGenerator(name = "seq_trip_review", sequenceName = "seq_trip_review", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "seq_trip_review", strategy = GenerationType.SEQUENCE)
    @Column(name = "TRIP_REVIEW_ID", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PLAN_ID")
    private Plan plan;
    
    @ManyToOne
    @JoinColumn(name = "NEW_PLAN_ID")
    private NewPlan newPlan;

    @ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private Users user;

    @Column(name = "TITLE", length = 50)
    private String title;

    @Column(name = "REVIEW_CONTENT", length = 2000)
    private String reviewContent;

    @Column(name = "URL", length = 200)
    private String url;

    @Column(name = "POST_DATE")
    @ColumnDefault("SYSDATE")
    @CreationTimestamp
    private Timestamp postDate;

    @Column(name = "IS_ACTIVE", length = 1, nullable = false, columnDefinition = "NUMBER(1, 0)")
    @ColumnDefault("1")
    private Integer isActive;

    @Column(name = "IS_DELETE", length = 1, nullable = false, columnDefinition = "NUMBER(1, 0)")
    @ColumnDefault("0")
    private Integer isDelete;

    @Column(name = "DELETE_DATE")
    private Timestamp deleteDate;

    @Column(name = "RATING", precision = 3, scale = 1, nullable = false)
    private BigDecimal rating;  // Rating field added
}