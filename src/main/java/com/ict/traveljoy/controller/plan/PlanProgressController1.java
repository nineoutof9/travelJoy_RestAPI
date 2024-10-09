package com.ict.traveljoy.controller.plan;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ict.traveljoy.plan.progress.service.PlanProgress1DTO;
import com.ict.traveljoy.plan.progress.service.PlanProgress1Service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "PlanProgress1", description = "1단계")
@RestController
@RequestMapping("/api/plan")
@CrossOrigin
@RequiredArgsConstructor
public class PlanProgressController1 {
	
	@Autowired
	private final PlanProgress1Service planProgress1Service; 

	@PostMapping("/progress1")
	@Operation(summary = "1단계 저장", description = "1단계 저장 컨트롤러")
	public ResponseEntity<PlanProgress1DTO> savePlanProgress1(@RequestBody PlanProgress1DTO planProgress1DTO){
		
		try {
		PlanProgress1DTO savePlanProgress1 = planProgress1Service.savePlanProgress1(planProgress1DTO);
		if(savePlanProgress1 == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(savePlanProgress1,HttpStatus.CREATED);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/progress1")
	@Operation(summary = "1단계 수정", description = "1단계 수정 컨트롤러")
	public ResponseEntity<PlanProgress1DTO> updatePlanProgress1(@RequestBody PlanProgress1DTO planProgress1DTO){
		
		try {
		PlanProgress1DTO updatePlanProgress1 = planProgress1Service.updatePlanProgress1(planProgress1DTO);
		if(updatePlanProgress1 == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(updatePlanProgress1,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/progress1/{id}")
	@Operation(summary = "1단계 삭제", description = "1단계 삭제 컨트롤러")
	public ResponseEntity<Void> deletePlanProgress1(@PathVariable("id") Long id){
		
		try {
		planProgress1Service.deletePlanProgress1(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/progress1")
	@Operation(summary = "1단계 조회(기간)", description = "특정 기간으로 조회")
	public ResponseEntity<PlanProgress1DTO> getPlanProgressesByPlanStartDateBetween(
			@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
	        @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate){
		
		try {
		List<PlanProgress1DTO> planProgressByDate = planProgress1Service.getPlanProgressesByPlanStartDateBetween(startDate,endDate);
		if(planProgressByDate.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
		
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/progress1/{min_Travelers}")
	@Operation(summary = "1단계 조회(여행자순)", description = "특정 여행자 수 이상으로 조회")
	public ResponseEntity<PlanProgress1DTO> getPlanProgressesByTravelersGreaterThanEqual(@PathVariable("min_Travelers") Integer min_Travelers){
		
		try {
		List<PlanProgress1DTO> planProgressByGreater = planProgress1Service.getPlanProgressesByTravelersGreaterThanEqual(min_Travelers);
		if(planProgressByGreater.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
		
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/progress1/{max_TravelCost}")
	@Operation(summary = "1단계 조회(비용)", description = "특정 비용 이하로 조회")
	public ResponseEntity<PlanProgress1DTO> getPlanProgressesByTravelCostLessThanEqual(@PathVariable("max_TravelCost") BigDecimal max_TravelCost){
		
		try {
		List<PlanProgress1DTO> planProgressByCostLess = planProgress1Service.getPlanProgressesByTravelCostLessThanEqual(max_TravelCost);
		if(planProgressByCostLess.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
		
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/progress1/{plan_Id}")
	@Operation(summary = "1단계 조회(계획ID)", description = "특정 계획ID로 조회")
	public ResponseEntity<PlanProgress1DTO> getPlanProgressesByPlanId(@PathVariable("plan_Id") Long plan_Id){
		
		try {
		List<PlanProgress1DTO> planProgressByPlanId = planProgress1Service.getPlanProgressesByPlanId(plan_Id);
		if(planProgressByPlanId.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
		
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
