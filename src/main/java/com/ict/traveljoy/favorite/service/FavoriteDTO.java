package com.ict.traveljoy.favorite.service;

import java.time.LocalDateTime;
import java.util.List;

import com.ict.traveljoy.favorite.repository.Favorite;
import com.ict.traveljoy.place.hotel.repository.Hotel;
import com.ict.traveljoy.place.region.repository.Region;
import com.ict.traveljoy.users.repository.Users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteDTO {

    private Long id;
    private Users user;
    private Long targetId;
    private Boolean isEvent;
    private Boolean isFood;
    private Boolean isSight;
    private Boolean isHotel;
    private Boolean isActive;
    private Boolean isDelete;
    private LocalDateTime createDate;
    private LocalDateTime deleteDate;
    private String hotelName;  // 추가된 필드
    private List<String> hotelImageUrls;  // 추가된 필드
    private String regionName;  // 추가된 필
    private String target;
    private String memo;

    public Favorite toEntity() {
        switch (target.toLowerCase()) {
            case "event":
                isEvent = true;
                break;
            case "food":
                isFood = true;
                break;
            case "sight":
                isSight = true;
                break;
            case "hotel":
                isHotel = true;
                break;
            default:
                throw new IllegalArgumentException("Invalid target type: " + target);
        }

        return Favorite.builder()
                .id(id)
                .user(user)
                .targetId(targetId)
                .isEvent(isEvent == null || !isEvent ? 0 : 1)
                .isFood(isFood == null || !isFood ? 0 : 1)
                .isSight(isSight == null || !isSight ? 0 : 1)
                .isHotel(isHotel == null || !isHotel ? 0 : 1)
                .isActive(isActive == null || !isActive ? 0 : 1)
                .isDelete(isDelete == null || !isDelete ? 0 : 1)
                .createDate(createDate)
                .deleteDate(deleteDate)
                .memo(memo)
                .build();
    }

    public static FavoriteDTO toDTO(Favorite favorite) {
        String target = null;
        if (favorite.getIsEvent() == 1) target = "event";
        else if (favorite.getIsFood() == 1) target = "food";
        else if (favorite.getIsSight() == 1) target = "sight";
        else if (favorite.getIsHotel() == 1) target = "hotel";

        Hotel hotel = favorite.getHotel();
        String hotelName = hotel != null ? hotel.getHotelName() : null;
        List<String> hotelImageUrls = hotel != null ? hotel.getImageUrls() : null;
        String regionName = hotel != null && hotel.getRegion() != null ? hotel.getRegion().getName() : null;

        return FavoriteDTO.builder()
                .id(favorite.getId())
                .user(favorite.getUser())
                .targetId(favorite.getTargetId())
                .isEvent(favorite.getIsEvent() == 1)
                .isFood(favorite.getIsFood() == 1)
                .isSight(favorite.getIsSight() == 1)
                .isHotel(favorite.getIsHotel() == 1)
                .isActive(favorite.getIsActive() == 1)
                .isDelete(favorite.getIsDelete() == 1)
                .createDate(favorite.getCreateDate())
                .deleteDate(favorite.getDeleteDate())
                .target(target)
                .hotelName(hotelName)  // 추가된 필드 설정
                .hotelImageUrls(hotelImageUrls)  // 추가된 필드 설정
                .regionName(regionName)  // 추가된 필드 설정
                .memo(favorite.getMemo())
                .build();
    }
}
