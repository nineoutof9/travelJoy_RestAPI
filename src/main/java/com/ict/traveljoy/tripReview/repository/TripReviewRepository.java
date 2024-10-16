package com.ict.traveljoy.tripReview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ict.traveljoy.newplan.NewPlan;
import com.ict.traveljoy.plan.repository.Plan;

import java.util.List;

@Repository
public interface TripReviewRepository extends JpaRepository<TripReview, Long> {
	
	@Query("SELECT t FROM TripReview t WHERE t.isDelete = 0")
    List<TripReview> findAllActiveReviews();
	

    List<TripReview> findByIsActive(Integer isActive);

    List<TripReview> findByPlan(Plan plan);

    List<TripReview> findByTitleContaining(String title);


	List<TripReview> findAllByUser_Id(Long id);


	List<TripReview> findByNewPlan(NewPlan newPlan);
    
    
}