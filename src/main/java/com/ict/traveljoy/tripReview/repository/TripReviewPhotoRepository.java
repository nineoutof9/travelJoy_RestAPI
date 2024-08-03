package com.ict.traveljoy.tripReview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripReviewPhotoRepository extends JpaRepository<TripReviewPhoto, Long> {

    // 특정 TripReview ID로 모든 TripReviewPhoto 찾기
    List<TripReviewPhoto> findByTripReview_TripReviewId(Long tripReviewId);

    // 특정 Image ID로 모든 TripReviewPhoto 찾기
    List<TripReviewPhoto> findByImage_Id(Long imageId);

    // 특정 TripReview와 Image로 TripReviewPhoto 찾기
    Optional<TripReviewPhoto> findByTripReview_TripReviewIdAndImage_Id(Long tripReviewId, Long imageId);

    // TripReviewPhoto ID로 TripReviewPhoto 찾기
    Optional<TripReviewPhoto> findByTripReviewPhotoId(Long tripReviewPhotoId);
}
