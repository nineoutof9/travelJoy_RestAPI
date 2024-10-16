package com.ict.traveljoy.plan.details.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanAllergyRepository extends JpaRepository<PlanAllergy, Long> {
	// 특정 Plan에 대한 PlanAllergy 목록 조회
    List<PlanAllergy> findByPlan_Id(Long plan_Id);

    // 특정 Allergy에 대한 PlanAllergy 목록 조회
    List<PlanAllergy> findByAllergy_Id(Long allergy_Id);
}