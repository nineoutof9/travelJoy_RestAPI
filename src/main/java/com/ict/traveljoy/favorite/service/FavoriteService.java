package com.ict.traveljoy.favorite.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ict.traveljoy.favorite.repository.Favorite;
import com.ict.traveljoy.favorite.repository.FavoriteRepository;
import com.ict.traveljoy.place.food.repository.Food;
import com.ict.traveljoy.place.food.repository.FoodRepository;
import com.ict.traveljoy.place.hotel.repository.Hotel;
import com.ict.traveljoy.place.hotel.repository.HotelRepository;
import com.ict.traveljoy.place.sight.repository.Sight; // Sight 엔티티 import
import com.ict.traveljoy.place.sight.repository.SightRepository; // SightRepository 추가
import com.ict.traveljoy.users.repository.UserRepository;
import com.ict.traveljoy.users.repository.Users;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final FoodRepository foodRepository;
    private final SightRepository sightRepository; // SightRepository 추가

    public FavoriteDTO addFavorite(String useremail, FavoriteDTO dto, String target) {
        Users user = userRepository.findByEmail(useremail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Favorite newFav = dto.toEntity();
        newFav.setUser(user);
        newFav.setIsActive(1);

        if ("hotel".equalsIgnoreCase(target)) {
            Hotel hotel = hotelRepository.findById(dto.getTargetId())
                    .orElseThrow(() -> new IllegalArgumentException("Hotel not found"));
            newFav.setHotel(hotel);
            newFav.setIsHotel(1);
        } else if ("food".equalsIgnoreCase(target)) {
            Food food = foodRepository.findById(dto.getTargetId())
                    .orElseThrow(() -> new IllegalArgumentException("Food not found"));
            newFav.setFood(food);  
            newFav.setIsFood(1);
        } else if ("sight".equalsIgnoreCase(target)) { // 관광지 처리 추가
            Sight sight = sightRepository.findById(dto.getTargetId())
                    .orElseThrow(() -> new IllegalArgumentException("Sight not found"));
            newFav.setSight(sight);
            newFav.setIsSight(1);
        } else {
            switch (target) {
                case "event":
                    newFav.setIsEvent(1);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid target type: " + target);
            }
        }
        return FavoriteDTO.toDTO(favoriteRepository.save(newFav));
    }

    @Transactional(readOnly = true)
    public List<FavoriteDTO> getFavoriteAll(String useremail) {
        Users user = userRepository.findByEmail(useremail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<Favorite> favoriteEntityList = favoriteRepository.findAllByUser_IdAndIsDelete(user.getId(), 0);

        return favoriteEntityList.stream()
                .map(FavoriteDTO::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FavoriteDTO> getFavoriteAllByTarget(String target, String useremail) {
        Users user = userRepository.findByEmail(useremail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<Favorite> favoriteEntityList;
        switch (target) {
            case "event":
                favoriteEntityList = favoriteRepository.findAllByUser_IdAndIsEventAndIsActiveAndIsDelete(user.getId(), 1, 1, 0);
                break;
            case "food":
                favoriteEntityList = favoriteRepository.findAllByUser_IdAndIsFoodAndIsActiveAndIsDelete(user.getId(), 1, 1, 0);
                break;
            case "sight":
                favoriteEntityList = favoriteRepository.findAllByUser_IdAndIsSightAndIsActiveAndIsDelete(user.getId(), 1, 1, 0);
                break;
            case "hotel":
                favoriteEntityList = favoriteRepository.findAllByUser_IdAndIsHotelAndIsActiveAndIsDelete(user.getId(), 1, 1, 0);
                break;
            default:
                throw new IllegalArgumentException("Invalid target type: " + target);
        }

        return favoriteEntityList.stream()
                .map(FavoriteDTO::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FavoriteDTO getFavoriteByTargetId(String target, long targetId) {
        Favorite favorite = favoriteRepository.findById(targetId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid targetId"));

        if ("hotel".equalsIgnoreCase(target) && favorite.getIsHotel() == 1) {
            Hotel hotel = hotelRepository.findById(favorite.getTargetId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid hotelId"));
            favorite.setHotel(hotel);
        } else if ("food".equalsIgnoreCase(target) && favorite.getIsFood() == 1) {
            Food food = foodRepository.findById(favorite.getTargetId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid foodId"));
            favorite.setFood(food);
        } else if ("sight".equalsIgnoreCase(target) && favorite.getIsSight() == 1) { // 관광지 처리 추가
            Sight sight = sightRepository.findById(favorite.getTargetId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid sightId"));
            favorite.setSight(sight);
        }

        return FavoriteDTO.toDTO(favorite);
    }

    public FavoriteDTO removebyId(long id) {
        if (favoriteRepository.existsById(id)) {
            Favorite favorite = favoriteRepository.findById(id).get();
            favorite.setIsActive(0);
            favorite.setIsDelete(1);
            favoriteRepository.save(favorite);
            return FavoriteDTO.toDTO(favorite);
        } else {
            throw new IllegalArgumentException("오류");
        }
    }

    public FavoriteDTO removeAll() {
        List<Favorite> favoriteEntityList = favoriteRepository.findAll();
        for (Favorite fav : favoriteEntityList) {
            fav.setIsActive(0);
            fav.setIsDelete(1);
        }
        favoriteRepository.saveAll(favoriteEntityList);
        return null;
    }
    
    public FavoriteDTO updateMemo(Long id, String memo, String useremail) {
        Favorite favorite = favoriteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Favorite not found"));
        favorite.setMemo(memo);
        return FavoriteDTO.toDTO(favoriteRepository.save(favorite));
    }
    
    @Transactional(readOnly = true)
    public String getMemo(Long id, String useremail) {
    	Favorite favorite = favoriteRepository.findById(id)
    			.orElseThrow(() -> new IllegalArgumentException("Favorite not found"));
    	return favorite.getMemo();
    }
}
