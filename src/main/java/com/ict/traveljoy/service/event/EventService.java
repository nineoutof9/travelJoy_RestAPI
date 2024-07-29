package com.ict.traveljoy.service.event;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.traveljoy.repository.event.Event;
import com.ict.traveljoy.repository.event.EventRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    // 모든 이벤트 검색
    public List<EventDTO> findAllEvents() {
        return eventRepository.findAll().stream()
                .map(EventDTO::toDto)
                .collect(Collectors.toList());
    }

    // ID로 이벤트 검색
    public Optional<EventDTO> findEventById(Long id) {
        return eventRepository.findById(id)
                .map(EventDTO::toDto);
    }

    // 이벤트 저장
    public EventDTO saveEvent(EventDTO eventDto) {
        // 데이터 유효성 검증
        if (eventDto.getEventName() == null || eventDto.getEventName().isEmpty()) {
            throw new IllegalArgumentException("이벤트 이름이 비어있으면 안돼요");
        }
        // 다른 필수 필드 검증 추가 가능

        Event event = eventDto.toEntity();
        event = eventRepository.save(event);
        return EventDTO.toDto(event);
    }

    // ID로 이벤트 삭제
    public void deleteEvent(Long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("주어진 번호의 이벤트를 찾을 수 없어요");
        }
    }

    // 특정 지역의 이벤트 검색
    public List<EventDTO> findEventsByRegionId(Long regionId) {
        return eventRepository.findByRegion_Id(regionId).stream()
                .map(EventDTO::toDto)
                .collect(Collectors.toList());
    }

    // 이벤트 이름으로 검색
    public List<EventDTO> findEventsByName(String eventName) {
        return eventRepository.findByEventName(eventName).stream()
                .map(EventDTO::toDto)
                .collect(Collectors.toList());
    }

    // 특정 리뷰 평점 이상의 이벤트 검색
    public List<EventDTO> findEventsByReviewRate(float reviewRate) {
        return eventRepository.findByAverageReviewRateGreaterThanEqual(reviewRate).stream()
                .map(EventDTO::toDto)
                .collect(Collectors.toList());
    }
}