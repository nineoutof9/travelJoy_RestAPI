package com.ict.traveljoy.controller.plan;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.traveljoy.info.userinterest.service.UserInterestService;
import com.ict.traveljoy.newplan.NewPlan;
import com.ict.traveljoy.newplan.NewPlanDTO;
import com.ict.traveljoy.newplan.NewPlanService;
import com.ict.traveljoy.plan.service.PlanDTO;
import com.ict.traveljoy.plan.service.PlanService;
import com.ict.traveljoy.users.repository.UserRepository;
import com.ict.traveljoy.users.repository.Users;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Plan", description = "플랜")
@RestController
@RequestMapping("/api")
@CrossOrigin
@RequiredArgsConstructor
public class PlanController {
	
	private final PlanService planService;
	private final RestTemplate restTemplate;
	private final UserInterestService userInterestService;
	private final UserRepository userRepository;
	
    private final NewPlanService newPlanService;



    @PostMapping("/plan/newplan")
    public ResponseEntity<NewPlan> createNewPlan(@RequestBody NewPlanDTO newPlanDTO) {
        NewPlan savedPlan = newPlanService.createNewPlan(newPlanDTO);
        return ResponseEntity.ok(savedPlan);
    }

    @GetMapping("/plan/newplan/{id}")
    public ResponseEntity<NewPlanDTO> getNewPlan(@PathVariable("id") Long id) {
        return ResponseEntity.ok(newPlanService.getNewPlan(id));
    }
    
    @GetMapping("/plan/newplan/email/{email}")
    public ResponseEntity<List<NewPlanDTO>> getNewPlan(@PathVariable("email") String email) {
        List<NewPlanDTO> newPlans = newPlanService.getNewPlanByEmail(email);  // 여러 계획을 가져옴
        return ResponseEntity.ok(newPlans);  // 리스트를 반환
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
	@PostMapping("/plan/ai")
	public ResponseEntity<?> generateAIPlan(@RequestBody Map<String, Object> plan) {
	    try {
	        String flaskUrl = "http://localhost:8000/ai/plan";

	        // 현재 로그인한 유저 정보 가져오기
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String userEmail = authentication.getName(); // 유저 이메일 가져오기

	        // 유저 관심사 조회
	        List<String> userInterests = userInterestService.findInterestsByUser(userEmail);

	        // 관심사 문자열 생성
	        String interestsString = userInterests.isEmpty() 
	            ? "The user has no specific interests."
	            : "The user is interested in: " + String.join(", ", userInterests) + ".";

	        // 여행자 수 받아오기
	        Map<String, Object> travelerCounts = (Map<String, Object>) plan.get("travler");
	        int adult = (int) travelerCounts.getOrDefault("adult", 0);
	        int senior = (int) travelerCounts.getOrDefault("senior", 0);
	        int teen = (int) travelerCounts.getOrDefault("teen", 0);
	        int child = (int) travelerCounts.getOrDefault("child", 0);

	        // 여행 날짜 받아오기
	        String startDate = (String) plan.get("startDate");
	        String endDate = (String) plan.get("endDate");

	        // 옵션 받아오기
	        List<String> options = (List<String>) plan.get("options");
	        String optionsString = options.isEmpty()
	            ? "No specific options were selected."
	            : "The user has selected the following options: " + String.join(", ", options) + ".";

	        String travelerString = "The group consists of " + adult + " adults, " + senior + " seniors, " 
	                              + teen + " teens, and " + child + " children.";

	        // 프롬프트 생성
	        String prompt = "You are a travel assistant. The user has provided a travel plan with destinations. "
	                      + "For each day, distribute the times for each destination considering optimal travel routes and realistic time spent at each location. "
	                      + "The user's preferences and interests should also be considered: " + interestsString + " "
	                      + travelerString + " "
	                      + "The trip will take place from " + startDate + " to " + endDate + ". "
	                      + optionsString + " "
	                      + "Based on this initial plan, create three travel plans: A, B, and C.";

	        // A 플랜 조건
	        prompt += " Plan A must maintain the provided destination sequence and distribute times for each destination."
	                + " The format should be as follows: "
	                + "{ \"plan\": [ { \"day\": 1, \"contents\": [ { \"index\": 1, \"title\": \"Destination Name\", \"addr\": \"Address\", \"time\": \"YYYY-MM-DD HH:MM\", \"lat\": Latitude, \"lng\": Longitude, \"activity\": \"Type of activity\" } ] }, "
	                + "{ \"day\": 2, \"contents\": [ { \"index\": 1, \"title\": \"Destination Name\", \"addr\": \"Address\", \"time\": \"YYYY-MM-DD HH:MM\", \"lat\": Latitude, \"lng\": Longitude, \"activity\": \"Type of activity\" } ] } ] }";

	        // B 플랜 조건
	        prompt += " Plan B allows reordering the destinations for optimal time management and travel efficiency."
	                + " Ensure that the last activity of the day is always a hotel or accommodation."
	                + " The user's preferences and group composition must also be considered: " + interestsString + " "
	                + travelerString + ". Provide appropriate timing between destinations. Return the output in JSON format.";

	        // C 플랜 조건
	        prompt += " Plan C not only uses the provided destination data but also recommends additional destinations or activities based on the region and user interests: " + interestsString + "."
	                + " This plan can reorder the destinations and include new destinations or activities that are relevant to the trip."
	                + " Return the output in JSON format.";

	        prompt += " Please return only the JSON response with three plans: Plan A, Plan B, and Plan C, each considering user interests, traveler group composition, and optimized scheduling.";

	        // 요청 데이터 준비
	        Map<String, Object> request = new HashMap<>();
	        request.put("name", plan.get("name"));

	        // description에 프롬프트와 plan 데이터를 함께 포함
	        if (plan.get("plan") != null) {
	            String planJson = new ObjectMapper().writeValueAsString(plan.get("plan"));
	            String fullPrompt = prompt + " Initial plan: " + planJson;
	            request.put("description", fullPrompt);
	        } else {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("plan 값이 비어 있습니다.");
	        }

	        // Flask로 요청 전송
	        ResponseEntity<Map> aiResponse = restTemplate.postForEntity(flaskUrl, request, Map.class);
	        Map<String, Object> aiResponseBody = aiResponse.getBody();

	        if (aiResponseBody == null || !aiResponseBody.containsKey("content")) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("AI 서버 응답이 올바르지 않습니다.");
	        }

	        String aiGeneratedPlanText = (String) aiResponseBody.get("content");
	        List<Map<String, Object>> generatedPlans = parseAIResponseToPlans(aiGeneratedPlanText);

	        return ResponseEntity.ok(generatedPlans);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("AI 플랜 생성 중 오류 발생: " + e.getMessage());
	    }
	}


	// AI 응답을 JSON으로 변환하는 메서드
	private List<Map<String, Object>> parseAIResponseToPlans(String aiResponse) {
	    List<Map<String, Object>> plans = new ArrayList<>();

	    try {
	        // JSON 문자열을 파싱하여 Map<String, Object>로 변환
	        ObjectMapper objectMapper = new ObjectMapper();
	        Map<String, Object> parsedResponse = objectMapper.readValue(aiResponse, Map.class);

	        // Plan A, Plan B, Plan C 등 플랜 데이터 파싱
	        List<String> planKeys = Arrays.asList("Plan A", "Plan B", "Plan C");
	        
	        for (String planKey : planKeys) {
	            if (parsedResponse.containsKey(planKey)) {
	                Map<String, Object> planData = (Map<String, Object>) parsedResponse.get(planKey);
	                plans.add(planData);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return plans;
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
