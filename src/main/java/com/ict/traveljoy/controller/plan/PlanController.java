package com.ict.traveljoy.controller.plan;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.client.RestTemplate;

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
	
	@Autowired
	private final RestTemplate restTemplate;
	
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
	public ResponseEntity<String> deletePlan(@PathVariable Long id){
		
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
	
	@PostMapping("/plan/kmeans")
	public ResponseEntity<?> handleKmeansClustering(@RequestBody Map<String, Object> requestData) {
	    String flaskUrl = "http://localhost:3030/kmeans";
	    try {
	        // Flask로부터 받은 클러스터링 결과
	        ResponseEntity<Map> response = restTemplate.postForEntity(flaskUrl, requestData, Map.class);
	        Map<String, Object> responseBody = response.getBody();

	        if (responseBody == null || !responseBody.containsKey("result")) {
	            return ResponseEntity.status(500).body("Flask 서버의 응답이 올바르지 않습니다.");
	        }

	        // Flask 결과에서 그룹별로 일차와 좌표를 계산
	        List<Map<String, Object>> resultList = (List<Map<String, Object>>) responseBody.get("result");

	        // 그룹별로 좌표와 데이터를 모은다
	        Map<Integer, List<Map<String, Object>>> groupedData = new HashMap<>();
	        Map<Integer, List<double[]>> groupedCoordinates = new HashMap<>();

	        for (Map<String, Object> resultItem : resultList) {
	            int group = (int) resultItem.get("group");
	            List<Double> coordsList = (List<Double>) resultItem.get("coordinates");
	            double[] coordinates = new double[]{coordsList.get(0), coordsList.get(1)}; // [lat, lng]

	            // 그룹별 좌표 추가
	            groupedCoordinates.computeIfAbsent(group, k -> new ArrayList<>()).add(coordinates);

	            // 그룹별 데이터를 추가 (좌표와 함께 데이터도 유지)
	            groupedData.computeIfAbsent(group, k -> new ArrayList<>()).add(resultItem);
	        }

	        // 각 그룹의 정중앙 좌표 계산 및 일차별 데이터 추가
	        List<Map<String, Object>> dayResults = new ArrayList<>();

	        for (Map.Entry<Integer, List<double[]>> entry : groupedCoordinates.entrySet()) {
	            int group = entry.getKey();
	            List<double[]> coords = entry.getValue();

	            // 정중앙 좌표 계산 (평균값 사용)
	            double[] centroid = calculateCentroid(coords);

	            // 해당 그룹의 모든 데이터와 중앙 좌표를 함께 담아서 반환
	            Map<String, Object> dayResult = new HashMap<>();
	            dayResult.put("day", group + 1);  // n+1일차
	            dayResult.put("centroid", centroid);  // 중앙 좌표
	            dayResult.put("data", groupedData.get(group));  // 일차별 해당 데이터들

	            dayResults.add(dayResult);
	        }

	        // 결과 반환
	        return ResponseEntity.ok(dayResults);
	    } catch (Exception e) {
	        return ResponseEntity.status(500).body("Flask 서버로의 요청 중 오류 발생: " + e.getMessage());
	    }
	}

	// 좌표 리스트에서 정중앙 좌표(평균값)를 계산하는 함수
	private double[] calculateCentroid(List<double[]> coordinates) {
	    double latSum = 0;
	    double lngSum = 0;
	    int count = coordinates.size();

	    for (double[] coord : coordinates) {
	        latSum += coord[0];
	        lngSum += coord[1];
	    }

	    return new double[]{latSum / count, lngSum / count};  // [평균 위도, 평균 경도]
	}


}/////////////
