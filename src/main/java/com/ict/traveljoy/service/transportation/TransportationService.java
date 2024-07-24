package com.ict.traveljoy.service.transportation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.traveljoy.repository.transportation.Transportation;
import com.ict.traveljoy.repository.transportation.TransportationRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TransportationService {

    @Autowired
    private TransportationRepository transportationRepository;

    // 모든 교통수단 정보 검색
    public List<TransportationDTO> findAllTransportations() {
        return transportationRepository.findAll().stream()
                .map(transportation -> TransportationDTO.toDto(transportation))
                .collect(Collectors.toList());
    }

    // ID로 교통수단 정보 검색
    public Optional<TransportationDTO> findTransportationById(Long id) {
        return transportationRepository.findById(id)
                .map(transportation -> TransportationDTO.toDto(transportation));
    }

    // 교통수단 정보 저장
    public TransportationDTO saveTransportation(TransportationDTO transportationDto) {
        // 데이터 유효성 검증
        if (transportationDto.getStartDate() == null) {
            throw new IllegalArgumentException("출발 날짜는 비어있으면 안돼요");
        }

        Transportation transportation = transportationDto.toEntity();
        transportation = transportationRepository.save(transportation);
        return TransportationDTO.toDto(transportation);
    }

    // ID로 교통수단 정보 삭제
    public void deleteTransportation(Long id) {
        if (transportationRepository.existsById(id)) {
            transportationRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("주어진 번호의 교통수단 정보를 찾을 수 없어요");
        }
    }

    // 이동 유형에 따른 검색
    public List<TransportationDTO> findTransportationsByIsBus(char isBus) {
        return transportationRepository.findByIsBus(isBus).stream()
                .map(transportation -> TransportationDTO.toDto(transportation))
                .collect(Collectors.toList());
    }

    public List<TransportationDTO> findTransportationsByIsTrain(char isTrain) {
        return transportationRepository.findByIsTrain(isTrain).stream()
                .map(transportation -> TransportationDTO.toDto(transportation))
                .collect(Collectors.toList());
    }

    public List<TransportationDTO> findTransportationsByIsAirplain(char isAirplain) {
        return transportationRepository.findByIsAirplain(isAirplain).stream()
                .map(transportation -> TransportationDTO.toDto(transportation))
                .collect(Collectors.toList());
    }

    public List<TransportationDTO> findTransportationsByIsDrive(char isDrive) {
        return transportationRepository.findByIsDrive(isDrive).stream()
                .map(transportation -> TransportationDTO.toDto(transportation))
                .collect(Collectors.toList());
    }


    // 특정 기간 내의 교통수단 검색
    public List<TransportationDTO> findTransportationsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return transportationRepository.findByStartDate(startDate, endDate).stream()
                .map(transportation -> TransportationDTO.toDto(transportation))
                .collect(Collectors.toList());
    }

    // 가격 범위 내의 교통수단 검색
    public List<TransportationDTO> findTransportationsByPrice(float minPrice, float maxPrice) {
        return transportationRepository.findByPrice(minPrice, maxPrice).stream()
                .map(transportation -> TransportationDTO.toDto(transportation))
                .collect(Collectors.toList());
    }
}
