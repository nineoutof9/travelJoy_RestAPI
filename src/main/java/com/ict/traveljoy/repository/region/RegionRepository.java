package com.ict.traveljoy.repository.region;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {
	//이름으로 Region 찾는 메소드
	Region findByName(String name);
}
