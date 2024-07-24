package com.ict.traveljoy.service.events;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.traveljoy.repository.events.Events;
import com.ict.traveljoy.repository.events.EventsRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EventsService {

    @Autowired
    private EventsRepository eventsRepository;

    // 모든 이벤트 검색
    public List<EventsDTO> findAllEvents() {
        return eventsRepository.findAll().stream()
                .map(events -> EventsDTO.toDto(events))
                .collect(Collectors.toList());
    }

    // ID로 이벤트 검색
    public Optional<EventsDTO> findEventById(Long id) {
        return eventsRepository.findById(id)
                .map(events -> EventsDTO.toDto(events));
    }

    // 이벤트 저장
    public EventsDTO saveEvent(EventsDTO eventsDto) {
        // 데이터 유효성 검증
        if (eventsDto.getEventName() == null) {
            throw new IllegalArgumentException("이벤트 이름이 비어있으면 안돼요");
        }

        Events event = eventsDto.toEntity();
        event = eventsRepository.save(event);
        return EventsDTO.toDto(event);
    }

    // ID로 이벤트 삭제
    public void deleteEvent(Long id) {
        if (eventsRepository.existsById(id)) {
            eventsRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("주어진 번호의 이벤트를 찾을 수 없어요");
        }
    }

    // 특정 지역의 이벤트 검색
    public List<EventsDTO> findEventsByRegionId(Long regionId) {
        return eventsRepository.findByRegion_Id(regionId).stream()
                .map(events -> EventsDTO.toDto(events))
                .collect(Collectors.toList());
    }

    // 이벤트 이름으로 검색
    public List<EventsDTO> findEventsByName(String eventName) {
        return eventsRepository.findByEventName(eventName).stream()
                .map(events -> EventsDTO.toDto(events))
                .collect(Collectors.toList());
    }

    // 특정 리뷰 평점 이상의 이벤트 검색
    public List<EventsDTO> findEventsByReviewRate(float reviewRate) {
        return eventsRepository.findByAverageReviewRateGreaterThanEqual(reviewRate).stream()
                .map(events -> EventsDTO.toDto(events))
                .collect(Collectors.toList());
    }
}
