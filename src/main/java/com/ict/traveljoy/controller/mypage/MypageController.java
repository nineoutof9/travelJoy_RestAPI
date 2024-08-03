package com.ict.traveljoy.controller.mypage;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.traveljoy.place.food.service.FoodDTO;
import com.ict.traveljoy.place.food.service.FoodService;
import com.ict.traveljoy.place.hotel.service.HotelDTO;
import com.ict.traveljoy.place.hotel.service.HotelService;
import com.ict.traveljoy.place.sight.service.SightDTO;
import com.ict.traveljoy.place.sight.service.SightService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Tag(name="Mypage", description = "회원 개인 페이지")
@RestController
@RequestMapping("/users")
@CrossOrigin
@RequiredArgsConstructor
public class MypageController {
	
	private final HotelService hotelService;
	private final FoodService foodService;
	private final SightService sightService;
	
	

//////////////////////////////////숙소
	//숙소 저장
	@PostMapping("/hotel/{hotel}")
	public ResponseEntity<HotelDTO> saveHotel(HotelDTO hotelDTO) {
		
		try {
		HotelDTO saveDTO = hotelService.saveHotel(hotelDTO);
		return ResponseEntity.ok(saveDTO);
		} catch(Exception e) {e.printStackTrace();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
	
	//숙소 조회
	@GetMapping("/hotel/{hotel}")
    public ResponseEntity<List<HotelDTO>> getHotels(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long regionId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Float minPrice,
            @RequestParam(required = false) Float maxPrice,
            @RequestParam(required = false) Long reviewCount,
            @RequestParam(required = false) Float reviewRate) {
        
        try {
            List<HotelDTO> dtos = new ArrayList<>();

            if (id != null) {
                Optional<HotelDTO> dto = hotelService.findHotelById(id);
                dto.ifPresent(dtos::add);
            } else if (regionId != null) {
                dtos = hotelService.findHotelsByRegionId(regionId);
            } else if (name != null) {
                dtos = hotelService.findHotelsByName(name);
            } else if (minPrice != null && maxPrice != null) {
                dtos = hotelService.findHotelsByPriceRange(minPrice, maxPrice);
            } else if (reviewCount != null) {
                dtos = hotelService.findHotelsByReviewCount(reviewCount);
            } else if (reviewRate != null) {
                dtos = hotelService.findHotelsByReviewRate(reviewRate);
            }

            if (dtos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
            }

            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        }
    }

	//숙소 수정
	/*
	@PutMapping("/hotel/{hotel}")
	public ResponseEntity<Optional<HotelDTO>> updateHotel(@PathVariable Long id, @RequestBody HotelDTO dto) {
		
		try {
		Optional<HotelDTO> updateDTO = hotelService.findHotelById(id);
		return ResponseEntity.ok(updateDTO);
		} catch(Exception e) {e.printStackTrace();
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);}
		

	}*/
	
	//숙소 삭제
	@DeleteMapping("/hotel/{hotel}")
	public String deleteHotel(@PathVariable Long id) {
		hotelService.deleteHotel(id);
		return "숙소 삭제 성공";
	}
	
//////////////////////////////////음식
	//음식 저장
	@PostMapping("/food/{food}")
	public ResponseEntity<FoodDTO> saveFood(FoodDTO foodDTO) {

		try {
		FoodDTO saveDTO = foodService.saveFood(foodDTO);
		return ResponseEntity.ok(saveDTO);
		} catch(Exception e) {e.printStackTrace();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
	}
	//음식 조회
	@GetMapping("/food/{food}")
    public ResponseEntity<List<FoodDTO>> getFood(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long regionId,
            @RequestParam(required = false) String foodname,
            @RequestParam(required = false) Float maxPrice,
            @RequestParam(required = false) Float reviewRate) {
        
        try {
            List<FoodDTO> dtos = new ArrayList<>();

            if (id != null) {
                Optional<FoodDTO> dto = foodService.findFoodById(id);
                dto.ifPresent(dtos::add);
            } else if (regionId != null) {
                dtos = foodService.findFoodsByRegionId(regionId);
            } else if (foodname != null) {
                dtos = foodService.findFoodsByName(foodname);
            } else if (maxPrice != null) {
                dtos = foodService.findFoodsByPrice(maxPrice);
            } else if (reviewRate != null) {
                dtos = foodService.findFoodsByReviewRate(reviewRate);
            }

            if (dtos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
            }

            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        }
    }
	/*
	@PutMapping("/food/{food}")
	public String updateFood() {
		return "음식 수정";
	}*/
	//음식 삭제
	@DeleteMapping("/food/{food}")
	public String deleteFood(@PathVariable Long id) {
		foodService.deleteFood(id);
		return "음식 삭제 성공";
	}
	
//////////////////////////////////명소
	//명소 저장
	@PostMapping("/sight/{sight}")
	public ResponseEntity<SightDTO> saveSight(SightDTO sightDTO) {
	
		try {
		SightDTO saveDTO = sightService.saveSight(sightDTO);
		return ResponseEntity.ok(saveDTO);
		} catch(Exception e) {e.printStackTrace();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
	@GetMapping("/sight/{sight}")
    public ResponseEntity<List<SightDTO>> getSight(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long regionId,
            @RequestParam(required = false) String sightname,
            @RequestParam(required = false) Float reviewRate) {
        
        try {
            List<SightDTO> dtos = new ArrayList<>();

            if (id != null) {
                Optional<SightDTO> dto = sightService.findSightById(id);
                dto.ifPresent(dtos::add);
            } else if (regionId != null) {
                dtos = sightService.findSightsByRegionId(regionId);
            } else if (sightname != null) {
                dtos = sightService.findSightsByName(sightname);
            } else if (reviewRate != null) {
                dtos = sightService.findSightsByReviewRate(reviewRate);
            }

            if (dtos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
            }

            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        }
    }
	/*
	@PutMapping("/sight/{sight}")
	public String updateSight() {
		return "명소 저장";
	}*/
	
	@DeleteMapping("/sight/{sight}")
	public String deleteSight(@PathVariable Long id) {
		sightService.deleteSight(id);
		return "명소 삭제 성공";
	}
	

	@GetMapping("/getBookmark")
	public String getBookmark() {

		return "즐겨찾기한 관광지, 숙소, 맛집 등 장소 불러오기";
	}
	
	@GetMapping("/setReview")
	public String setReview() {
		return "작성한 후기 수정 / 저장 / 삭제";
	}
	
	@GetMapping("/getReview")
	public String getReview() {
		return "작성한 후기 불러오기";
	}
	
	@GetMapping("/setSchedule")
	public String setSchedule() {
		return "일정 저장 / 삭제 / 수정";
	}
	
	@GetMapping("/getSchedule")
	public String getSchedule() {
		return "일정 불러오기";
	}
	
	
	

}
