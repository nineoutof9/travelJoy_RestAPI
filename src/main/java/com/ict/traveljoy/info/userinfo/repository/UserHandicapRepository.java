package com.ict.traveljoy.info.userinfo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHandicapRepository extends JpaRepository<UserHandicap, Long>{
	
}
