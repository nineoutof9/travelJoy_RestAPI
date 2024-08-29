package com.ict.traveljoy.controller.plan;

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

import com.ict.traveljoy.plan.details.service.PlanAllergyDTO;
import com.ict.traveljoy.plan.details.service.PlanAllergyService;
import com.ict.traveljoy.plan.details.service.PlanHandicapDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "PlanAllergy", description = "알러지")
@RestController
@RequestMapping("/api/plan")
@CrossOrigin
@RequiredArgsConstructor
public class PlanAllergyController {
	
	@Autowired
	private final PlanAllergyService planAllergyService;
	
	@PostMapping("/Allergey")
	@Operation(summary = "알러지 저장", description = "알러지 저장 컨트롤러")
	public ResponseEntity<PlanAllergyDTO> savePlanAllergy(@RequestBody PlanAllergyDTO planAllergyDTO) {
	    try {
	        // Allergy 값이 null인 경우 처리
	        if (planAllergyDTO.getAllergy() == null || planAllergyDTO.getAllergy().getId() == null) {
	            // Allergy가 null이면 그대로 PlanAllergy를 저장
	            planAllergyDTO.setAllergy(null);
	        }

	        PlanAllergyDTO savedPlanAllergy = planAllergyService.savePlanAllergy(planAllergyDTO);
	        return new ResponseEntity<>(savedPlanAllergy, HttpStatus.CREATED);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@PutMapping("/Allergey")
	@Operation(summary = "알러지 수정", description = "알러지 수정 컨트롤러")
	public ResponseEntity<PlanAllergyDTO> updatePlanAllergy(@RequestBody PlanAllergyDTO planAllergyDTO, Long id){
		
		try {
			PlanAllergyDTO updatePlanAllergy = planAllergyService.updatePlanAllergy(id,planAllergyDTO);
		if(updatePlanAllergy == null) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(updatePlanAllergy,HttpStatus.OK);
		
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.OK);
			
		}
	}
	
	@DeleteMapping("/Allergey/{id}")
	@Operation(summary = "알러지 삭제", description = "알러지 삭제 컨트롤러")
	public ResponseEntity<String> deletePlanAllergey(@PathVariable("id") Long id){
		
		try {
			planAllergyService.deletePlanAllergy(id);
		return new ResponseEntity<>("삭제되었습니다",HttpStatus.OK);
		
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
	
	@GetMapping("/Allergey/{id}")
	@Operation(summary = "알러지 조회(id)", description = "id로 알러지 조회 컨트롤러")
	public ResponseEntity<PlanAllergyDTO> getAllergyById(@PathVariable("id") Long id){
		
		try {
			PlanAllergyDTO getAllergyById = planAllergyService.getPlanAllergyById(id);
		if(getAllergyById == null) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(getAllergyById,HttpStatus.OK);
		
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.OK);
			
		}
	}

}
