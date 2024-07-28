package com.ict.traveljoy.service.favorite;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.repository.favorite.Favorite;
import com.ict.traveljoy.repository.favorite.FavoriteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavoriteService {

	private final FavoriteRepository favoriteRepository;
	private final ObjectMapper objectMapper;
	
	public List<FavoriteDTO> favoriteAll() {
		List<Favorite> favoriteEntityList =  favoriteRepository.findAll();
		
		return objectMapper.convertValue(favoriteEntityList, objectMapper.getTypeFactory().defaultInstance().constructCollectionType(List.class,FavoriteDTO.class));
	}
}
