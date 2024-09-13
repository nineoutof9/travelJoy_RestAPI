package com.ict.traveljoy.tripReview.service;

import com.ict.traveljoy.plan.repository.Plan;
import com.ict.traveljoy.tripReview.repository.TripReview;
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
    private String writer;
    private String title;
    private String reviewContent;
    private String url;
    private Timestamp postDate;
    private Boolean isActive;
    private Boolean isDelete;
    private Timestamp deleteDate;
    private BigDecimal rating;

    public TripReview toEntity() {
        return TripReview.builder()
                .id(id)
                .plan(plan)  
                .writer(writer)
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
                .writer(tripReview.getWriter())
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
