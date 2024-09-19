//package com.ict.traveljoy.pushalarm.service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//
//import com.ict.traveljoy.pushalarm.repository.PushAlarm;
//import com.ict.traveljoy.pushalarm.repository.PushAlarmRepository;
//
//import jakarta.transaction.Transactional;
//
//@Service
//@Transactional
//public class PushAlarmService2 {
//
//
//	@Autowired
//	private PushAlarmRepository pushAlarmRepository;
//
//	    // 새로운 PushAlarm을 저장하는 메서드
//	    public PushAlarmDTO savePushAlarm(PushAlarmDTO dto) {
//	    	
//	        if(dto.getTitle() == null && dto.getPushAlarmContent() == null) {
//	        	throw new IllegalArgumentException("알림의 제목과 내용을 넣어주세요");
//	    	
//	        	 }
//	        PushAlarm pushAlarm = dto.toEntity();
//	        pushAlarm = pushAlarmRepository.save(pushAlarm);
//	        return PushAlarmDTO.toDTO(pushAlarm);
//	    }
//
//	    // ID로 특정 PushAlarm을 조회하는 메서드
//	    public PushAlarmDTO findById(Long id) {
//	        Optional<PushAlarm> pushAlarms = pushAlarmRepository.findById(id);
//	        return pushAlarms.map(pushAlarm->PushAlarmDTO.toDTO(pushAlarm))
//	                .orElseThrow(() -> new IllegalArgumentException("해당 ID로 푸시 알림을 찾을 수 없습니다: " + id));
//	    }
//
//	    // 모든 active PushAlarm을 조회하는 메서드
//	    public List<PushAlarmDTO> findAllActive() {
//	        List<PushAlarm> pushAlarms = pushAlarmRepository.findAll();
//	        List<PushAlarmDTO> alarmDTOs = new ArrayList<PushAlarmDTO>();
//	        
//	        for(PushAlarm alarm:pushAlarms) {
//	        	if(alarm.getIsActive()==1) {
//	        		alarmDTOs.add(PushAlarmDTO.toDTO(alarm));
//	        		System.out.println("alarm"+alarm.getTitle());
//	        	}
//	        }
//	        return alarmDTOs;
//	    }
//	    
//	    // 모든 PushAlarm을 조회하는 메서드
//	    public List<PushAlarmDTO> findAll() {
//	        List<PushAlarm> pushAlarms = pushAlarmRepository.findAll();
//	        return pushAlarms.stream()
//	                .map(pushAlarm->PushAlarmDTO.toDTO(pushAlarm))
//	                .collect(Collectors.toList());
//	    }
//
//	    // 특정 PushAlarm을 삭제하는 메서드
//	    public void deleteAlarm(Long id) {
//	    	
//	        if (pushAlarmRepository.existsById(id)) {
//	        	pushAlarmRepository.deleteById(id);
//	            
//	        }else {
//	        throw new IllegalArgumentException("푸시 알림을 찾을 수 없습니다 ");
//	        }
//	    }
//
//	    // 특정 PushAlarm을 업데이트하는 메서드
//	    public PushAlarmDTO updatePushAlarm(Long id, PushAlarmDTO dto) {
//	        PushAlarm existingEntity = pushAlarmRepository.findById(id)
//	                .orElseThrow(() -> new IllegalArgumentException("해당 ID로 푸시 알림을 찾을 수 없습니다: " + id));
//
//	        existingEntity.setTitle(dto.getTitle());
//	        existingEntity.setPushAlarmContent(dto.getPushAlarmContent());
//	        existingEntity.setIsActive(dto.getIsActive() != null && dto.getIsActive() ? 1 : 0);
//	        existingEntity.setIsDelete(dto.getIsDelete() != null && dto.getIsDelete() ? 1 : 0);
//
//	        PushAlarm updatedEntity = pushAlarmRepository.save(existingEntity);
//	        return PushAlarmDTO.toDTO(updatedEntity);
//	    }
//}
