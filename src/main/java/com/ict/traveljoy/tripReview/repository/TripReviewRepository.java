package com.ict.traveljoy.tripReview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ict.traveljoy.plan.repository.Plan;

import java.util.List;

@Repository
public interface TripReviewRepository extends JpaRepository<TripReview, Long> {

    List<TripReview> findByWriter(String writer);

    List<TripReview> findByIsActive(Integer isActive);

    List<TripReview> findByPlan_id(Long planId);

    List<TripReview> findByTitleContaining(String title);
}
