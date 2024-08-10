package com.ict.traveljoy.feedback.service;

import com.ict.traveljoy.feedback.repository.Feedback;
import com.ict.traveljoy.plan.repository.Plan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackDto {
    private Long feedbackId;
    private Long planId; 
    private String owner;
    private Integer rate;
    
    public Feedback toEntity() {
        Feedback feedback = new Feedback();
        feedback.setFeedbackId(feedbackId);
        
        if (planId != null) {
            Plan plan = new Plan();
            plan.setId(planId);
            feedback.setPlan(plan);
        }
        
        feedback.setOwner(owner);
        feedback.setRate(rate);
        
        return feedback;
    }
    
    public static FeedbackDto toDto(Feedback feedback) {
        return FeedbackDto.builder()
                .feedbackId(feedback.getFeedbackId())
                .planId(feedback.getPlan() != null ? feedback.getPlan().getId() : null)
                .owner(feedback.getOwner())
                .rate(feedback.getRate())
                .build();
    }
}
