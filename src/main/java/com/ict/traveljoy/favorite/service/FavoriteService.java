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
	public List<FavoriteDTO> favoriteAll() {
		List<Favorite> favoriteEntityList =  favoriteRepository.findAll();
		
		return objectMapper.convertValue(favoriteEntityList, objectMapper.getTypeFactory().defaultInstance().constructCollectionType(List.class,FavoriteDTO.class));
	}
	
	//target에 따라 favorite내용 모두 가져오기
	@Transactional(readOnly = true)
	public List<FavoriteDTO> getFavoriteAllByTarget(String target){
		// target : event / food / sight / hotel
		List<Favorite> favoriteEntityList;
		switch(target) {
			case "event":
				favoriteEntityList = favoriteRepository.findAllByIsEvent(1);
				break;// findAllBy엔터티_필드명();
			case "food":
				favoriteEntityList = favoriteRepository.findAllByIsFood(1);
				break;
			case "sight":
				favoriteEntityList = favoriteRepository.findAllByIsSight(1);
				break;
			default:
				favoriteEntityList = favoriteRepository.findAllByIsHotel(1);
				break;
		}
		
		List<FavoriteDTO> favoriteDtos = favoriteEntityList.stream().map(fav->FavoriteDTO.toDTO(fav)).collect(Collectors.toList());
		return favoriteDtos;
	}
	
	// targetId에 따라 내용 가져오기(url)
	@Transactional(readOnly = true)
	public FavoriteDTO getFavoriteByTargetId(String target,long targetId) {
		Favorite favorite;
		switch(target) {
		case "event":
//			favorite = favoriteRepository.findByEventId(targetId);
			break;// findAllBy엔터티_필드명();
		case "food":
//			favorite = favoriteRepository.findByFoodId(targetId);
			break;
		case "sight":
//			favorite = favoriteRepository.findBySightId(targetId);
			break;
		default:
			//favorite = favoriteRepository.findByHotelId(targetId);
			break;
	}
		
		
		return null;
	}

	
	
	//즐겨찾기 삭제
	public FavoriteDTO removebyId(long id) {
//		if(cRepository.existsById(id)) {
//		//삭제전 삭제할 한줄댓글 조회(반환용)
//		Comments comments = cRepository.findById(id).get();
//		//삭제처리
//		cRepository.deleteById(id);
//		return CommentsDTO.toDTO(comments);
//	}
//	else throw new IllegalArgumentException("해당하는 댓글 아이디가 없어요: "+id);
		if(favoriteRepository.existsById(id)) {
			Favorite favorite = favoriteRepository.findById(id).get();
			favoriteRepository.deleteById(id);
			return FavoriteDTO.toDTO(favorite);
		}
		else throw new IllegalArgumentException("오류");
	}

	public FavoriteDTO removeAll() {
		favoriteRepository.deleteAll();
		return null;
	}
}
