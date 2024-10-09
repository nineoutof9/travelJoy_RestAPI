package com.ict.traveljoy.plan.details.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanInterestRepository extends JpaRepository<PlanInterest, Long> {

    // planId로 PlanInterest 조회
    List<PlanInterest> findByPlanId(Long plan_Id);

    // interestId로 PlanInterest 조회
    List<PlanInterest> findByInterestId(Long interest_Id);

    // planId와 interestId로 PlanInterest 조회
    PlanInterest findByPlanIdAndInterestId(Long plan_Id, Long interest_Id);

    // 특정 관심사에 해당하는 PlanInterest 조회 // 여러 개의 ID를 검색하기 위해 In 키워드를 사용
    List<PlanInterest> findByInterestIdIn(List<Long> interest_Ids);

    // planId로 특정 PlanInterest 삭제
    void deleteByPlanId(Long plan_Id);
}
