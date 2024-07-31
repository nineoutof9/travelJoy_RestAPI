package com.ict.traveljoy.service.pushalarm;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.repository.pushalarm.PushAlarmRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PushAlarmService {

	private final PushAlarmRepository pushAlarmRepository;
	private final ObjectMapper objectMapper;
	
}
