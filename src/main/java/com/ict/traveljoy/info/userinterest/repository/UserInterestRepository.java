package com.ict.traveljoy.info.userinterest.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ict.traveljoy.info.interest.repository.Interest;
import com.ict.traveljoy.users.repository.Users;

@Repository
public interface UserInterestRepository extends JpaRepository<UserInterest, Long>{

	List<UserInterest> findAllByUser_Id(Long id);

	boolean existsByUserAndInterest(Users user, Interest interest);

	boolean existsByUser(Users user);
	
}
