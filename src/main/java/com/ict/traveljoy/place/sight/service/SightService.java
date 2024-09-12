package com.ict.traveljoy.place.sight.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.traveljoy.place.region.repository.Region;
import com.ict.traveljoy.place.region.repository.RegionRepository;
import com.ict.traveljoy.place.sight.repository.Sight;
import com.ict.traveljoy.place.sight.repository.SightRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SightService {

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private SightRepository sightRepository;

    // 모든 명소 검색
    public List<SightDTO> findAllSights() {
        return sightRepository.findAll().stream()
                .map(SightDTO::toDto)
                .collect(Collectors.toList());
    }

    // ID로 명소 검색
    public Optional<SightDTO> findSightById(Long id) {
        return sightRepository.findById(id)
                .map(SightDTO::toDto);
    }

    // 명소 저장
    @Transactional
    public SightDTO saveSight(SightDTO sightDto) {

        if (sightDto.getSightName() == null || sightDto.getSightName().isEmpty()) {
            throw new IllegalArgumentException("관광지 이름이 비어있으면 안돼요");
        }

        Region region = sightDto.getRegion();
        if (region == null || region.getName() == null || region.getName().isEmpty()) {
            throw new IllegalArgumentException("Region 정보가 없거나 올바르지 않습니다.");
        }
        
        List<Region> regions = regionRepository.findByName(region.getName());
        if (regions.isEmpty()) {
            System.out.println("새로운 Region 저장: " + region.getName());
            region = regionRepository.save(region);  // 새로운 Region 저장
        } else {
            region = regions.get(0);  // 첫 번째 Region 사용
        }


        // Sight 객체로 변환
        Sight sight = sightDto.toEntity();
        sight.setRegion(region);
        
        if (sightDto.getImageUrls() != null && !sightDto.getImageUrls().isEmpty()) {
            sight.setIsHasImage(1);  // 이미지가 있으면 is_has_image를 1로 설정
        } else {
            sight.setIsHasImage(0);  // 이미지가 없으면 is_has_image를 0으로 설정
        }
        
        System.out.println("Saving sight: " + sight.getSightName());
        System.out.println("Food region: " + sight.getRegion().getName());

        sight=sightRepository.save(sight);
        
        System.out.println("Saved sight with ID:"+sight.getId());
        return SightDTO.toDto(sight);
        
    }

    // 명소 수정
    @Transactional
    public SightDTO updateSight(Long id, SightDTO sightDto) {
        Optional<Sight> sightOpt = sightRepository.findById(id);

        if (sightOpt.isPresent()) {
            Sight sight = sightOpt.get();
            
         // Region의 name 필드에 country 값을 업데이트하는 로직 추가
            Region region = sightDto.getRegion();
            if (region == null || region.getName() == null || region.getName().isEmpty()) {
                throw new IllegalArgumentException("Region 정보가 없거나 올바르지 않습니다.");
            }

            List<Region> regions = regionRepository.findByName(region.getName());
            if (regions.isEmpty()) {
                System.out.println("새로운 Region 저장: " + region.getName());
                region = regionRepository.save(region);  // 새로운 Region 저장
            } else {
                region = regions.get(0);  // 첫 번째 Region 사용
            }

            sight.setSightName(sightDto.getSightName());
            sight.setEntranceFee(sightDto.getEntranceFee());
            sight.setDescriptions(sightDto.getDescriptions());
            sight.setRegion(region);
            sight.setAddress(sightDto.getAddress());
            sight.setTotalReviewCount(sightDto.getTotalReviewCount());
            sight.setAverageReviewRate(sightDto.getAverageReviewRate());

            if (sightDto.getImageUrls() != null && !sightDto.getImageUrls().isEmpty()) {
                sight.setIsHasImage(1);  // 이미지가 있으면 is_has_image를 1로 설정
            } else {
                sight.setIsHasImage(0);  // 이미지가 없으면 is_has_image를 0으로 설정
            }

            Sight updatedSight = sightRepository.save(sight);

            // Print 디버깅: 업데이트된 음식 정보 출력
            System.out.println("Updated food with ID: " + updatedSight.getId());

            return SightDTO.toDto(updatedSight);
        } else {
            throw new IllegalArgumentException("주어진 번호의 음식을 찾을 수 없어요");
        }

    }

    // ID로 명소 삭제
    @Transactional
    public void deleteSight(Long id) {
        if (sightRepository.existsById(id)) {
            sightRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("주어진 번호의 관광지를 찾을 수 없어요");
        }
    }

    // 특정 지역의 명소 검색
    public List<SightDTO> findSightsByRegionName(String regionName) {
        // 디버깅: 지역 이름 로그 출력
        System.out.println("Searching for sights in region: " + regionName);

        Region region = regionRepository.findByName(regionName).stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid RegionName"));

        return sightRepository.findByRegion_Name(regionName).stream()
                .map(SightDTO::toDto)
                .collect(Collectors.toList());
    }

    // 명소 이름으로 검색
    public List<SightDTO> findSightsByName(String sightName) {
        return sightRepository.findBySightName(sightName).stream()
                .map(SightDTO::toDto)
                .collect(Collectors.toList());
    }

    // 특정 리뷰 평점 이상의 명소 검색
    public List<SightDTO> findSightsByReviewRate(float reviewRate) {
        return sightRepository.findByAverageReviewRateGreaterThanEqual(reviewRate).stream()
                .map(SightDTO::toDto)
                .collect(Collectors.toList());
    }

	public List<SightDTO> findSightsByRegionId(Long regionId) {
		// TODO Auto-generated method stub
		return null;
	}
}
