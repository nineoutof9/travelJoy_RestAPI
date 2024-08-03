package com.ict.traveljoy.history.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.history.repository.SearchHistoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchHistoryService {

	private final SearchHistoryRepository searchHistoryRepository;
	private final ObjectMapper objectMapper;
}
