package com.ict.traveljoy.tripReview.service;

import com.ict.traveljoy.image.repository.Image;
import com.ict.traveljoy.image.repository.ImageRepository;
import com.ict.traveljoy.plan.repository.Plan;
import com.ict.traveljoy.plan.repository.PlanRepository;
import com.ict.traveljoy.tripReview.repository.TripReview;
import com.ict.traveljoy.tripReview.repository.TripReviewPhoto;
import com.ict.traveljoy.tripReview.repository.TripReviewPhotoRepository;
import com.ict.traveljoy.tripReview.repository.TripReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class TripReviewService {

    private final TripReviewRepository tripReviewRepository;
    private final TripReviewPhotoRepository tripReviewPhotoRepository;
    private final PlanRepository planRepository;

    @Autowired
    public TripReviewService(TripReviewRepository tripReviewRepository, TripReviewPhotoRepository tripReviewPhotoRepository, PlanRepository planRepository) {
        this.tripReviewRepository = tripReviewRepository;
        this.tripReviewPhotoRepository = tripReviewPhotoRepository;
        this.planRepository = planRepository;
    }

    @Transactional
    public TripReviewDTO createReview(TripReviewDTO TripReviewDTO) {
        // planId를 기반으로 Plan 객체를 설정
        if (TripReviewDTO.getPlanId() != null) {
            Optional<Plan> planOptional = planRepository.findById(TripReviewDTO.getPlanId());
            if (planOptional.isPresent()) {
                TripReviewDTO.setPlan(planOptional.get());
            } else {
                throw new IllegalArgumentException("Plan with ID " + TripReviewDTO.getPlanId() + " does not exist.");
            }
        }

        TripReview tripReview = TripReviewDTO.toEntity();
        TripReview savedTripReview = tripReviewRepository.save(tripReview);
        return TripReviewDTO.toDto(savedTripReview);
    }

    @Transactional
    public TripReviewDTO updateReview(Long tripReviewId, TripReviewDTO TripReviewDTO) {
        Optional<TripReview> tripReviewOptional = tripReviewRepository.findById(tripReviewId);
        if (tripReviewOptional.isEmpty()) {
            throw new IllegalArgumentException("TripReview with ID " + tripReviewId + " does not exist.");
        }

        TripReview tripReview = tripReviewOptional.get();
        tripReview.setWriter(TripReviewDTO.getWriter());
        tripReview.setTitle(TripReviewDTO.getTitle());
        tripReview.setReviewContent(TripReviewDTO.getReviewContent());
        tripReview.setUrl(TripReviewDTO.getUrl());
        tripReview.setPostDate(TripReviewDTO.getPostDate());
        tripReview.setIsActive(TripReviewDTO.getIsActive() ? 1 : 0);
        tripReview.setIsDelete(TripReviewDTO.getIsDelete() ? 1 : 0);
        tripReview.setDeleteDate(TripReviewDTO.getDeleteDate());

        if (TripReviewDTO.getPlan() != null) {
            Optional<Plan> planOptional = planRepository.findById(TripReviewDTO.getPlan().getId());
            if (planOptional.isEmpty()) {
                throw new IllegalArgumentException("Plan with ID " + TripReviewDTO.getPlan().getId() + " does not exist.");
            }
            tripReview.setPlan(planOptional.get());
        } else {
            tripReview.setPlan(null);
        }

        TripReview updatedTripReview = tripReviewRepository.save(tripReview);
        return TripReviewDTO.toDto(updatedTripReview);
    }

    @Transactional
    public void deleteReview(Long tripReviewId) {
        Optional<TripReview> tripReviewOptional = tripReviewRepository.findById(tripReviewId);
        if (tripReviewOptional.isEmpty()) {
            throw new IllegalArgumentException("TripReview with ID " + tripReviewId + " does not exist.");
        }
        TripReview tripReview = tripReviewOptional.get();
        tripReview.setIsDelete(1);
        tripReview.setDeleteDate(new Timestamp(System.currentTimeMillis()));
        tripReviewRepository.save(tripReview);
        System.out.println("Deleted review with ID: " + tripReviewId);
        
    }
    
    public List<TripReviewDTO> getAllReviews() {
        List<TripReview> tripReviews = tripReviewRepository.findAllActiveReviews();
        return tripReviews.stream().map(TripReviewDTO::toDto).toList();
    }

    public TripReviewDTO getReviewById(Long tripReviewId) {
        Optional<TripReview> tripReviewOptional = tripReviewRepository.findById(tripReviewId);
        if (tripReviewOptional.isEmpty()) {
            throw new IllegalArgumentException("TripReview with ID " + tripReviewId + " does not exist.");
        }
        return TripReviewDTO.toDto(tripReviewOptional.get());
    }

    public List<TripReviewDTO> getReviewsByWriter(String writer) {
        List<TripReview> tripReviews = tripReviewRepository.findByWriter(writer);
        return tripReviews.stream().map(TripReviewDTO::toDto).toList();
    }

    public List<TripReviewDTO> getReviewsByPlanId(Long planId) {
        Plan plan = new Plan();
        plan.setId(planId);
        List<TripReview> tripReviews = tripReviewRepository.findByPlan(plan);
        return tripReviews.stream().map(TripReviewDTO::toDto).toList();
    }

    public List<TripReviewDTO> getReviewsByTitleContaining(String title) {
        List<TripReview> tripReviews = tripReviewRepository.findByTitleContaining(title);
        return tripReviews.stream().map(TripReviewDTO::toDto).toList();
    }
}
