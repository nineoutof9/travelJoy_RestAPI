package com.ict.traveljoy.place.region.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.traveljoy.place.region.repository.Region;
import com.ict.traveljoy.place.region.repository.RegionRepository;

@Service
public class RegionService {
    
    @Autowired 
    private RegionRepository regionRepository;

    public RegionDTO saveRegion(RegionDTO regionDto) {
        Region region = regionDto.toEntity();
        Region savedRegion = regionRepository.save(region);
        return RegionDTO.toDto(savedRegion);
    }

    public Optional<RegionDTO> getRegionById(Long id) {
        return regionRepository.findById(id).map(RegionDTO::toDto);
    }

    public List<RegionDTO> getAllRegion() {
        return regionRepository.findAll().stream()
                .map(RegionDTO::toDto)
                .collect(Collectors.toList());
    }

    public RegionDTO updateRegion(Long id, RegionDTO regionDto) {
        Optional<Region> existingRegionOpt = regionRepository.findById(id);

        if (existingRegionOpt.isPresent()) {
            Region existingRegion = existingRegionOpt.get();
            existingRegion.setName(regionDto.getName());
            existingRegion.setRegionInfo(regionDto.getRegionInfo());
            Region updatedRegion = regionRepository.save(existingRegion);
            return RegionDTO.toDto(updatedRegion);
        } else {
            throw new IllegalArgumentException("Region with ID " + id + " not found");
        }
    }

    public void deleteRegion(Long id) {
        regionRepository.deleteById(id);
    }

    // 이름으로 Region 엔터티 검색
    public List<RegionDTO> getRegionByName(String name) {
        return regionRepository.findByName(name).stream()
                .map(RegionDTO::toDto)
                .collect(Collectors.toList());
    }
}
