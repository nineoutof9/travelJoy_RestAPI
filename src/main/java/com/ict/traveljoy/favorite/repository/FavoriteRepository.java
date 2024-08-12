package com.ict.traveljoy.favorite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long>{

	List<Favorite> findByEvent_Id(long targetId);

}
