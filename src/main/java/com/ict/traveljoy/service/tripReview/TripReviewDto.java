package com.ict.traveljoy.service.tripReview;

import com.ict.traveljoy.repository.plan.Plan;
import com.ict.traveljoy.repository.tripReview.TripReview;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripReviewDto {
    private Long tripReviewId;
    private Long planId;
    private String writer;
    private String title;
    private String reviewContent;
    private String url;
    private Timestamp postDate;
    private String isActive;
    private String isDelete;
    private Timestamp deleteDate;

    // TripReviewDto를 TripReview 엔티티로 변환
    public TripReview toEntity() {
        Plan plan = new Plan();
        plan.setPlanId(planId);

        return TripReview.builder()
                .tripReviewId(tripReviewId)
                .plan(plan)
                .writer(writer)
                .title(title)
                .reviewContent(reviewContent)
                .url(url)
                .postDate(postDate)
                .isActive(isActive)
                .isDelete(isDelete)
                .deleteDate(deleteDate)
                .build();
    }

    // TripReview 엔티티를 TripReviewDto로 변환
    public static TripReviewDto fromEntity(TripReview tripReview) {
        return TripReviewDto.builder()
                .tripReviewId(tripReview.getTripReviewId())
                .planId(tripReview.getPlan() != null ? tripReview.getPlan().getPlanId() : null)
                .writer(tripReview.getWriter())
                .title(tripReview.getTitle())
                .reviewContent(tripReview.getReviewContent())
                .url(tripReview.getUrl())
                .postDate(tripReview.getPostDate())
                .isActive(tripReview.getIsActive())
                .isDelete(tripReview.getIsDelete())
                .deleteDate(tripReview.getDeleteDate())
                .build();
    }
}
