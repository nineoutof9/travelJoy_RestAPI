package com.ict.traveljoy.service.report;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.repository.report.ReportCategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportCategoryService {

	private final ReportCategoryRepository reportCategoryRepository;
	private final ObjectMapper objectMapper;
}
