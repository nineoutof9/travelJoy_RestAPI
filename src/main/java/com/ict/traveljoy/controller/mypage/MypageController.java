package com.ict.traveljoy.controller.mypage;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.traveljoy.place.event.service.EventDTO;
import com.ict.traveljoy.place.event.service.EventService;
import com.ict.traveljoy.place.food.service.FoodDTO;
import com.ict.traveljoy.place.food.service.FoodService;
import com.ict.traveljoy.place.hotel.service.HotelDTO;
import com.ict.traveljoy.place.hotel.service.HotelService;
import com.ict.traveljoy.place.placeInterest.service.PlaceInterestDTO;
import com.ict.traveljoy.place.placeInterest.service.PlaceInterestService;
import com.ict.traveljoy.place.region.service.RegionDTO;
import com.ict.traveljoy.place.region.service.RegionService;
import com.ict.traveljoy.place.regionWeather.service.RegionWeatherDTO;
import com.ict.traveljoy.place.regionWeather.service.RegionWeatherService;
import com.ict.traveljoy.place.sight.service.SightDTO;
import com.ict.traveljoy.place.sight.service.SightService;
import com.ict.traveljoy.place.transportation.service.TransportationDTO;
import com.ict.traveljoy.place.transportation.service.TransportationService;
import com.ict.traveljoy.tripReview.repository.TripReview;
import com.ict.traveljoy.tripReview.service.TripReviewDto;
import com.ict.traveljoy.tripReview.service.TripReviewPhotoDTO;
import com.ict.traveljoy.tripReview.service.TripReviewPhotoService;
import com.ict.traveljoy.tripReview.service.TripReviewService;
import com.ict.traveljoy.weather.service.WeatherDTO;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@Tag(name="Mypage", description = "회원 개인 페이지")
@RestController
@RequestMapping("/api/users")
@CrossOrigin
@RequiredArgsConstructor
public class MypageController {
   
   @Autowired
   private final HotelService hotelService;
   @Autowired
   private final FoodService foodService;
   @Autowired
   private final SightService sightService;
   @Autowired
   private final TripReviewService tripReviewService;
   @Autowired
   private final TripReviewPhotoService tripReviewPhotoService;
   @Autowired
   private EventService eventService;
   @Autowired
   private TransportationService transportationService;
   @Autowired
   private PlaceInterestService placeInterestService;
   @Autowired
   private RegionService regionService;
   @Autowired
   private RegionWeatherService regionWeatherService;
   

//////////////////////////////////숙소
   //숙소 저장
   
   
   @PostMapping("/hotels")
   public ResponseEntity<HotelDTO> saveHotel(HotelDTO hotelDTO) {
      
	      try {
	      HotelDTO saveDTO = hotelService.saveHotel(hotelDTO);
	      return ResponseEntity.ok(saveDTO);
	      } catch(Exception e) {e.printStackTrace();
	      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	      }
   }
   
   //숙소 조회
   @GetMapping("/hotel/{id}")
    public ResponseEntity<List<HotelDTO>> getHotels(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long region_Id,
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
            } else if (region_Id != null) {
                dtos = hotelService.findHotelsByRegionId(region_Id);
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
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ArrayList<>());
            }

            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        }
    }

   //숙소 수정

   @PutMapping("/hotel/{id}")
   public ResponseEntity<HotelDTO> updateHotel(@PathVariable Long id, @RequestBody HotelDTO hotelDTO) {
       try {
    	   HotelDTO updatedHotel = hotelService.updateHotel(id, hotelDTO);
           return new ResponseEntity<>(updatedHotel, HttpStatus.OK);
       } catch (IllegalArgumentException e) {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       } catch (Exception e) {
           e.printStackTrace();
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }
   
   //숙소 삭제
   @DeleteMapping("/hotel/{id}")
   public String deleteHotel(@PathVariable Long id) {
      hotelService.deleteHotel(id);
      return "숙소 삭제 성공";
   }
   
//////////////////////////////////음식
   //음식 저장
   @PostMapping("/food/{id}")
   public ResponseEntity<FoodDTO> saveFood(FoodDTO foodDTO) {

      try {
      FoodDTO saveDTO = foodService.saveFood(foodDTO);
      return ResponseEntity.ok(saveDTO);
      } catch(Exception e) {e.printStackTrace();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
      }
      
   }
   //음식 조회
   @GetMapping("/food/{id}")
    public ResponseEntity<List<FoodDTO>> getFoods(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long region_Id,
            @RequestParam(required = false) String foodname,
            @RequestParam(required = false) Float maxPrice,
            @RequestParam(required = false) Float reviewRate) {
        
        try {
            List<FoodDTO> dtos = new ArrayList<>();

            if (id != null) {
                Optional<FoodDTO> dto = foodService.findFoodById(id);
                dto.ifPresent(dtos::add);
            } else if (region_Id != null) {
                dtos = foodService.findFoodsByRegionId(region_Id);
            } else if (foodname != null) {
                dtos = foodService.findFoodsByName(foodname);
            } else if (maxPrice != null) {
                dtos = foodService.findFoodsByPrice(maxPrice);
            } else if (reviewRate != null) {
                dtos = foodService.findFoodsByReviewRate(reviewRate);
            }

            if (dtos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ArrayList<>());
            }

            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        }
    }
   //음식 수정

   @PutMapping("/food/{id}")
   public ResponseEntity<FoodDTO> updateFood(@PathVariable Long id, @RequestBody FoodDTO foodDTO) {
       try {
    	   FoodDTO updatedFood = foodService.updateFood(id, foodDTO);
           return new ResponseEntity<>(updatedFood, HttpStatus.OK);
       } catch (IllegalArgumentException e) {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       } catch (Exception e) {
           e.printStackTrace();
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }
   //음식 삭제
   @DeleteMapping("/food/{id}")
   public String deleteFood(@PathVariable Long id) {
      foodService.deleteFood(id);
      return "음식 삭제 성공";
   }
   
//////////////////////////////////명소
   //명소 저장
   @PostMapping("/sight/{id}")
   public ResponseEntity<SightDTO> saveSight(SightDTO sightDTO) {
   
      try {
      SightDTO saveDTO = sightService.saveSight(sightDTO);
      return ResponseEntity.ok(saveDTO);
      } catch(Exception e) {e.printStackTrace();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
      }
   }
   
   @GetMapping("/sight/{id}")
    public ResponseEntity<List<SightDTO>> getSights(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long region_Id,
            @RequestParam(required = false) String sightname,
            @RequestParam(required = false) Float reviewRate) {
        
        try {
            List<SightDTO> dtos = new ArrayList<>();

            if (id != null) {
                Optional<SightDTO> dto = sightService.findSightById(id);
                dto.ifPresent(dtos::add);
            } else if (region_Id != null) {
                dtos = sightService.findSightsByRegionId(region_Id);
            } else if (sightname != null) {
                dtos = sightService.findSightsByName(sightname);
            } else if (reviewRate != null) {
                dtos = sightService.findSightsByReviewRate(reviewRate);
            }

            if (dtos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ArrayList<>());
            }

            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        }
    }
   
   @PutMapping("/sight/{id}")
   public ResponseEntity<SightDTO> updateSight(@PathVariable Long id, @RequestBody SightDTO sightDTO) {
       try {
    	   SightDTO updatedSight = sightService.updateSight(id, sightDTO);
           return new ResponseEntity<>(updatedSight, HttpStatus.OK);
       } catch (IllegalArgumentException e) {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       } catch (Exception e) {
           e.printStackTrace();
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }

   
   @DeleteMapping("/sight/{id}")
   public String deleteSight(@PathVariable Long id) {
      sightService.deleteSight(id);
      return "명소 삭제 성공";
   }
   
/*
   ///////여행 리뷰    
   @PostMapping("/trip-reviews")
   public ResponseEntity<TripReviewDto> saveTripReview(@RequestBody TripReviewDto tripReviewDto) {
      try {
         TripReviewDto saveDTO = tripReviewService.createReview(tripReviewDto);
         return ResponseEntity.ok(saveDTO);
      }catch(Exception e) {e.printStackTrace();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
      }
}
   
   @PostMapping("/trip-reviews/photo")
   public ResponseEntity<TripReviewPhotoDto> saveTripReviewPhoto(@RequestBody TripReviewPhotoDto tripReviewPhotoDTO) {
      
      try {
         TripReviewPhotoDTO saveDTO = tripReviewPhotoService.savePhoto(tripReviewPhotoDTO);
         return ResponseEntity.ok(saveDTO);
      }catch(Exception e) {e.printStackTrace();
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
      }
}
   
   @GetMapping("/trip-reviews")
   public ResponseEntity<List<TripReviewDto>> getTripReview(
           @RequestParam(required = false) Long tripReviewId,
           @RequestParam(required = false) String writer,
           @RequestParam(required = false) Long planId,
           @RequestParam(required = false) String title) {
      try {
           if (tripReviewId != null) {
               TripReviewDto dto = tripReviewService.getReviewById(tripReviewId);
               return ResponseEntity.ok(List.of(dto));
           } else if (writer != null) {
               return ResponseEntity.ok(tripReviewService.getReviewsByWriter(writer));
           } else if (planId != null) {
               return ResponseEntity.ok(tripReviewService.getReviewsByPlanId(planId));
           } else if (title != null) {
               return ResponseEntity.ok(tripReviewService.getReviewsByTitleContaining(title));
           } else {
               return ResponseEntity.ok(tripReviewService.getAllReviews());
           }
       } catch (Exception e) {
           e.printStackTrace();
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
       }
   }

   
   @GetMapping("/trip-reviews/photo")
   public ResponseEntity<List<TripReviewPhotoDto>> getTripReviewPhoto(
           @RequestParam(required = false) Long tripReviewId,
           @RequestParam(required = false) Long imageId,
           @RequestParam(required = false) Long tripReviewPhotoId) {
      try {
           if (tripReviewId != null && imageId != null) {
              TripReviewPhotoDto dto = tripReviewPhotoService.getPhotoByTripReviewIdAndImageId(tripReviewId, imageId);
               return ResponseEntity.ok(List.of(dto));
           } else if (tripReviewPhotoId != null) {
               return ResponseEntity.ok(List.of(tripReviewPhotoService.getPhotoById(tripReviewPhotoId)));
           } else if (imageId != null) {
               return ResponseEntity.ok(tripReviewPhotoService.getPhotosByImageId(imageId));
           } else if (tripReviewId != null) {
               return ResponseEntity.ok(tripReviewPhotoService.getPhotosByReviewId(tripReviewId));
           } else {
               return ResponseEntity.ok(new ArrayList<>());
           }
       } catch (Exception e) {
           e.printStackTrace();
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
       }
   }
   
   
   @PutMapping("/trip-reviews/{id}")
   public ResponseEntity<TripReviewDto> updateTripReview(@PathVariable Long tripReviewId,
           @RequestBody TripReviewDto tripReviewDto) {
       try {
           TripReviewDto updatedDTO = tripReviewService.updateReview(tripReviewId, tripReviewDto);
           return ResponseEntity.ok(updatedDTO);
       }catch(Exception e) {e.printStackTrace();
      
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);}
   }

   
   @PutMapping("/trip-reviews/photo/{id}")
   public ResponseEntity<TripReviewPhotoDto> updateTripReviewPhoto(@PathVariable Long tripReviewPhoto_Id,
           @RequestBody TripReviewPhotoDto tripReviewPhotoDTO) {
       try {
           TripReviewPhotoDto updatedDTO = tripReviewPhotoService.updatePhoto(tripReviewPhoto_Id, tripReviewPhotoDTO);
           return ResponseEntity.ok(updatedDTO);
       }catch(Exception e) {e.printStackTrace();
      
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);//}

   }
   
   
   @DeleteMapping("/trip-reviews/{id}")
   public ResponseEntity<String> deleteTripReview(@PathVariable Long tripReviewId) {
       try {
           tripReviewService.deleteReview(tripReviewId);
           return ResponseEntity.ok("여행 리뷰 삭제 성공");
       }catch(Exception e) {e.printStackTrace();
      
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);}

   }
   
   @DeleteMapping("/trip-reviews/photo/{id}")
   public ResponseEntity<String> deleteTripReviewAndPhoto(@PathVariable Long tripReviewPhotoId) {
       try {
           tripReviewPhotoService.deletePhoto(tripReviewPhotoId);
           return ResponseEntity.ok("여행 리뷰 사진 삭제 성공");
       }catch(Exception e) {e.printStackTrace();
      
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);}

   }*/
   
/////이벤트
   //이벤트 저장
   @PostMapping("/events")
   public ResponseEntity<EventDTO> saveEvent(@RequestBody EventDTO eventDto) {
       try {
           EventDTO savedEvent = eventService.saveEvent(eventDto);
           return new ResponseEntity<>(savedEvent, HttpStatus.CREATED);
       } catch (Exception e) {
           e.printStackTrace();
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }
   
   //이벤트 조회
   
   @GetMapping("/events/{id}")
   public ResponseEntity<List<EventDTO>> getEvents(
           @RequestParam(required = false) Long id,
           @RequestParam(required = false) Long region_Id,
           @RequestParam(required = false) String eventName,
           @RequestParam(required = false) Float reviewRate) {
       
       try {
           List<EventDTO> dtos = new ArrayList<>();

           if (id != null) {
               Optional<EventDTO> dto = eventService.findEventById(id);
               dto.ifPresent(dtos::add);
           } else if (region_Id != null) {
               dtos = eventService.findEventsByRegionId(region_Id);
           } else if (eventName != null) {
               dtos = eventService.findEventsByName(eventName);
           } else if (reviewRate != null) {
               dtos = eventService.findEventsByReviewRate(reviewRate);
           }

           if (dtos.isEmpty()) {
               return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ArrayList<>());
           }

           return ResponseEntity.ok(dtos);
       } catch (Exception e) {
           e.printStackTrace();
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
       }
   }
   
   //이벤트 수정
   @PutMapping("/events/{id}")
   public ResponseEntity<EventDTO> updateEvent(@PathVariable Long id, @RequestBody EventDTO eventDto) {
       try {
           EventDTO updatedEvent = eventService.updateEvent(id, eventDto);
           return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
       } catch (IllegalArgumentException e) {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       } catch (Exception e) {
           e.printStackTrace();
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }
   
   //이벤트 삭제
   @DeleteMapping("/events/{id}")
   public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
       try {
           eventService.deleteEvent(id);
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       } catch (IllegalArgumentException e) {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       } catch (Exception e) {
           e.printStackTrace();
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }

   //교통수단 저장
   @PostMapping("/transportations")
   public ResponseEntity<TransportationDTO> saveTransportation(@RequestBody TransportationDTO transportationDTO) {
       try {
           TransportationDTO savedTransportation = transportationService.saveTransportation(transportationDTO);
           return new ResponseEntity<>(savedTransportation, HttpStatus.CREATED);
       } catch (Exception e) {
           e.printStackTrace();
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }
   //교통수단 수정
   @PutMapping("/transportations/{id}")
   public ResponseEntity<TransportationDTO> updateTransportation(@PathVariable Long id, @RequestBody TransportationDTO transportationDTO) {
       try {
           TransportationDTO updatedTransportation = transportationService.updateTransportation(id, transportationDTO);
           return new ResponseEntity<>(updatedTransportation, HttpStatus.OK);
       } catch (IllegalArgumentException e) {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       } catch (Exception e) {
           e.printStackTrace();
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }
   
   //교통수단 삭제
   @DeleteMapping("/transportations/{id}")
   public ResponseEntity<Void> deleteTransportation(@PathVariable Long id) {
       try {
           transportationService.deleteTransportation(id);
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       } catch (IllegalArgumentException e) {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       } catch (Exception e) {
           e.printStackTrace();
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }
   //교통수단 조회
   @GetMapping("/transportations/{id}")
   public ResponseEntity<List<TransportationDTO>> getTransportations(
           @RequestParam(required = false) Long id,
           @RequestParam(required = false) boolean isBus,
           @RequestParam(required = false) boolean isTrain,
           @RequestParam(required = false) boolean isAirplane,
           @RequestParam(required = false) boolean isDrive,
           @RequestParam(required = false) LocalDateTime startDate,
           @RequestParam(required = false) LocalDateTime endDate,
           @RequestParam(required = false) Float minPrice,
           @RequestParam(required = false) Float maxPrice) {
       
       try {
           List<TransportationDTO> dtos = new ArrayList<>();

           if (id != null) {
               Optional<TransportationDTO> dto = transportationService.findTransportationById(id);
               dto.ifPresent(dtos::add);
           } else if (isBus != false) {
               dtos = transportationService.findTransportationsByIsBus(isBus);
           } else if (isTrain != false) {
               dtos = transportationService.findTransportationsByIsTrain(isTrain);
           } else if (isAirplane != false) {
               dtos = transportationService.findTransportationsByIsAirplane(isAirplane);
           } else if (isDrive != false) {
               dtos = transportationService.findTransportationsByIsDrive(isDrive);
           } else if (startDate != null && endDate != null) {
               dtos = transportationService.findTransportationsByDateRange(startDate, endDate);
           } else if (minPrice != null && maxPrice != null) {
               dtos = transportationService.findTransportationsByPrice(minPrice, maxPrice);
           }

           if (dtos.isEmpty()) {
               return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ArrayList<>());
           }

           return ResponseEntity.ok(dtos);
       } catch (Exception e) {
           e.printStackTrace();
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
       }
   }
   
///place Interest
   
   //저장
   @PostMapping("/placeInterests")
   public ResponseEntity<PlaceInterestDTO> savePlaceInterest(@RequestBody PlaceInterestDTO placeInterestDTO) {
       try {
           PlaceInterestDTO savedPlaceInterest = placeInterestService.savePlaceInterest(placeInterestDTO);
           return new ResponseEntity<>(savedPlaceInterest, HttpStatus.CREATED);
       } catch (Exception e) {
           e.printStackTrace();
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }
   //수정
   @PutMapping("/placeInterests/{id}")
   public ResponseEntity<PlaceInterestDTO> updatePlaceInterest(@PathVariable Long id, @RequestBody PlaceInterestDTO placeInterestDTO) {
       try {
           PlaceInterestDTO updatedPlaceInterest = placeInterestService.savePlaceInterest(placeInterestDTO);
           return new ResponseEntity<>(updatedPlaceInterest, HttpStatus.OK);
       } catch (IllegalArgumentException e) {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       } catch (Exception e) {
           e.printStackTrace();
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }
   //삭제
   @DeleteMapping("/placeInterests/{id}")
   public ResponseEntity<Void> deletePlaceInterest(@PathVariable Long id) {
       try {
           placeInterestService.deletePlaceInterestById(id);
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       } catch (IllegalArgumentException e) {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       } catch (Exception e) {
           e.printStackTrace();
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }

   //조회
   @GetMapping("/placeInterests/{id}")
   public ResponseEntity<PlaceInterestDTO> getPlaceInterestById(@PathVariable Long id) {
       try {
           return placeInterestService.getPlaceInterestById(id)
                   .map(placeInterest -> new ResponseEntity<>(placeInterest, HttpStatus.OK))
                   .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
       } catch (Exception e) {
           e.printStackTrace();
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }
   
   //모든 placeInterests 조회
   @GetMapping("/placeInterests")
   public ResponseEntity<List<PlaceInterestDTO>> getAllPlaceInterests() {
       try {
           List<PlaceInterestDTO> placeInterests = placeInterestService.getAllPlaceInterests();
           return new ResponseEntity<>(placeInterests, HttpStatus.OK);
       } catch (Exception e) {
           e.printStackTrace();
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }
   ////지역

   //지역 저장
   @PostMapping("/regions")
   public ResponseEntity<RegionDTO> saveRegion(@RequestBody RegionDTO regionDTO) {
       try {
           RegionDTO savedRegion = regionService.saveRegion(regionDTO);
           return new ResponseEntity<>(savedRegion, HttpStatus.CREATED);
       } catch (Exception e) {
           e.printStackTrace();
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }
   //지역 수정
   @PutMapping("/regions/{id}")
   public ResponseEntity<RegionDTO> updateRegion(@PathVariable Long id, @RequestBody RegionDTO regionDto) {
       try {
           RegionDTO updatedRegion = regionService.updateRegion(id, regionDto);
           return new ResponseEntity<>(updatedRegion, HttpStatus.OK);
       } catch (IllegalArgumentException e) {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       } catch (Exception e) {
           e.printStackTrace();
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }
   //지역 삭제
   @DeleteMapping("/regions/{id}")
   public ResponseEntity<Void> deleteRegion(@PathVariable Long id) {
       try {
           regionService.deleteRegion(id);
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       } catch (IllegalArgumentException e) {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       } catch (Exception e) {
           e.printStackTrace();
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }
   //지역 조회(id)
   @GetMapping("/regions/{id}")
   public ResponseEntity<RegionDTO> getRegionById(@PathVariable Long id) {
       try {
           return regionService.getRegionById(id)
                   .map(region -> new ResponseEntity<>(region, HttpStatus.OK))
                   .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
       } catch (Exception e) {
           e.printStackTrace();
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }
   //모든 지역 조회
   @GetMapping("/regions")
   public ResponseEntity<List<RegionDTO>> getAllRegions() {
       try {
           List<RegionDTO> regions = regionService.getAllRegion();
           return new ResponseEntity<>(regions, HttpStatus.OK);
       } catch (Exception e) {
           e.printStackTrace();
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }
   
   //////지역 날씨
   //지역 날씨 저장
   @PostMapping("/regionWeathers")
   public ResponseEntity<RegionWeatherDTO> saveRegionWeather(@RequestBody RegionWeatherDTO regionWeatherDTO) {
       try {
           RegionWeatherDTO savedRegionWeather = regionWeatherService.saveRegionWeather(regionWeatherDTO);
           return new ResponseEntity<>(savedRegionWeather, HttpStatus.CREATED);
       } catch (Exception e) {
           e.printStackTrace();
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }
   //지역 날씨 수정
   @PutMapping("/regionWeathers/{id}")
   public ResponseEntity<RegionWeatherDTO> updateRegionWeather(@PathVariable Long id, @RequestBody RegionWeatherDTO regionWeatherDto) {
       try {
           RegionWeatherDTO updatedRegionWeather = regionWeatherService.saveRegionWeather(regionWeatherDto);
           return new ResponseEntity<>(updatedRegionWeather, HttpStatus.OK);
       } catch (IllegalArgumentException e) {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       } catch (Exception e) {
           e.printStackTrace();
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }
   //지역 날씨 삭제
   @DeleteMapping("/regionWeathers/{id}")
   public ResponseEntity<Void> deleteRegionWeather(@PathVariable Long id) {
       try {
           regionWeatherService.deleteRegionWeather(id);
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       } catch (IllegalArgumentException e) {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       } catch (Exception e) {
           e.printStackTrace();
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }
   //모든 지역 날씨 조회
   @GetMapping("/regionWeathers")
   public ResponseEntity<List<RegionWeatherDTO>> getAllRegionWeathers() {
       try {
           List<RegionWeatherDTO> regionWeathers = regionWeatherService.findAllRegionWeathers();
           return new ResponseEntity<>(regionWeathers, HttpStatus.OK);
       } catch (Exception e) {
           e.printStackTrace();
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }
   //지역 날씨 조회(id)
   @GetMapping("/regionWeathers/{id}")
   public ResponseEntity<RegionWeatherDTO> getRegionWeatherById(@PathVariable Long id) {
       try {
           return regionWeatherService.findRegionWeatherById(id)
                   .map(regionWeather -> new ResponseEntity<>(regionWeather, HttpStatus.OK))
                   .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
       } catch (Exception e) {
           e.printStackTrace();
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }
   
   //plan
   
   //planProgress
   
   //plan
}///마페컨
