package com.ict.traveljoy.controller.tripreview;

import com.ict.traveljoy.tripReview.service.TripReviewService;
import com.ict.traveljoy.tripReview.service.TripReviewDTO;
import com.ict.traveljoy.tripReview.service.TripReviewPhotoDTO;
import com.ict.traveljoy.tripReview.service.TripReviewPhotoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="TripReviewPage", description = "여행 리뷰 페이지")
@RestController
@RequestMapping("/api/reviewList")
public class TripReviewController {

    private final TripReviewService tripReviewService;
    private final TripReviewPhotoService tripReviewPhotoService;

    @Autowired
    public TripReviewController(TripReviewService tripReviewService, TripReviewPhotoService tripReviewPhotoService) {
        this.tripReviewService = tripReviewService;
        this.tripReviewPhotoService = tripReviewPhotoService;
    }

    // 리뷰 생성
    @PostMapping("/createReview")
    public ResponseEntity<TripReviewDTO> createReview(@RequestBody TripReviewDTO TripReviewDTO) {
        try {
            TripReviewDTO createdReview = tripReviewService.createReview(TripReviewDTO);
            return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid data provided: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 모든 리뷰 조회
    @GetMapping("/all")
    public ResponseEntity<List<TripReviewDTO>> getAllReviews() {
        try {
            List<TripReviewDTO> reviews = tripReviewService.getAllReviews();
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 특정 ID로 리뷰 조회
    @GetMapping("/{id}")
    public ResponseEntity<TripReviewDTO> getReviewById(@PathVariable("id") Long id) {
        try {
            TripReviewDTO review = tripReviewService.getReviewById(id);
            return review != null ? new ResponseEntity<>(review, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 리뷰 수정
    @PutMapping("/editReview/{id}")
    public ResponseEntity<TripReviewDTO> updateReview(@PathVariable("id") Long id, @RequestBody TripReviewDTO TripReviewDTO) {
        try {
            TripReviewDTO updatedReview = tripReviewService.updateReview(id, TripReviewDTO);
            return updatedReview != null ? new ResponseEntity<>(updatedReview, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 리뷰 삭제
    @DeleteMapping("/editReview/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable("id") Long id) {
        try {
            tripReviewService.deleteReview(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            System.err.println("Error deleting review: " + e.getMessage()); 
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 작성자 이름으로 리뷰 조회
    @GetMapping("/byWriter/{writer}")
    public ResponseEntity<List<TripReviewDTO>> getReviewsByWriter(@PathVariable("writer") String writer) {
        try {
            List<TripReviewDTO> reviews = tripReviewService.getReviewsByWriter(writer);
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 계획 ID로 리뷰 조회
    @GetMapping("/byPlanId/{planId}")
    public ResponseEntity<List<TripReviewDTO>> getReviewsByPlanId(@PathVariable("planId") Long planId) {
        try {
            List<TripReviewDTO> reviews = tripReviewService.getReviewsByPlanId(planId);
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 제목에 특정 문자열이 포함된 리뷰 조회
    @GetMapping("/byTitle/{title}")
    public ResponseEntity<List<TripReviewDTO>> getReviewsByTitleContaining(@PathVariable("title") String title) {
        try {
            List<TripReviewDTO> reviews = tripReviewService.getReviewsByTitleContaining(title);
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
