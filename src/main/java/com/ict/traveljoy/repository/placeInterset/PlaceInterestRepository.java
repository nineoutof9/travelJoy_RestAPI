package com.ict.traveljoy.repository.placeInterset;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceInterestRepository extends JpaRepository<PlaceInterest, Long> {
    
}
