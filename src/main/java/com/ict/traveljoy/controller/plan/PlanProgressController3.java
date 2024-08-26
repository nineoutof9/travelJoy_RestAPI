package com.ict.traveljoy.controller.plan;

import java.util.List;

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

import com.ict.traveljoy.plan.progress.service.PlanProgress1DTO;
import com.ict.traveljoy.plan.progress.service.PlanProgress1Service;
import com.ict.traveljoy.plan.progress.service.PlanProgress3DTO;
import com.ict.traveljoy.plan.progress.service.PlanProgress3Service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "PlanProgress3", description = "3단계")
@RestController
@RequestMapping("/api/plan")
@CrossOrigin
@RequiredArgsConstructor
public class PlanProgressController3 {
	
	@Autowired
	private final PlanProgress3Service planProgress3Service;
	
	@PostMapping("/progress3")
	@Operation(summary = "3단계 저장", description = "3단계 저장 컨트롤러")
	public ResponseEntity<PlanProgress3DTO> savePlanProgress3(@RequestBody PlanProgress3DTO planProgress3DTO){
		
		try {
		PlanProgress3DTO savePlanProgress3 = planProgress3Service.savePlanProgress3(planProgress3DTO);
		if(savePlanProgress3 == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(savePlanProgress3,HttpStatus.CREATED);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/progress3")
	@Operation(summary = "3단계 수정", description = "3단계 수정 컨트롤러")
	public ResponseEntity<PlanProgress3DTO> updatePlanProgress3(@RequestBody PlanProgress3DTO planProgress3DTO){
		
		try {
		PlanProgress3DTO updatePlanProgress3 = planProgress3Service.updatePlanProgress3(planProgress3DTO);
		if(updatePlanProgress3 == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(updatePlanProgress3,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/progress3/{planProgress3Id}")
	@Operation(summary = "3단계 삭제", description = "3단계 삭제 컨트롤러")
	public ResponseEntity<Void> deletePlanProgress3(@PathVariable Long id){
		
		try {
		planProgress3Service.deletePlanProgress3(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	@GetMapping("/progress3/{planProgressByPlanId}")
	@Operation(summary = "3단계 조회(계획ID)", description = "특정 계획ID로 조회")
	public ResponseEntity<PlanProgress3DTO> getPlanProgressesByPlanId(Long plan_Id){
		
		try {
		List<PlanProgress3DTO> planProgressByPlanId = planProgress3Service.getPlanProgressesByPlanId(plan_Id);
		if(planProgressByPlanId.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
		
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/progress3/{planProgressByTransportation}")
	@Operation(summary = "3단계 조회(교통)", description = "특정 교통 여부로 조회")
	public ResponseEntity<PlanProgress3DTO> getPlanProgressesByIsTransportation(boolean isTransportation){
		
		try {
		List<PlanProgress3DTO> getPlanProgressesByIsTransportation = planProgress3Service.getPlanProgressesByIsTransportation(isTransportation);
	
		if(getPlanProgressesByIsTransportation.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
		
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/progress3/{planProgressByDistance}")
	@Operation(summary = "3단계 조회(거리)", description = "특정 교통 여부로 조회")
	public ResponseEntity<PlanProgress3DTO> getPlanProgressesByIsDistance(boolean isDistance){
		
		try {
		List<PlanProgress3DTO> getPlanProgressesByIsDistance = planProgress3Service.getPlanProgressesByIsDistance(isDistance);
	
		if(getPlanProgressesByIsDistance.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
		
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/progress3/{planProgressByPrice}")
	@Operation(summary = "3단계 조회(비용)", description = "특정 비용 여부로 조회")
	public ResponseEntity<PlanProgress3DTO> getPlanProgressesByIsPrice(boolean isPrice){
		
		try {
		List<PlanProgress3DTO> getPlanProgressesByIsPrice = planProgress3Service.getPlanProgressesByIsPrice(isPrice);
	
		if(getPlanProgressesByIsPrice.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
		
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/progress3/{planProgressByMinCost}")
	@Operation(summary = "3단계 조회(최소비용)", description = "최소 비용 여부로 조회")
	public ResponseEntity<PlanProgress3DTO> getPlanProgressesByMinimumCost(Long minimum_Cost){
		
		try {
		List<PlanProgress3DTO> getPlanProgressesByMinimumCost = planProgress3Service.getPlanProgressesByMinimumCost(minimum_Cost);
	
		if(getPlanProgressesByMinimumCost.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
		
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/progress3/{planProgressByMaxCost}")
	@Operation(summary = "3단계 조회(최대비용)", description = "최대 비용 여부로 조회")
	public ResponseEntity<PlanProgress3DTO> getPlanProgressesByMaximumCost(Long maximum_Cost){
		
		try {
		List<PlanProgress3DTO> getPlanProgressesByMaximumCost = planProgress3Service.getPlanProgressesByMaximumCost(maximum_Cost);
	
		if(getPlanProgressesByMaximumCost.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
		
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/progress3/{planProgressByRate}")
	@Operation(summary = "3단계 조회(평가)", description = "특정 평가 여부로 조회")
	public ResponseEntity<PlanProgress3DTO> getPlanProgressesByIsRate(boolean isRate){
		
		try {
		List<PlanProgress3DTO> getPlanProgressesByIsRate = planProgress3Service.getPlanProgressesByIsRate(isRate);
	
		if(getPlanProgressesByIsRate.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
		
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/progress3/{planProgressByMinRate}")
	@Operation(summary = "3단계 조회(최소평가)", description = "최소 평가 여부로 조회")
	public ResponseEntity<PlanProgress3DTO> getPlanProgressesByMinimumRate(Integer minimum_Rate){
		
		try {
		List<PlanProgress3DTO> getPlanProgressesByMinimumRate = planProgress3Service.getPlanProgressesByMinimumRate(minimum_Rate);
	
		if(getPlanProgressesByMinimumRate.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
		
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/progress3/{planProgressByMaxRate}")
	@Operation(summary = "3단계 조회(최대평가)", description = "최대 평가 여부로 조회")
	public ResponseEntity<PlanProgress3DTO> getPlanProgressesByMaximumRate(Integer maximum_Rate){
		
		try {
		List<PlanProgress3DTO> getPlanProgressesByMaximumRate = planProgress3Service.getPlanProgressesByMaximumRate(maximum_Rate);
	
		if(getPlanProgressesByMaximumRate.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
		
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

}
