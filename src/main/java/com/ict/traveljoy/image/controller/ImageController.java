package com.ict.traveljoy.image.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.image.service.ImageDTO;
import com.ict.traveljoy.image.service.ImageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {
	
	private final ImageService imageService;
	private final ObjectMapper objectMapper;
	
	@PostMapping("/postImage")
	public ResponseEntity<List<Map<String, Object>>> updateProfile(
	        @RequestParam(value = "files", required = true) MultipartFile[] files,
	        @RequestParam Map<String, Object> imageInfo) {
	    List<Map<String, Object>> responseList = new ArrayList<>();
	    try {
	        String imageType = (String) imageInfo.get("imageType");
	        String dirName = (String) imageInfo.get("dirName");

	        // 각 파일에 대해 반복
	        for (MultipartFile file : files) {
	            ImageDTO imageDTO = ImageDTO.builder()
	                    .imageType(imageType)
	                    .isActive(true)
	                    .isDelete(false)
	                    .build();
	            imageDTO = imageService.postImage(imageDTO, dirName, file);

	            Map<String, Object> responseData = objectMapper.convertValue(imageDTO, Map.class);
	            responseData.keySet().retainAll(List.of("id", "imageUrl", "saveDate", "isActive", "isDelete", "deleteDate", "imageType"));
	            
	            responseList.add(responseData);  // 각 이미지의 결과를 리스트에 추가
	        }
	        return ResponseEntity.ok(responseList);  // 전체 리스트를 반환
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}
	
}
