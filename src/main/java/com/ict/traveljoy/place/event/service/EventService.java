package com.ict.traveljoy.place.event.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ict.traveljoy.place.event.repository.Event;
import com.ict.traveljoy.place.event.repository.EventRepository;
import com.ict.traveljoy.place.hotel.service.HotelDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
    @Transactional
    public EventDTO saveEvent(EventDTO eventDto) {

        if (eventDto.getEventName().isEmpty() && eventDto.getRegion() == null) {
            throw new IllegalArgumentException("값이 비어있으면 안돼요");
        }

        Event event = eventDto.toEntity();
        event = eventRepository.save(event);
        return EventDTO.toDto(event);
    }

    //이벤트 수정
    @Transactional
    public EventDTO updateEvent(Long id, EventDTO eventDto) {
        Optional<Event> eventOpt = eventRepository.findById(id);

        if (eventOpt.isPresent()) {

           Event event = eventOpt.get();
           event.setEventName(eventDto.getEventName());
           event.setEntranceFee(eventDto.getEntranceFee());
            event.setDescriptions(eventDto.getDescriptions());
            event.setAddress(eventDto.getAddress());
            event.setTotalReviewCount(eventDto.getTotalReviewCount());
           event.setRegion(eventDto.getRegion());
           event.setAverageReviewRate(eventDto.getAverageReviewRate());
           event.setEventStartDate(eventDto.getEventStartDate());
           event.setEventEndDate(eventDto.getEventEndDate());

            
            Event updatedEvent = eventRepository.save(event);
            return EventDTO.toDto(updatedEvent);
        } else {
            throw new IllegalArgumentException("주어진 번호의 이벤트를 찾을 수 없어요");
        }
    }
    
    // ID로 이벤트 삭제
    @Transactional
    public void deleteEvent(Long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("주어진 번호의 이벤트를 찾을 수 없어요");
        }
    }

    // 특정 지역의 이벤트 검색

    public List<EventDTO> findEventsByRegionId(Long regionId) {
        return eventRepository.findAllByRegionId(regionId).stream()
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
    public List<EventDTO> findEventsByReviewRate(Float reviewRate) {
        return eventRepository.findByAverageReviewRateGreaterThanEqual(reviewRate).stream()
                .map(EventDTO::toDto)
                .collect(Collectors.toList());
    }

	public List<EventDTO> findDestination(double latitude, double longitude, double distance, LocalDate startDate, LocalDate endDate) {
		return eventRepository.findEventsWithinDistanceAndDateRange(latitude,longitude, distance, startDate, endDate).stream()
				.map(EventDTO::toDto)
        		.collect(Collectors.toList());
	}
}