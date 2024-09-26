package com.ict.traveljoy.newplan;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ict.traveljoy.users.repository.Users;

public interface NewPlanRepository extends JpaRepository<NewPlan, Long> {

	List<NewPlan> findAllByUser_Email(String email);
}