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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.favorite.service.FavoriteDto;
import com.ict.traveljoy.favorite.service.FavoriteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/bookmark")
@RequiredArgsConstructor
public class FavoriteController {
	
	private final FavoriteService favoriteService; //주입받음
	private final ObjectMapper objectMapper;
	
	//CRUD
	//event
	@GetMapping("/event")
	public ResponseEntity<List<FavoriteDto>> getAllFavEvents(){
		
		try {
			List<FavoriteDto> favorietEventList = favoriteService.getFavoriteAllByTarget("event");
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(favorietEventList);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		
	}
	
	@GetMapping("/event/{targetId}")
	public ResponseEntity<FavoriteDto> getFavEvent(@PathVariable String targetId) {
		try {
			FavoriteDto favorite = favoriteService.getFavoriteByTargetId(targetId);
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(favorite);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	//food
	@GetMapping("/food")
	public ResponseEntity<List<FavoriteDto>> getAllFavFoods(){
		try {
			List<FavoriteDto> favorietEventList = favoriteService.getFavoriteAllByTarget("food");
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(favorietEventList);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		
	}
	
	@GetMapping("/food/{targetId}")
	public ResponseEntity<FavoriteDto> getFavFood(@PathVariable String targetId) {
		try {
			FavoriteDto favorite = favoriteService.getFavoriteByTargetId(targetId);
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(favorite);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	//sight
	@GetMapping("/sight")
	public ResponseEntity<List<FavoriteDto>> getAllFavSights(){
		try {
			List<FavoriteDto> favorietEventList = favoriteService.getFavoriteAllByTarget("sight");
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(favorietEventList);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		
	}
	
	@GetMapping("/sight/{targetId}")
	public ResponseEntity<FavoriteDto> getFavSight(@PathVariable String targetId) {
		try {
			FavoriteDto favorite = favoriteService.getFavoriteByTargetId(targetId);
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(favorite);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	//hotel
	@GetMapping("/hotel")
	public ResponseEntity<List<FavoriteDto>> getAllFavHotels(){
		try {
			List<FavoriteDto> favorietEventList = favoriteService.getFavoriteAllByTarget("hotel");
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(favorietEventList);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		
	}
	
	@GetMapping("/hotel/{targetId}")
	public ResponseEntity<FavoriteDto> getFavHotel(@PathVariable String targetId) {
		try {
			FavoriteDto favorite = favoriteService.getFavoriteByTargetId(targetId);
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(favorite);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	
	//즐겨찾기삭제 id로 
	@DeleteMapping("/{id}")
	public ResponseEntity<FavoriteDto> removeFav(@PathVariable String id) {
		try {
			FavoriteDto favorite = favoriteService.removebyId(id);
			return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(favorite);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
}
