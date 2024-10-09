package com.ict.traveljoy.favorite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    // 모든 즐겨찾기 조회 (삭제된 항목 제외)
    List<Favorite> findAllByUser_IdAndIsDelete(Long userId, Integer isDelete);

    // 북마크한 모든 행사 조회 (삭제된 항목 제외)
    List<Favorite> findAllByUser_IdAndIsEventAndIsActiveAndIsDelete(Long userId, Integer isEvent, Integer isActive, Integer isDelete);

    // 북마크한 모든 식당 조회 (삭제된 항목 제외)
    List<Favorite> findAllByUser_IdAndIsFoodAndIsActiveAndIsDelete(Long userId, Integer isFood, Integer isActive, Integer isDelete);

    // 북마크한 모든 관광지 조회 (삭제된 항목 제외)
    List<Favorite> findAllByUser_IdAndIsSightAndIsActiveAndIsDelete(Long userId, Integer isSight, Integer isActive, Integer isDelete);

    // 북마크한 모든 숙소 조회 (삭제된 항목 제외)
    List<Favorite> findAllByUser_IdAndIsHotelAndIsActiveAndIsDelete(Long userId, Integer isHotel, Integer isActive, Integer isDelete);

}