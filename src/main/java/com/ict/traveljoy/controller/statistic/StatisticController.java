package com.ict.traveljoy.controller.statistic;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "통계 관리", description = "통계 Rest Api 컨트롤러입니다")
@RestController
@RequestMapping("/api/statistics")
public class StatisticController {

    private Map<String, Long> statistics = new HashMap<>();

    public StatisticController() {
        // 초기 통계 데이터 설정
        statistics.put("userCount", 1234L);
        statistics.put("tripCount", 567L);
    }

    @CrossOrigin
    @GetMapping("/user-count")
    @Operation(summary = "유저 수 조회하기", description = "유저 수 조회 컨트롤러입니다")
    public ResponseEntity<Long> getUserCount() {
        try {
            Long userCount = statistics.getOrDefault("userCount", 0L);
            return ResponseEntity.ok(userCount);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @CrossOrigin
    @GetMapping("/trip-count")
    @Operation(summary = "여행 수 조회하기", description = "여행 수 조회 컨트롤러입니다")
    public ResponseEntity<Long> getTripCount() {
        try {
            Long tripCount = statistics.getOrDefault("tripCount", 0L);
            return ResponseEntity.ok(tripCount);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @CrossOrigin
    @PutMapping("/update-user-count")
    @Operation(summary = "유저 수 수정하기", description = "유저 수 수정 컨트롤러입니다")
    public ResponseEntity<String> updateUserCount(@RequestBody Map<String, Long> payload) {
        try {
            Long count = payload.get("count");
            statistics.put("userCount", count);
            return ResponseEntity.ok("유저 수가 " + count + "(으)로 수정되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("유저 수 수정 중 오류가 발생했습니다.");
        }
    }

    @CrossOrigin
    @PutMapping("/update-trip-count")
    @Operation(summary = "여행 수 수정하기", description = "여행 수 수정 컨트롤러입니다")
    public ResponseEntity<String> updateTripCount(@RequestBody Map<String, Long> payload) {
        try {
            Long count = payload.get("count");
            statistics.put("tripCount", count);
            return ResponseEntity.ok("여행 수가 " + count + "(으)로 수정되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("여행 수 수정 중 오류가 발생했습니다.");
        }
    }

    @CrossOrigin
    @DeleteMapping("/delete-user-count")
    @Operation(summary = "유저 수 삭제하기", description = "유저 수 삭제 컨트롤러입니다")
    public ResponseEntity<String> deleteUserCount() {
        try {
            if (statistics.containsKey("userCount")) {
                statistics.remove("userCount");
                return ResponseEntity.ok("유저 수가 삭제되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("유저 수를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("유저 수 삭제 중 오류가 발생했습니다.");
        }
    }

    @CrossOrigin
    @DeleteMapping("/delete-trip-count")
    @Operation(summary = "여행 수 삭제하기", description = "여행 수 삭제 컨트롤러입니다")
    public ResponseEntity<String> deleteTripCount() {
        try {
            if (statistics.containsKey("tripCount")) {
                statistics.remove("tripCount");
                return ResponseEntity.ok("여행 수가 삭제되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("여행 수를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("여행 수 삭제 중 오류가 발생했습니다.");
        }
    }
}
