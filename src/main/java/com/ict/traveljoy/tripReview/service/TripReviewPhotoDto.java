package com.ict.traveljoy.tripReview.service;

import com.ict.traveljoy.image.repository.Image;
import com.ict.traveljoy.tripReview.repository.TripReview;
import com.ict.traveljoy.tripReview.repository.TripReviewPhoto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripReviewPhotoDto {
    private Long id;
    private TripReview tripReview;
    private Image image;

    // TripReviewPhotoDto를 TripReviewPhoto 엔티티로 변환
    public TripReviewPhoto toEntity() {
//        TripReview tripReview = TripReview.builder()
//        		.id(id)
//                .tripReview(tripReview)
//                .build();
//
//        Image image = Image.builder()
//                .id(imageId) // 수정: long 타입의 id 설정
//                .build();

        return TripReviewPhoto.builder()
                .id(id)
                .tripReview(tripReview)
                .image(image)
                .build();
    }
    
    // TripReviewPhoto 엔티티를 TripReviewPhotoDto로 변환
    public static TripReviewPhotoDto toDto(TripReviewPhoto tripReviewPhoto) {
//        if (tripReviewPhoto == null) {
//            return null;
//        }
//        TripReviewPhotoDto dto = new TripReviewPhotoDto();
//        dto.setTripReviewPhotoId(tripReviewPhoto.getTripReviewPhotoId());
//        dto.setTripReviewId(tripReviewPhoto.getTripReview().getTripReviewId());
//        dto.setImageId(tripReviewPhoto.getImage().getId()); // 수정: getId()로 이미지 ID 가져오기
//        dto.setImageUrl(tripReviewPhoto.getImage().getImageUrl()); // 수정: getImageUrl()로 이미지 URL 가져오기
    	
        return TripReviewPhotoDto.builder()
        		.id(tripReviewPhoto.getId())
        		.tripReview(tripReviewPhoto.getTripReview())
        		.image(tripReviewPhoto.getImage())
        		.build();
    }

    
}
