package com.ict.traveljoy.move.service;

import com.ict.traveljoy.move.repository.Move;
import com.ict.traveljoy.move.repository.MoveRepository;
import com.ict.traveljoy.plan.progress.repository.PlanProgress2;
import com.ict.traveljoy.place.transportation.repository.Transportation;
import com.ict.traveljoy.place.transportation.repository.TransportationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MoveService {

    private final MoveRepository moveRepository;
    private final TransportationRepository transportationRepository;

    @Autowired
    public MoveService(MoveRepository moveRepository, TransportationRepository transportationRepository) {
        this.moveRepository = moveRepository;
        this.transportationRepository = transportationRepository;
    }

    // startDetailPlanId로 Move 목록 조회
    public List<MoveDTO> getMovesByStartDetailPlanId(Long startDetailPlan_Id) {
        List<Move> moves = moveRepository.findByStartDetailPlan_Id(startDetailPlan_Id);
        return moves.stream()
                .map(MoveDTO::toDto)
                .collect(Collectors.toList());
    }

    // endDetailPlanId로 Move 목록 조회
    public List<MoveDTO> getMovesByEndDetailPlanId(Long endDetailPlan_Id) {
        List<Move> moves = moveRepository.findByEndDetailPlan_Id(endDetailPlan_Id);
        return moves.stream()
                .map(MoveDTO::toDto)
                .collect(Collectors.toList());
    }

    // transportationId로 Move 목록 조회
    public List<MoveDTO> getMovesByTransportationId(Long transportation_Id) {
        List<Move> moves = moveRepository.findByTransportationId(transportation_Id);
        return moves.stream()
                .map(MoveDTO::toDto)
                .collect(Collectors.toList());
    }

    // Move 저장
    public MoveDTO saveMove(MoveDTO moveDto) {
        Move move = moveDto.toEntity();

        // 외래 키로 지정된 Transportation 엔티티 설정
        if (moveDto.getTransportation() != null) {
            Transportation transportation = transportationRepository.findById(moveDto.getTransportation().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid transportation ID: " + moveDto.getTransportation().getId()));
            move.setTransportation(transportation);
        }

        Move savedMove = moveRepository.save(move);
        return MoveDTO.toDto(savedMove);
    }

    // Move 수정
    public MoveDTO updateMove(MoveDTO moveDto, PlanProgress2 planProgress2) {
        Move existingMove = moveRepository.findById(moveDto.getId()).orElse(null);
        if (existingMove != null) {
            // Set startDetailPlan
            if (moveDto.getStartDetailPlan() != null) {
                PlanProgress2 startDetailPlan = new PlanProgress2();
                startDetailPlan.setId(moveDto.getStartDetailPlan().getId());
                existingMove.setStartDetailPlan(startDetailPlan);
            } else {
                existingMove.setStartDetailPlan(null);
            }

            // Set endDetailPlan
            if (moveDto.getEndDetailPlan() != null) {
                PlanProgress2 endDetailPlan = new PlanProgress2();
                endDetailPlan.setId(moveDto.getEndDetailPlan().getId());
                existingMove.setEndDetailPlan(endDetailPlan);
            } else {
                existingMove.setEndDetailPlan(null);
            }

            // Set transportation
            if (moveDto.getTransportation() != null) {
                Transportation transportation = transportationRepository.findById(moveDto.getTransportation().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid transportation ID: " + moveDto.getTransportation().getId()));
                existingMove.setTransportation(transportation);
            } else {
                existingMove.setTransportation(null);
            }

            Move updatedMove = moveRepository.save(existingMove);
            return MoveDTO.toDto(updatedMove);
        }
        return null; // 수정할 Move가 없는 경우
    }

    // Move 삭제
    public void deleteMove(Long moveId) {
        moveRepository.deleteById(moveId);
    }
}
