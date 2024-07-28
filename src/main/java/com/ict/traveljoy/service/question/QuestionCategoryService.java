package com.ict.traveljoy.service.question;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.repository.question.QuestionCategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionCategoryService {

	private final QuestionCategoryRepository questionCategoryRepository;
	private final ObjectMapper objectMapper;
}
