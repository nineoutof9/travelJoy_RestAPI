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
        TripReview tripReview = new TripReview();
        tripReview.setTripReviewId(tripReviewId);
        tripReview.setWriter(writer);
        tripReview.setTitle(title);
        tripReview.setReviewContent(reviewContent);
        tripReview.setUrl(url);
        tripReview.setPostDate(postDate);
        tripReview.setIsActive(isActive);
        tripReview.setIsDelete(isDelete);
        tripReview.setDeleteDate(deleteDate);

        Plan plan = new Plan();
        plan.setPlanId(planId);
        tripReview.setPlan(plan);

        return tripReview;
    }

    // TripReview 엔티티를 TripReviewDto로 변환
    public static TripReviewDto toDto(TripReview tripReview) {
        return TripReviewDto.builder()
                .tripReviewId(tripReview.getTripReviewId())
                .planId(tripReview.getPlan().getPlanId())
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
