package com.ict.traveljoy.question.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long>{

	List<Question> findAllByQuestionCategory_Id(Long categoryId);

	List<Question> findAllByUser_Id(Long id);

}
