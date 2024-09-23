package com.ict.traveljoy.info.allergy.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ict.traveljoy.info.userallergy.repository.UserAllergy;

@Repository
public interface AllergyRepository extends JpaRepository<Allergy, Long>{

	Allergy findByInterestTopic(String interestTopic);

	
}
