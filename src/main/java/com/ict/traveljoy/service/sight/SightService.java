package com.ict.traveljoy.service.sight;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.traveljoy.repository.sight.Sight;
import com.ict.traveljoy.repository.sight.SightRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SightService {

    @Autowired
    private SightRepository sightRepository;

    // 모든 명소 검색
    public List<SightDTO> findAllSights() {
        return sightRepository.findAll().stream()
                .map(sight -> SightDTO.toDto(sight))
                .collect(Collectors.toList());
    }

    // ID로 명소 검색
    public Optional<SightDTO> findSightById(Long id) {
        return sightRepository.findById(id)
                .map(sight -> SightDTO.toDto(sight));
    }

    // 명소 저장
    public SightDTO saveSight(SightDTO sightDto) {
        // 데이터 유효성 검증
        if (sightDto.getSightName() == null || sightDto.getSightName().isEmpty()) {
            throw new IllegalArgumentException("관광지 이름이 비어있으면 안돼요");
        }
        // 다른 필수 필드 검증 추가 가능

        Sight sight = sightDto.toEntity();
        sight = sightRepository.save(sight);
        return SightDTO.toDto(sight);
    }

    // ID로 명소 삭제
    public void deleteSight(Long id) {
        if (sightRepository.existsById(id)) {
            sightRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("주어진 번호의 관광지를 찾을 수 없어요");
        }
    }

    // 특정 지역의 명소 검색
    public List<SightDTO> findSightsByRegionId(Long regionId) {
        return sightRepository.findByRegion_Id(regionId).stream()
                .map(sight -> SightDTO.toDto(sight))
                .collect(Collectors.toList());
    }

    // 명소 이름으로 검색
    public List<SightDTO> findSightsByName(String sightName) {
        return sightRepository.findBySightName(sightName).stream()
                .map(sight -> SightDTO.toDto(sight))
                .collect(Collectors.toList());
    }

    // 특정 리뷰 평점 이상의 명소 검색
    public List<SightDTO> findSightsByReviewRate(float reviewRate) {
        return sightRepository.findByAverageReviewRateGreaterThanEqual(reviewRate).stream()
                .map(sight -> SightDTO.toDto(sight))
                .collect(Collectors.toList());
    }
}
