package com.ict.traveljoy.service.notice;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.repository.notice.ViewCountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ViewCountService {

	private final ViewCountRepository viewCountRepository;
	private final ObjectMapper objectMapper;
	
}
