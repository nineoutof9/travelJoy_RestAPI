package com.ict.traveljoy.tripReview.service;

import com.ict.traveljoy.plan.repository.Plan;
import com.ict.traveljoy.tripReview.repository.TripReview;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripReviewDto {
    private Long id;
    private Plan plan;
    private String writer;
    private String title;
    private String reviewContent;
    private String url;
    private Timestamp postDate;
    private Boolean isActive;
    private Boolean isDelete;
    private Timestamp deleteDate;

    // TripReviewDto를 TripReview 엔티티로 변환
    public TripReview toEntity() {
//        Plan plan = new Plan();
//        plan.setId(planId);

        return TripReview.builder()
                //.tripReviewId(tripReviewId)
        		.id(id)
                .plan(plan)
                .writer(writer)
                .title(title)
                .reviewContent(reviewContent)
                .url(url)
                .postDate(postDate)
                .isActive(isActive == true ? 1 : 0)
                .isDelete(isDelete == true ? 1 : 0)
                .deleteDate(deleteDate)
                .build();
    }

    // TripReview 엔티티를 TripReviewDto로 변환
    public static TripReviewDto toDto(TripReview tripReview) {
        return TripReviewDto.builder()
                //.tripReviewId(tripReview.getTripReviewId())
        		.id(tripReview.getId())
                .plan(tripReview.getPlan())
                .writer(tripReview.getWriter())
                .title(tripReview.getTitle())
                .reviewContent(tripReview.getReviewContent())
                .url(tripReview.getUrl())
                .postDate(tripReview.getPostDate())
                .isActive(tripReview.getIsActive() == 1 ? true : false)
                .isDelete(tripReview.getIsDelete() == 1 ? true : false)
                .deleteDate(tripReview.getDeleteDate())
                .build();
    }
}
