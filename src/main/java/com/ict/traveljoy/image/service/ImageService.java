package com.ict.traveljoy.image.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.image.repository.Image;
import com.ict.traveljoy.image.repository.ImageRepository;
import com.ict.traveljoy.users.repository.Users;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService {

	private final ImageRepository imageRepository;
	private final ObjectMapper objectMapper;
	private final S3Uploader s3Uploader;
	
	public ImageDTO postImage(ImageDTO imageDTO, String dirName, MultipartFile file) throws IOException {
		//디렉토리 이름으로 폴더 만들어서 파일 저장
		String imageUrl = s3Uploader.upload(file, dirName);
		Image image = imageDTO.toEntity();
		image.setImageUrl(imageUrl);
		return ImageDTO.toDTO(imageRepository.save(image));
	}
	
    public ImageDTO findById(Long id) {
    	if(imageRepository.existsById(id)) {
	    	Image image = imageRepository.findById(id).get();
	    	ImageDTO imageDTO = ImageDTO.toDTO(image);
	    	return imageDTO;
    	}
    	else throw new IllegalArgumentException("이미지가 없습니다");
    }
    
    //지우지는 않고 상태만 바꾸기
    public ImageDTO deleteById(Long id) {
    	ImageDTO imageDTO = findById(id);
    	Image image = imageDTO.toBuilder()
    	.isActive(false)
    	.isDelete(true)
    	.deleteDate(LocalDateTime.now())
    	.build().toEntity();
    	imageRepository.save(image);
    	return imageDTO;
    }
    
    //DB랑 클라우드에서도 진짜 지우기
    public ImageDTO realDeleteById(Long id) {
    	ImageDTO imageDTO = findById(id);
    	s3Uploader.deleteFile(imageDTO.getImageUrl());
    	imageRepository.delete(imageDTO.toEntity());
    	return imageDTO;
    }
    
    public List<ImageDTO> findAll(){
    	List<Image> images = imageRepository.findAll();
    	List<ImageDTO> imageDTOs = images.stream().map(ImageDTO::toDTO).collect(Collectors.toList());
    	return imageDTOs;
    }
}
