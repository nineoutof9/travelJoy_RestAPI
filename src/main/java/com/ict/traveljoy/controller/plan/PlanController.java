package com.ict.traveljoy.controller.plan;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

import com.ict.traveljoy.plan.service.PlanDTO;
import com.ict.traveljoy.plan.service.PlanService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Plan", description = "플랜")
@RestController
@RequestMapping("/api")
@CrossOrigin
@RequiredArgsConstructor
public class PlanController {
	
	@Autowired
	private final PlanService planService;
	
	@PostMapping("/plan")
	@Operation(summary = "plan 저장", description = "plan 저장 컨트롤러")
	public ResponseEntity<PlanDTO> savePlan(@RequestBody PlanDTO planDTO){
		
		try {
			PlanDTO savePlan = planService.savePlan(planDTO);
			if(savePlan == null) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(savePlan,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
	}
	
	@PutMapping("/plan")
	@Operation(summary = "plan 수정", description = "plan 수정 컨트롤러")
	public ResponseEntity<PlanDTO> updatePlan(@RequestBody PlanDTO planDTO){
		
		try {
			PlanDTO updatePlan = planService.updatePlan(planDTO);
			if(updatePlan == null) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(updatePlan,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
	}
	
	@DeleteMapping("/plan/{id}")
	@Operation(summary = "plan 삭제", description = "plan 삭제 컨트롤러")
	public ResponseEntity<String> deletePlan(@PathVariable("id") Long id){
		
		try {
			planService.deletePlan(id);
			return new ResponseEntity<>("계획이 삭제되었습니다.",HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
	}
	
	@GetMapping("/plan/{id}")
	@Operation(summary = "plan 조회(id)", description = "plan 조회 컨트롤러")
	public ResponseEntity<PlanDTO> getPlanById(@PathVariable("id") Long id){
	    try {
	        Optional<PlanDTO> planDTO = planService.getPlanById(id);
	        if(planDTO.isEmpty()) { // isEmpty()로 수정
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        }
	        return new ResponseEntity<>(planDTO.get(), HttpStatus.OK);
	    } catch(Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@GetMapping("/plan/isActive")
	@Operation(summary = "plan 조회(active)", description = "plan 조회 컨트롤러")
	public ResponseEntity<List<PlanDTO>> getPlanByIsActive(@RequestParam(name = "isActive") Integer isActive){
		
		try {
			List<PlanDTO> plans = planService.getPlansByIsActive(isActive);
			if(plans.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(plans,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			
		}

	}
	
	@GetMapping("/plan/createDate")
	@Operation(summary = "plan 조회(date)", description = "plan 조회 컨트롤러")
	public ResponseEntity<List<PlanDTO>> getPlanByCreateDate(@RequestParam(name = "createDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime createDate){
		
		try {
			List<PlanDTO> plans = planService.getPlansCreatedAfter(createDate);
			if(plans.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(plans,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			
		}

	}
	
	@GetMapping("/plan/keyword")
	@Operation(summary = "plan 조회(keyword)", description = "plan 조회 컨트롤러")
	public ResponseEntity<List<PlanDTO>> getPlansByKeyword(@RequestParam(name = "keyword") String keyword){
		
		try {
			List<PlanDTO> plans = planService.getPlansByKeyword(keyword);
			if(plans.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(plans,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			
		}

	}
	

	@GetMapping("/plan/all")
	@Operation(summary = "모든 plan 조회", description = "모든 plan 조회 컨트롤러")
	public ResponseEntity<List<PlanDTO>> getAllPlans(){
		
		try {
			List<PlanDTO> plans = planService.findAllPlans();
			if(plans.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(plans,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			
		}

	}
	

}/////////////
