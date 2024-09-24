package com.ict.traveljoy.info.userhandicap.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ict.traveljoy.info.handicap.repository.Handicap;
import com.ict.traveljoy.users.repository.Users;

@Repository
public interface UserHandicapRepository extends JpaRepository<UserHandicap, Long>{

	List<UserHandicap> findAllByUser_Id(Long id);

	boolean existsByUserAndHandicap(Users user, Handicap handicap);

	boolean existsByUser(Users user);
	
}
