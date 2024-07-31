package com.ict.traveljoy.controller.tripReview;

import com.ict.traveljoy.service.tripReview.TripReviewDto;
import com.ict.traveljoy.service.tripReview.TripReviewPhotoDto;
import com.ict.traveljoy.service.tripReview.TripReviewPhotoService;
import com.ict.traveljoy.service.tripReview.TripReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trip-reviews")
public class TripReviewController {

    private final TripReviewService tripReviewService;
    private final TripReviewPhotoService tripReviewPhotoService;

    @Autowired
    public TripReviewController(TripReviewService tripReviewService, TripReviewPhotoService tripReviewPhotoService) {
        this.tripReviewService = tripReviewService;
        this.tripReviewPhotoService = tripReviewPhotoService;
    }

    // TripReview 저장
    @PostMapping
    public ResponseEntity<TripReviewDto> createTripReview(@RequestBody TripReviewDto tripReviewDto) {
        TripReviewDto savedTripReview = tripReviewService.saveTripReview(tripReviewDto);
        return ResponseEntity.ok(savedTripReview);
    }

    // TripReview 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<TripReviewDto> updateTripReview(@PathVariable("id") Long tripReviewId, @RequestBody TripReviewDto tripReviewDto) {
        TripReviewDto updatedTripReview = tripReviewService.updateTripReview(tripReviewId, tripReviewDto);
        return ResponseEntity.ok(updatedTripReview);
    }

    // TripReview 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTripReview(@PathVariable("id") Long tripReviewId) {
        tripReviewService.deleteTripReview(tripReviewId);
        return ResponseEntity.noContent().build();
    }

    // TripReview 조회
    @GetMapping("/{id}")
    public ResponseEntity<TripReviewDto> getTripReview(@PathVariable("id") Long tripReviewId) {
        TripReviewDto tripReviewDto = tripReviewService.getTripReview(tripReviewId);
        return ResponseEntity.ok(tripReviewDto);
    }

    // 모든 TripReview 조회
    @GetMapping
    public ResponseEntity<List<TripReviewDto>> getAllTripReviews() {
        List<TripReviewDto> tripReviews = tripReviewService.getAllTripReviews();
        return ResponseEntity.ok(tripReviews);
    }

    // 특정 Writer의 TripReview 조회
    @GetMapping("/writer/{writer}")
    public ResponseEntity<List<TripReviewDto>> getTripReviewsByWriter(@PathVariable("writer") String writer) {
        List<TripReviewDto> tripReviews = tripReviewService.getTripReviewsByWriter(writer);
        return ResponseEntity.ok(tripReviews);
    }

    // 특정 Plan ID로 TripReview 조회
    @GetMapping("/plan/{planId}")
    public ResponseEntity<List<TripReviewDto>> getTripReviewsByPlanId(@PathVariable("planId") Long planId) {
        List<TripReviewDto> tripReviews = tripReviewService.getTripReviewsByPlanId(planId);
        return ResponseEntity.ok(tripReviews);
    }

    // 제목에 특정 문자열이 포함된 TripReview 조회
    @GetMapping("/title/{title}")
    public ResponseEntity<List<TripReviewDto>> getTripReviewsByTitleContaining(@PathVariable("title") String title) {
        List<TripReviewDto> tripReviews = tripReviewService.getTripReviewsByTitleContaining(title);
        return ResponseEntity.ok(tripReviews);
    }

    // TripReviewPhoto 저장
    @PostMapping("/{tripReviewId}/photos")
    public ResponseEntity<TripReviewPhotoDto> createTripReviewPhoto(@PathVariable("tripReviewId") Long tripReviewId, @RequestBody TripReviewPhotoDto tripReviewPhotoDto) {
        tripReviewPhotoDto.setTripReviewId(tripReviewId);
        TripReviewPhotoDto savedTripReviewPhoto = tripReviewPhotoService.saveTripReviewPhoto(tripReviewPhotoDto);
        return ResponseEntity.ok(savedTripReviewPhoto);
    }

    // 특정 TripReviewId에 해당하는 모든 TripReviewPhoto 삭제
    @DeleteMapping("/{tripReviewId}/photos")
    public ResponseEntity<Void> deleteAllTripReviewPhotosByTripReviewId(@PathVariable("tripReviewId") Long tripReviewId) {
        tripReviewPhotoService.deleteAllTripReviewPhotosByTripReviewId(tripReviewId);
        return ResponseEntity.noContent().build();
    }
}
