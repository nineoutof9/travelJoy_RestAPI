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

import com.ict.traveljoy.plan.details.service.PlanInterestDTO;
import com.ict.traveljoy.plan.details.service.PlanInterestService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "PlanInterest", description = "관심사항")
@RestController
@RequestMapping("/api/plan")
@CrossOrigin
@RequiredArgsConstructor
public class PlanInterestController {

	@Autowired
	private final PlanInterestService planInterestService;
	
	@CrossOrigin
	@PostMapping("/interest")
	@Operation(summary = "관심사항 저장", description = "관심사항 저장 컨트롤러")
	public ResponseEntity<PlanInterestDTO> savePlanInterest(@RequestBody PlanInterestDTO planInterestDTO){
		
		
		try {
			PlanInterestDTO savePlanInterest = planInterestService.savePlanInterest(planInterestDTO);
		if(savePlanInterest == null) {
			
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			
		}
		return new ResponseEntity<>(savePlanInterest,HttpStatus.CREATED);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@CrossOrigin
	@PutMapping("/interest")
	@Operation(summary = "관심사항 수정", description = "관심사항 수정 컨트롤러")
	public ResponseEntity<PlanInterestDTO> updatePlanInterest(@RequestBody PlanInterestDTO planInterestDTO){
		
		
		try {
			PlanInterestDTO updatePlanInterest = planInterestService.updatePlanInterest(planInterestDTO);
		
		if(updatePlanInterest == null) {
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(updatePlanInterest,HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@CrossOrigin
	@DeleteMapping("/interest/{planId}")
	@Operation(summary = "관심사항 삭제", description = "관심사항 삭제 컨트롤러")
	public ResponseEntity<Void> deletePlanInterest(@PathVariable Long plan_Id){
		
		
		try {
		planInterestService.deletePlanInterestByPlanId(plan_Id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	
	@CrossOrigin
	@GetMapping("/interest/{planId}")
	@Operation(summary = "관심사항 조회", description = "planId로 관심사항 조회 컨트롤러")
	public ResponseEntity<PlanInterestDTO> getPlanInterest(@PathVariable Long plan_Id){
		
		
		try {
			PlanInterestDTO planInterestByPlanId = (PlanInterestDTO) planInterestService.getPlanInterestsByPlanId(plan_Id);
			if(planInterestByPlanId == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(planInterestByPlanId,HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@CrossOrigin
	@GetMapping("/interest/{interestId}")
	@Operation(summary = "관심사항 조회", description = "interestId로 관심사항 조회 컨트롤러")
	public ResponseEntity<PlanInterestDTO> getPlanInterestsByInterestId(@PathVariable Long interest_Id){
		
		
		try {
			PlanInterestDTO planInterestByInterestId = (PlanInterestDTO) planInterestService.getPlanInterestsByInterestId(interest_Id);
		if(planInterestByInterestId == null) {
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(planInterestByInterestId,HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@CrossOrigin
	@GetMapping("/interest/{planId}/{intersetId}")
	@Operation(summary = "관심사항 조회", description = "planID,interestId로 관심사항 조회 컨트롤러")
	public ResponseEntity<PlanInterestDTO> getPlanInterestByPlanIdAndInterestId(@PathVariable Long plan_Id, @PathVariable Long interest_Id){
		
		
		try {
			PlanInterestDTO planInterest = (PlanInterestDTO)planInterestService.getPlanInterestByPlanIdAndInterestId(plan_Id,interest_Id);
			if(planInterest == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(planInterest,HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@CrossOrigin
	@GetMapping("/interest/{intersetIds}")
	@Operation(summary = "관심사항 조회", description = "특정 관심사에 해당하는 PlanInterest로 관심사항 조회 컨트롤러")
	public ResponseEntity<PlanInterestDTO> getPlanInterestsByInterestIds(@PathVariable List<Long> interest_Ids){
		
		
		try {
			PlanInterestDTO planInterest = (PlanInterestDTO)planInterestService.getPlanInterestsByInterestIds(interest_Ids);
		return new ResponseEntity<>(planInterest,HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
}
