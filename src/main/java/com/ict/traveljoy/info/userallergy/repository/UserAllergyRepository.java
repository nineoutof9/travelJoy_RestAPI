package com.ict.traveljoy.info.userallergy.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ict.traveljoy.info.allergy.repository.Allergy;
import com.ict.traveljoy.users.repository.Users;

@Repository
public interface UserAllergyRepository extends JpaRepository<UserAllergy, Long>{

	List<UserAllergy> findAllByUser_Id(Long id);

	boolean existsByUserAndAllergy(Users user, Allergy allergy);



	
}
