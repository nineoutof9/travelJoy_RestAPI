package com.ict.traveljoy.info.handicap.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HandicapRepository extends JpaRepository<Handicap, Long>{

	Handicap findByHandicapType(String added);
	
	
	
}
