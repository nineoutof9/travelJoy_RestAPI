package com.ict.traveljoy.service.tripReview;

import com.ict.traveljoy.repository.plan.Plan;
import com.ict.traveljoy.repository.plan.PlanRepository;  // PlanRepository를 추가
import com.ict.traveljoy.repository.tripReview.TripReview;
import com.ict.traveljoy.repository.tripReview.TripReviewRepository;
import com.ict.traveljoy.repository.tripReview.TripReviewPhoto;
import com.ict.traveljoy.repository.tripReview.TripReviewPhotoRepository;
import com.ict.traveljoy.repository.image.Image;
import com.ict.traveljoy.repository.image.ImageRepository;  // ImageRepository를 추가
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TripReviewService {

    private final TripReviewRepository tripReviewRepository;
    private final PlanRepository planRepository;  // PlanRepository 추가
    private final TripReviewPhotoRepository tripReviewPhotoRepository;
    private final ImageRepository imageRepository;  // ImageRepository 추가

    @Autowired
    public TripReviewService(
            TripReviewRepository tripReviewRepository,
            PlanRepository planRepository,
            TripReviewPhotoRepository tripReviewPhotoRepository,
            ImageRepository imageRepository
    ) {
        this.tripReviewRepository = tripReviewRepository;
        this.planRepository = planRepository;
        this.tripReviewPhotoRepository = tripReviewPhotoRepository;
        this.imageRepository = imageRepository;
    }

    // 작성자로 TripReview 조회
    public List<TripReviewDto> getTripReviewsByWriter(String writer) {
        List<TripReview> tripReviews = tripReviewRepository.findByWriter(writer);
        return tripReviews.stream()
                .map(TripReviewDto::toDto)
                .collect(Collectors.toList());
    }

    // isActive 여부로 TripReview 조회
    public List<TripReviewDto> getTripReviewsByIsActive(String isActive) {
        List<TripReview> tripReviews = tripReviewRepository.findByIsActive(isActive);
        return tripReviews.stream()
                .map(TripReviewDto::toDto)
                .collect(Collectors.toList());
    }

    // planId로 TripReview 조회
    public List<TripReviewDto> getTripReviewsByPlanId(Long planId) {
        List<TripReview> tripReviews = tripReviewRepository.findByPlanId(planId);
        return tripReviews.stream()
                .map(TripReviewDto::toDto)
                .collect(Collectors.toList());
    }

    // 제목에 특정 단어가 포함된 TripReview 조회
    public List<TripReviewDto> getTripReviewsByTitleContaining(String title) {
        List<TripReview> tripReviews = tripReviewRepository.findByTitleContaining(title);
        return tripReviews.stream()
                .map(TripReviewDto::toDto)
                .collect(Collectors.toList());
    }

    // TripReview 저장
    public TripReviewDto saveTripReview(TripReviewDto tripReviewDto) {
        TripReview tripReview = tripReviewDto.toEntity();
        Plan plan = planRepository.findById(tripReviewDto.getPlanId())
                .orElseThrow(() -> new RuntimeException("Plan not found"));
        tripReview.setPlan(plan);
        TripReview savedTripReview = tripReviewRepository.save(tripReview);
        return TripReviewDto.toDto(savedTripReview);
    }

    // TripReview 수정
    public TripReviewDto updateTripReview(TripReviewDto tripReviewDto) {
        TripReview existingTripReview = tripReviewRepository.findById(tripReviewDto.getTripReviewId())
                .orElseThrow(() -> new RuntimeException("TripReview not found"));

        Plan plan = planRepository.findById(tripReviewDto.getPlanId())
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        existingTripReview.setPlan(plan);
        existingTripReview.setWriter(tripReviewDto.getWriter());
        existingTripReview.setTitle(tripReviewDto.getTitle());
        existingTripReview.setReviewContent(tripReviewDto.getReviewContent());
        existingTripReview.setUrl(tripReviewDto.getUrl());
        existingTripReview.setPostDate(tripReviewDto.getPostDate());
        existingTripReview.setIsActive(tripReviewDto.getIsActive());
        existingTripReview.setIsDelete(tripReviewDto.getIsDelete());
        existingTripReview.setDeleteDate(tripReviewDto.getDeleteDate());

        TripReview updatedTripReview = tripReviewRepository.save(existingTripReview);
        return TripReviewDto.toDto(updatedTripReview);
    }

    // TripReview 삭제
    public void deleteTripReview(Long tripReviewId) {
        if (tripReviewRepository.existsById(tripReviewId)) {
            tripReviewRepository.deleteById(tripReviewId);
        } else {
            throw new RuntimeException("TripReview not found");
        }
    }

    // TripReviewPhoto 추가
    public TripReviewPhotoDto addTripReviewPhoto(TripReviewPhotoDto tripReviewPhotoDto) {
        TripReview tripReview = tripReviewRepository.findById(tripReviewPhotoDto.getTripReviewId())
                .orElseThrow(() -> new RuntimeException("TripReview not found"));
        Image image = imageRepository.findById(tripReviewPhotoDto.getImageId())
                .orElseThrow(() -> new RuntimeException("Image not found"));

        TripReviewPhoto tripReviewPhoto = tripReviewPhotoDto.toEntity();
        tripReviewPhoto.setTripReview(tripReview);
        tripReviewPhoto.setImage(image);

        TripReviewPhoto savedTripReviewPhoto = tripReviewPhotoRepository.save(tripReviewPhoto);
        return TripReviewPhotoDto.toDto(savedTripReviewPhoto);
    }

    // TripReviewPhoto 삭제
    public void deleteTripReviewPhoto(Long tripReviewId, Long imageId) {
        TripReviewPhoto tripReviewPhoto = tripReviewPhotoRepository.findByTripReviewIdAndImageId(tripReviewId, imageId);
        if (tripReviewPhoto != null) {
            tripReviewPhotoRepository.delete(tripReviewPhoto);
        } else {
            throw new RuntimeException("TripReviewPhoto not found");
        }
    }
}
