package com.ict.traveljoy.repository.plans;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlansRepository extends JpaRepository<Plans, Long> {
	
	// planId로 Plans 조회
    Plans findByPlanId(Long planId);

    // isActive 여부로 Plans 조회
    List<Plans> findByIsActive(String isActive);

    // 특정 날짜 이후에 생성된 Plans 조회
    List<Plans> findByCreateDateAfter(LocalDateTime createDate);

    // 이름에 특정 단어가 포함된 Plans 조회
    List<Plans> findByPlanNameContains(String keyword);

}
