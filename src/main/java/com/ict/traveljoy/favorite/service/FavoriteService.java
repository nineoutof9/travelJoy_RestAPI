package com.ict.traveljoy.favorite.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.favorite.repository.Favorite;
import com.ict.traveljoy.favorite.repository.FavoriteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavoriteService {

	private final FavoriteRepository favoriteRepository;
	private final ObjectMapper objectMapper;
	
	@Transactional(readOnly = true)
	public List<FavoriteDto> favoriteAll() {
		List<Favorite> favoriteEntityList =  favoriteRepository.findAll();
		
		return objectMapper.convertValue(favoriteEntityList, objectMapper.getTypeFactory().defaultInstance().constructCollectionType(List.class,FavoriteDto.class));
	}
	
	//target에 따라 favorite내용 다 가져오기
	@Transactional(readOnly = true)
	public List<FavoriteDto> getFavoriteAllByTarget(String target){
		// target : event / food / sight / hotel
		List<Favorite> favoriteEntityList;
		switch(target) {
			case "event":
				favoriteEntityList = favoriteRepository.findAll();
				break;
//			case "food":
//				favoriteEntityList = favoriteRepository.findByEvent_Id(Long.parseLong(target));
//				break;
//			case "sight":
//				favoriteEntityList = favoriteRepository.findByEvent_Id(Long.parseLong(target));
//				break;
			default:
//				favoriteEntityList = favoriteRepository.findByEvent_Id(Long.parseLong(target));
				favoriteEntityList = favoriteRepository.findAll();
				break;
		}
		
		List<FavoriteDto> favoriteDtos = favoriteEntityList.stream().map(fav->FavoriteDto.toDto(fav)).collect(Collectors.toList());
		return favoriteDtos;
	}
	
	// targetId에 따라 내용 가져오기(url)
	@Transactional(readOnly = true)
	public FavoriteDto getFavoriteByTargetId(String targetId) {
		FavoriteDto.builder()
		.id(1234l)
		.targetId(1212l)
		.isActive(true)
		.isDelete(false)
		.isEvent(false)
		.isFood(true)
		.isSight(false)
		.isHotel(false)
		.build();
		return null;
	}

	
	
	//즐겨찾기 삭제
	public FavoriteDto removebyId(String Id) {
		// TODO Auto-generated method stub
		return null;
	}
}
