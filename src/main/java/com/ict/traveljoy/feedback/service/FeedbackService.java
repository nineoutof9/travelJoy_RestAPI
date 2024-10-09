package com.ict.traveljoy.feedback.service;

import com.ict.traveljoy.feedback.repository.Feedback;
import com.ict.traveljoy.feedback.repository.FeedbackRepository;
import com.ict.traveljoy.plan.repository.Plan;
import com.ict.traveljoy.plan.repository.PlanRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final PlanRepository planRepository;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository, PlanRepository planRepository) {
        this.feedbackRepository = feedbackRepository;
        this.planRepository = planRepository;
    }

    public List<FeedbackDTO> getAllFeedbacks() {
        try {
            List<Feedback> feedbacks = feedbackRepository.findAll();
            return feedbacks.stream()
                    .map(FeedbackDTO::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving all feedbacks", e);
        }
    }
    
    // feedbackId로 특정 Feedback 조회
    public FeedbackDTO getFeedbackById(Long feedbackId) {
        try {
            Optional<Feedback> optionalFeedback = feedbackRepository.findById(feedbackId);
            if (optionalFeedback.isPresent()) {
                return FeedbackDTO.toDto(optionalFeedback.get());
            } else {
                return null; 
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving feedback by id", e);
        }
    }


    // planId로 Feedback 조회
    public List<FeedbackDTO> getFeedbacksByPlanId(Long planId) {
        try {
            List<Feedback> feedbacks = feedbackRepository.findByPlanId(planId);
            return feedbacks.stream()
                    .map(FeedbackDTO::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving feedbacks by planId", e);
        }
    }

    // 소유자(owner)로 Feedback 조회
    public List<FeedbackDTO> getFeedbacksByOwner(String owner) {
        try {
            List<Feedback> feedbacks = feedbackRepository.findByOwner(owner);
            return feedbacks.stream()
                    .map(FeedbackDTO::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving feedbacks by owner", e);
        }
    }

    // 평점(rate)으로 Feedback 조회
    public List<FeedbackDTO> getFeedbacksByRate(Integer rate) {
        try {
            List<Feedback> feedbacks = feedbackRepository.findByRate(rate);
            return feedbacks.stream()
                    .map(FeedbackDTO::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving feedbacks by rate", e);
        }
    }

    // Feedback 저장
    public FeedbackDTO saveFeedback(FeedbackDTO feedbackDto) {
        try {
            Feedback feedback = feedbackDto.toEntity();
            // plan 설정 로직이 필요할 경우 여기에 추가
            // if (feedbackDto.getPlanId() != null) {
            //     Plan plan = new Plan();
            //     plan.setId(feedbackDto.getPlanId());
            //     feedback.setPlan(plan);
            // }
            Feedback savedFeedback = feedbackRepository.save(feedback);
            return FeedbackDTO.toDto(savedFeedback);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving feedback", e);
        }
    }

 // Feedback 수정
    public FeedbackDTO updateFeedback(FeedbackDTO feedbackDto) {
        try {
            Optional<Feedback> optionalFeedback = feedbackRepository.findById(feedbackDto.getId());
            if (optionalFeedback.isPresent()) {
                Feedback existingFeedback = optionalFeedback.get();

                if (feedbackDto.getPlan() != null && feedbackDto.getPlan().getId() != null) {
                    Optional<Plan> optionalPlan = planRepository.findById(feedbackDto.getPlan().getId());
                    if (optionalPlan.isPresent()) {
                        existingFeedback.setPlan(optionalPlan.get());
                    } else {
                        throw new RuntimeException("Plan with id " + feedbackDto.getPlan().getId() + " not found");
                    }
                }

                existingFeedback.setOwner(feedbackDto.getOwner());
                existingFeedback.setRate(feedbackDto.getRate());

                Feedback updatedFeedback = feedbackRepository.save(existingFeedback);
                return FeedbackDTO.toDto(updatedFeedback);
            } else {
                throw new RuntimeException("Feedback with id " + feedbackDto.getId() + " not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating feedback", e);
        }
    }

    // Feedback 삭제
    public void deleteFeedback(Long feedbackId) {
        try {
            if (feedbackRepository.existsById(feedbackId)) {
                feedbackRepository.deleteById(feedbackId);
            } else {
                throw new RuntimeException("Feedback with id " + feedbackId + " not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting feedback", e);
        }
    }
}
