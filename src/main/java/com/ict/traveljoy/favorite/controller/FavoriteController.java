package com.ict.traveljoy.favorite.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.controller.CheckContainsUseremail;
import com.ict.traveljoy.favorite.service.FavoriteDTO;
import com.ict.traveljoy.favorite.service.FavoriteService;
import com.ict.traveljoy.security.jwt.util.JwtUtility;
import com.ict.traveljoy.users.repository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bookmark")
@RequiredArgsConstructor
@Tag(name="북마크 API", description = "bookmarked(liked) items Controller")
public class FavoriteController {
	
	private final JwtUtility jwtUtil;
	private final FavoriteService favoriteService; //주입받음
	private final ObjectMapper objectMapper;
	private final CheckContainsUseremail checkUser;
	

	//CREATE
	@PostMapping
	@Operation(summary="회원별로, 주제(행사/음식/장소/호텔)별로 JSON형식의 데이터를 받아서 새로운 북마크 추가")
	@Parameter(name="user",description="로그인 상태인경우는 필요없음",required=false)
	@Parameter(name="target",description="타겟 대상(event/food/sight/hotel 중 하나",required=true)
//	@Parameter(name="targetId",description="주제(event/food/sight/hotel)")

  
	public ResponseEntity<FavoriteDTO> addFavorite(@RequestBody FavoriteDTO favoriteDTO,HttpServletRequest request){ //target,targetId받아서 저장하기
		String useremail = checkUser.checkContainsUseremail(request);
		String target = request.getParameter("target");
		
		try {		
			FavoriteDTO createdFavorite = favoriteService.addFavorite(useremail,favoriteDTO,target);
			if(createdFavorite ==null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND); //없음,400
			}
			 return new ResponseEntity<>(createdFavorite, HttpStatus.CREATED); //201
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); //오류, 500
		}

	}
	
	//READ
	@GetMapping("/all")
	public ResponseEntity<List<FavoriteDTO>> getAllFav(HttpServletRequest request){
		
		String useremail = checkUser.checkContainsUseremail(request);
		
		try {
			List<FavoriteDTO> favorietEventList = favoriteService.getFavoriteAll(useremail); //userid=1인경우
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(favorietEventList);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		
	}
	
	//get 특정 타겟 즐겨찾기들만
	@Parameter(name="target",description="타겟 대상(event/food/sight/hotel 중 하나",required=true)
	@GetMapping("/target")
	public ResponseEntity<List<FavoriteDTO>> getAllFavTargets(HttpServletRequest request){
		
		String useremail = checkUser.checkContainsUseremail(request);
		String target = request.getParameter("target");
		
		try {
			List<FavoriteDTO> favorietEventList = favoriteService.getFavoriteAllByTarget(target,useremail); //userid=1인경우
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(favorietEventList);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		
	}
	
  // 특정 타겟들만
	@Parameter(name="target",description="타겟 대상(event/food/sight/hotel 중 하나",required=true)
	@GetMapping("/{targetId}")
	public ResponseEntity<FavoriteDTO> getFavTarget(@PathVariable("targetId") String targetId,HttpServletRequest request) {	
		String useremail = checkUser.checkContainsUseremail(request);
		String target = request.getParameter("target");
		
		try {
			FavoriteDTO favorite = favoriteService.getFavoriteByTargetId(target,Long.parseLong(targetId));
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(favorite);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	
	//DELETE
	//즐겨찾기삭제 favorite_id로(target_id아님) 
	@DeleteMapping("/{id}")
	public ResponseEntity<FavoriteDTO> removeOneById(@PathVariable("id") String id,HttpServletRequest request) {
		
		String useremail = checkUser.checkContainsUseremail(request);
		
		try {
			FavoriteDTO favorite = favoriteService.removebyId(Long.parseLong(id));
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(favorite);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	@DeleteMapping("/clear")
	public ResponseEntity<FavoriteDTO> removeAll(HttpServletRequest request) {
		
		String useremail = checkUser.checkContainsUseremail(request);
		
		try {
			FavoriteDTO favorite = favoriteService.removeAll();
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(favorite);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
}
