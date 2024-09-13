package com.ict.traveljoy.tripReview.service;

import com.ict.traveljoy.plan.repository.Plan;
import com.ict.traveljoy.plan.repository.PlanRepository;
import com.ict.traveljoy.tripReview.repository.TripReview;
import com.ict.traveljoy.tripReview.repository.TripReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class TripReviewService {

    private final TripReviewRepository tripReviewRepository;
    private final PlanRepository planRepository;

    @Autowired
    public TripReviewService(TripReviewRepository tripReviewRepository, PlanRepository planRepository) {
        this.tripReviewRepository = tripReviewRepository;
        this.planRepository = planRepository;
    }

    @Transactional
    public TripReviewDTO createReview(TripReviewDTO tripReviewDTO) {
        // planId를 기반으로 Plan 객체를 설정
        if (tripReviewDTO.getPlanId() != null) {
            Optional<Plan> planOptional = planRepository.findById(tripReviewDTO.getPlanId());
            if (planOptional.isPresent()) {
                tripReviewDTO.setPlan(planOptional.get());
            } else {
                throw new IllegalArgumentException("Plan with ID " + tripReviewDTO.getPlanId() + " does not exist.");
            }
        }

        // Validate rating
        if (tripReviewDTO.getRating() == null || tripReviewDTO.getRating().compareTo(BigDecimal.ZERO) < 0 || tripReviewDTO.getRating().compareTo(BigDecimal.valueOf(5.0)) > 0 || tripReviewDTO.getRating().scale() > 1) {
            throw new IllegalArgumentException("Rating must be between 0.0 and 5.0 with up to one decimal place.");
        }

        TripReview tripReview = tripReviewDTO.toEntity();
        TripReview savedTripReview = tripReviewRepository.save(tripReview);
        return TripReviewDTO.toDto(savedTripReview);
    }

    @Transactional
    public TripReviewDTO updateReview(Long tripReviewId, TripReviewDTO tripReviewDTO) {
        Optional<TripReview> tripReviewOptional = tripReviewRepository.findById(tripReviewId);
        if (tripReviewOptional.isEmpty()) {
            throw new IllegalArgumentException("TripReview with ID " + tripReviewId + " does not exist.");
        }

        TripReview tripReview = tripReviewOptional.get();
        tripReview.setWriter(tripReviewDTO.getWriter());
        tripReview.setTitle(tripReviewDTO.getTitle());
        tripReview.setReviewContent(tripReviewDTO.getReviewContent());
        tripReview.setUrl(tripReviewDTO.getUrl());
        tripReview.setPostDate(tripReviewDTO.getPostDate());
        tripReview.setIsActive(tripReviewDTO.getIsActive() ? 1 : 0);
        tripReview.setIsDelete(tripReviewDTO.getIsDelete() ? 1 : 0);
        tripReview.setDeleteDate(tripReviewDTO.getDeleteDate());

        if (tripReviewDTO.getPlan() != null) {
            Optional<Plan> planOptional = planRepository.findById(tripReviewDTO.getPlan().getId());
            if (planOptional.isEmpty()) {
                throw new IllegalArgumentException("Plan with ID " + tripReviewDTO.getPlan().getId() + " does not exist.");
            }
            tripReview.setPlan(planOptional.get());
        } else {
            tripReview.setPlan(null);
        }

        // Validate rating
        if (tripReviewDTO.getRating() == null || tripReviewDTO.getRating().compareTo(BigDecimal.ZERO) < 0 || tripReviewDTO.getRating().compareTo(BigDecimal.valueOf(5.0)) > 0 || tripReviewDTO.getRating().scale() > 1) {
            throw new IllegalArgumentException("Rating must be between 0.0 and 5.0 with up to one decimal place.");
        }
        tripReview.setRating(tripReviewDTO.getRating());

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
        List<TripReview> tripReviews = tripReviewRepository.findAll();
        return tripReviews.stream().map(TripReviewDTO::toDto).toList();
    }

    public TripReviewDTO getReviewById(Long id) {
        Optional<TripReview> tripReviewOptional = tripReviewRepository.findById(id);
        if (tripReviewOptional.isEmpty()) {
            throw new IllegalArgumentException("TripReview with ID " + id + " does not exist.");
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
