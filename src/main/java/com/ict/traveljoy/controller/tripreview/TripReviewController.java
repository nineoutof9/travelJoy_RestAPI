package com.ict.traveljoy.controller.tripreview;

import com.ict.traveljoy.tripReview.service.TripReviewService;
import com.ict.traveljoy.image.service.ImageDTO;
import com.ict.traveljoy.image.service.ImageService;
import com.ict.traveljoy.tripReview.service.TripReviewDTO;
import com.ict.traveljoy.tripReview.service.TripReviewPhotoDTO;
import com.ict.traveljoy.tripReview.service.TripReviewPhotoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name="TripReviewPage", description = "여행 리뷰 페이지")
@RestController
@RequestMapping("/api/reviewList")
public class TripReviewController {

    private final TripReviewService tripReviewService;
    private final TripReviewPhotoService tripReviewPhotoService;
    private final ImageService imageService;

    @Autowired
    public TripReviewController(TripReviewService tripReviewService, TripReviewPhotoService tripReviewPhotoService, ImageService imageService) {
        this.tripReviewService = tripReviewService;
        this.tripReviewPhotoService = tripReviewPhotoService;
        this.imageService = imageService;
    }

    // 리뷰 생성
    @PostMapping("/createReview")
    public ResponseEntity<TripReviewDTO> createReview(
            @RequestPart("tripReviewDTO") TripReviewDTO tripReviewDTO,
            @RequestPart(value = "files", required = false) MultipartFile[] files) {  // 파일은 필수 아님

        // 리뷰 생성 로직 호출
    	System.out.println("planID:"+tripReviewDTO.getPlanId());
        TripReviewDTO createdReview = tripReviewService.createReview(tripReviewDTO);
        
        try {
            // 파일이 있을 경우에만 파일 업로드 처리
            if (files != null && files.length > 0) {
                for (MultipartFile file : files) {
                    if (!file.isEmpty()) {
                        // 1. 이미지 저장
                        String dirName = "review-images";  // 이미지가 저장될 디렉토리 이름 설정
                        ImageDTO imageDTO = new ImageDTO();  // 새로운 ImageDTO 객체 생성

                        // ImageService를 사용해 이미지 업로드 및 URL 생성
                        ImageDTO savedImageDTO = imageService.postImage(imageDTO, dirName, file);

                        // 2. 저장된 이미지를 리뷰에 추가
                        TripReviewPhotoDTO tripReviewPhotoDTO = new TripReviewPhotoDTO();
                        tripReviewPhotoDTO.setImage(savedImageDTO.toEntity());  // 저장된 이미지 설정

                        tripReviewPhotoService.addPhotoToReview(createdReview.getId(), tripReviewPhotoDTO);
                    }
                }
            }

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
