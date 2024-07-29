package com.ict.traveljoy.service.tripReview;

import com.ict.traveljoy.repository.tripReview.TripReview;
import com.ict.traveljoy.repository.image.Image;
import com.ict.traveljoy.repository.tripReview.TripReviewPhoto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripReviewPhotoDto {
    private Long tripReviewPhotoId;
    private Long tripReviewId;
    private Long imageId;
    private String imageUrl;

    public static TripReviewPhotoDto toDto(TripReviewPhoto tripReviewPhoto) {
        return TripReviewPhotoDto.builder()
                .tripReviewPhotoId(tripReviewPhoto.getTripReviewPhotoId())
                .tripReviewId(tripReviewPhoto.getTripReview().getTripReviewId()) // 수정된 부분
                .imageId(tripReviewPhoto.getImage().getId())
                .imageUrl(tripReviewPhoto.getImage().getImageUrl())
                .build();
    }

    public TripReviewPhoto toEntity() {
        TripReview tripReview = TripReview.builder()
                .tripReviewId(tripReviewId)
                .build();

        Image image = Image.builder()
                .id(imageId)
                .build();

        return TripReviewPhoto.builder()
                .tripReviewPhotoId(tripReviewPhotoId)
                .tripReview(tripReview)
                .image(image)
                .build();
    }
}
