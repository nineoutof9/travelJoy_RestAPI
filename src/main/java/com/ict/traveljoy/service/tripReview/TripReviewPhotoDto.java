package com.ict.traveljoy.service.tripReview;

import com.ict.traveljoy.repository.image.Image;
import com.ict.traveljoy.repository.tripReview.TripReview;
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

    // TripReviewPhoto 엔티티를 TripReviewPhotoDto로 변환
    public static TripReviewPhotoDto toDto(TripReviewPhoto tripReviewPhoto) {
        if (tripReviewPhoto == null) {
            return null;
        }
        TripReviewPhotoDto dto = new TripReviewPhotoDto();
        dto.setTripReviewPhotoId(tripReviewPhoto.getTripReviewPhotoId());
        dto.setTripReviewId(tripReviewPhoto.getTripReview().getTripReviewId());
        dto.setImageId(tripReviewPhoto.getImage().getId()); // 수정: getId()로 이미지 ID 가져오기
        dto.setImageUrl(tripReviewPhoto.getImage().getImageUrl()); // 수정: getImageUrl()로 이미지 URL 가져오기
        return dto;
    }

    // TripReviewPhotoDto를 TripReviewPhoto 엔티티로 변환
    public TripReviewPhoto toEntity() {
        TripReview tripReview = TripReview.builder()
                .tripReviewId(tripReviewId)
                .build();

        Image image = Image.builder()
                .id(imageId) // 수정: long 타입의 id 설정
                .build();

        return TripReviewPhoto.builder()
                .tripReviewPhotoId(tripReviewPhotoId)
                .tripReview(tripReview)
                .image(image)
                .build();
    }
}
