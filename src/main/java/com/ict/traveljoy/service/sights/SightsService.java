package com.ict.traveljoy.service.sights;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.traveljoy.repository.sights.Sights;
import com.ict.traveljoy.repository.sights.SightsRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SightsService {
	
	@Autowired
	private SightsRepository sightsRepository;
	
	// 모든 명소 검색
	public List<SightsDTO> findAllSights(){
		return sightsRepository.findAll().stream()
								.map(sight -> SightsDTO.toDto(sight))
								.collect(Collectors.toList());
	}
	
	// ID로 명소 검색
	public Optional<SightsDTO> findSightById(Long id){
		return sightsRepository.findById(id)
								.map(sight -> SightsDTO.toDto(sight));
	}
	
	// 명소 저장
	public SightsDTO saveSight(SightsDTO sightsDto) {
		// 데이터 유효성 검증
		if(sightsDto.getSightName() == null) {
			throw new IllegalArgumentException("관광지 이름이 비어있으면 안돼요");
		}
		
		Sights sight = sightsDto.toEntity();
		sight = sightsRepository.save(sight);
		return SightsDTO.toDto(sight);
	}
	
	// ID로 명소 삭제
	public void deleteSight(Long id) {
		if(sightsRepository.existsById(id)) {
			sightsRepository.deleteById(id);
		} else {
			throw new IllegalArgumentException("주어진 번호의 관광지를 찾을 수 없어요");
		}
	}
	
	// 특정 지역의 명소 검색
	public List<SightsDTO> findSightsByRegionId(Long regionId) {
        return sightsRepository.findByRegion_Id(regionId).stream()
            .map(sight -> SightsDTO.toDto(sight))
            .collect(Collectors.toList());
    }
	
	// 명소 이름으로 검색
    public List<SightsDTO> findSightsByName(String sightName) {
        return sightsRepository.findBySightName(sightName).stream()
                .map(sight -> SightsDTO.toDto(sight))
                .collect(Collectors.toList());
    }

    
    // 특정 리뷰 평점 이상의 명소 검색
    public List<SightsDTO> findSightsByReviewRate(float reviewRate) {
        return sightsRepository.findByAverageReviewRate(reviewRate).stream()
                .map(sight -> SightsDTO.toDto(sight))
                .collect(Collectors.toList());
    }
}
