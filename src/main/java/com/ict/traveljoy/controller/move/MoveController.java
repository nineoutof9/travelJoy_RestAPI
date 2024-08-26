package com.ict.traveljoy.controller.move;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.traveljoy.move.service.MoveDTO;
import com.ict.traveljoy.move.service.MoveService;
import com.ict.traveljoy.place.hotel.service.HotelDTO;
import com.ict.traveljoy.plan.progress.repository.PlanProgress2;
import com.ict.traveljoy.plan.progress.service.PlanProgress1DTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name="move", description = "경로")
@RestController
@RequestMapping("/api/move")
@CrossOrigin
@RequiredArgsConstructor
public class MoveController {
	
	@Autowired
	private final MoveService moveService;
	
	// 저장
	@PostMapping("/move")
	@Operation(summary = "경로 저장", description = "경로 저장 컨트롤러")
	public ResponseEntity<MoveDTO> saveMove(@RequestBody MoveDTO moveDTO){
		try {
		MoveDTO saveDTO = moveService.saveMove(moveDTO);
		return ResponseEntity.ok(saveDTO);
		}
		catch(Exception e) {e.printStackTrace();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
	// 수정
    @PutMapping("/move/{id}")
    @Operation(summary = "경로 수정", description = "경로 수정 컨트롤러")
    public ResponseEntity<MoveDTO> updateMove(@RequestBody MoveDTO moveDTO, PlanProgress2 planProgress2) {
       
       try {
    	   MoveDTO updateMove = moveService.updateMove(moveDTO, planProgress2);
    	   if(updateMove == null) {
   			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   		}
   		return new ResponseEntity<>(updateMove,HttpStatus.OK);
       } catch(Exception e) {e.printStackTrace();
       
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);}
       

    }
	
    //삭제
    @DeleteMapping("/move/{id}")
    @Operation(summary = "경로 삭제", description = "경로 삭제 컨트롤러")
    public String deleteMove(@PathVariable("id") Long id) {
    	moveService.deleteMove(id);
       return "경로 삭제 성공";
    }
	
	// 시작위치로 조회
    @GetMapping("/move/{startDetailPlanId}")
	@Operation(summary = "1단계 조회(시작위치)", description = "시작 위치로 조회")
	public ResponseEntity<MoveDTO> getMovesByStartDetailPlanId(@PathVariable("startDetailPlan_Id") Long startDetailPlan_Id){
		
		try {
		List<MoveDTO> startDetailPlanId = moveService.getMovesByStartDetailPlanId(startDetailPlan_Id);
		if(startDetailPlanId.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
		
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

    // 종료위치로 조회
    @GetMapping("/move/{endDetailPlanId}")
	@Operation(summary = "1단계 조회(종료위치)", description = "종료 위치로 조회")
	public ResponseEntity<MoveDTO> getMovesByEndDetailPlanId(@PathVariable("endDetailPlan_Id") Long endDetailPlan_Id){
		
		try {
		List<MoveDTO> endDetailPlanId = moveService.getMovesByEndDetailPlanId(endDetailPlan_Id);
		if(endDetailPlanId.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
		
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
    // 교통수단으로 조회
    @GetMapping("/move/{transportationId}")
	@Operation(summary = "1단계 조회(교통수단)", description = "교통수단으로 조회")
	public ResponseEntity<MoveDTO> getMovesByTransportationId(@PathVariable("transportation_Id") Long transportation_Id){
		
		try {
		List<MoveDTO> transportationId = moveService.getMovesByTransportationId(transportation_Id);
		if(transportationId.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
		
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
