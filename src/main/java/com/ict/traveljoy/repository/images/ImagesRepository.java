package com.ict.traveljoy.repository.images;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImagesRepository extends JpaRepository<Images, Long> {

    // 이미지 ID로 조회
    Optional<Images> findByImageId(Long imageId);

    // 이미지 URL로 조회
    Optional<Images> findByImageUrl(String imageUrl);

    // URL에 특정 단어가 포함된 이미지 조회
    List<Images> findByImageUrlContaining(String keyword);
}
