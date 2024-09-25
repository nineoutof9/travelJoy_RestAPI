package com.ict.traveljoy.tripReview.service;

import com.ict.traveljoy.plan.repository.Plan;
import com.ict.traveljoy.tripReview.repository.TripReview;
import com.ict.traveljoy.users.repository.Users;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripReviewDTO {
    private Long id;
    private Long planId; 
    private Plan plan;
    private Users user;
    private String title;
    private String reviewContent;
    private String url;
    private Timestamp postDate;
    private Boolean isActive;
    private Boolean isDelete;
    private Timestamp deleteDate;
    private BigDecimal rating;

    public TripReview toEntity() {
    	Plan planE = new Plan();
    	planE.setId(planId);   	
        return TripReview.builder()
                .id(id)
                .plan(plan)  
                .user(user)
                .title(title)
                .reviewContent(reviewContent)
                .url(url)
                .postDate(postDate)
                .isActive(isActive != null && isActive ? 1 : 0)
                .isDelete(isDelete != null && isDelete ? 1 : 0)
                .deleteDate(deleteDate)
                .rating(rating)
                .build();
    }

    public static TripReviewDTO toDto(TripReview tripReview) {
        return TripReviewDTO.builder()
                .id(tripReview.getId())
                .plan(tripReview.getPlan())
                .planId(tripReview.getPlan() != null ? tripReview.getPlan().getId() : null) 
                .user(tripReview.getUser())
                .title(tripReview.getTitle())
                .reviewContent(tripReview.getReviewContent())
                .url(tripReview.getUrl())
                .postDate(tripReview.getPostDate())
                .isActive(tripReview.getIsActive() == 1 ? true : false)
                .isDelete(tripReview.getIsDelete() == 1 ? true : false)
                .deleteDate(tripReview.getDeleteDate())
                .rating(tripReview.getRating())
                .build();
    }
}
