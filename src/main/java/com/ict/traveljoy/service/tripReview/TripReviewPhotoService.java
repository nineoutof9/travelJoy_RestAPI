package com.ict.traveljoy.service.tripReview;

import com.ict.traveljoy.repository.tripReview.TripReview;
import com.ict.traveljoy.repository.image.Image;
import com.ict.traveljoy.repository.tripReview.TripReviewPhoto;
import com.ict.traveljoy.repository.tripReview.TripReviewPhotoRepository;
import com.ict.traveljoy.repository.tripReview.TripReviewRepository;
import com.ict.traveljoy.repository.image.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TripReviewPhotoService {

    private final TripReviewPhotoRepository tripReviewPhotoRepository;
    private final TripReviewRepository tripReviewRepository;  // 추가: TripReviewRepository
    private final ImageRepository imageRepository;  // 추가: ImageRepository

    @Autowired
    public TripReviewPhotoService(
            TripReviewPhotoRepository tripReviewPhotoRepository,
            TripReviewRepository tripReviewRepository,  // 추가: TripReviewRepository 주입
            ImageRepository imageRepository  // 추가: ImageRepository 주입
    ) {
        this.tripReviewPhotoRepository = tripReviewPhotoRepository;
        this.tripReviewRepository = tripReviewRepository;  // 초기화
        this.imageRepository = imageRepository;  // 초기화
    }

    // TripReviewId로 TripReviewPhoto 목록 조회
    public List<TripReviewPhotoDto> getTripReviewPhotosByTripReviewId(Long tripReviewId) {
        List<TripReviewPhoto> tripReviewPhotos = tripReviewPhotoRepository.findByTripReviewId(tripReviewId);
        return tripReviewPhotos.stream()
                .map(TripReviewPhotoDto::toDto)
                .collect(Collectors.toList());
    }

    // ImageId로 TripReviewPhoto 목록 조회
    public List<TripReviewPhotoDto> getTripReviewPhotosByImageId(Long imageId) {
        List<TripReviewPhoto> tripReviewPhotos = tripReviewPhotoRepository.findByImageId(imageId);
        return tripReviewPhotos.stream()
                .map(TripReviewPhotoDto::toDto)
                .collect(Collectors.toList());
    }

    // TripReviewId와 ImageId로 TripReviewPhoto 조회
    public TripReviewPhotoDto getTripReviewPhotoByTripReviewIdAndImageId(Long tripReviewId, Long imageId) {
        TripReviewPhoto tripReviewPhoto = tripReviewPhotoRepository.findByTripReviewIdAndImageId(tripReviewId, imageId);
        return tripReviewPhoto != null ? TripReviewPhotoDto.toDto(tripReviewPhoto) : null;
    }

    // TripReviewPhoto 저장
    public TripReviewPhotoDto saveTripReviewPhoto(TripReviewPhotoDto tripReviewPhotoDto) {
        TripReview tripReview = tripReviewRepository.findById(tripReviewPhotoDto.getTripReviewId())
                .orElseThrow(() -> new RuntimeException("TripReview not found"));
        Image image = imageRepository.findById(tripReviewPhotoDto.getImageId())
                .orElseThrow(() -> new RuntimeException("Image not found"));

        TripReviewPhoto tripReviewPhoto = tripReviewPhotoDto.toEntity();
        tripReviewPhoto.setTripReview(tripReview);
        tripReviewPhoto.setImage(image);

        TripReviewPhoto savedTripReviewPhoto = tripReviewPhotoRepository.save(tripReviewPhoto);
        return TripReviewPhotoDto.toDto(savedTripReviewPhoto);
    }

    // TripReviewPhoto 수정
    public TripReviewPhotoDto updateTripReviewPhoto(TripReviewPhotoDto tripReviewPhotoDto) {
        TripReviewPhoto existingTripReviewPhoto = tripReviewPhotoRepository.findById(tripReviewPhotoDto.getTripReviewPhotoId())
                .orElseThrow(() -> new RuntimeException("TripReviewPhoto not found"));

        TripReview tripReview = tripReviewRepository.findById(tripReviewPhotoDto.getTripReviewId())
                .orElseThrow(() -> new RuntimeException("TripReview not found"));
        Image image = imageRepository.findById(tripReviewPhotoDto.getImageId())
                .orElseThrow(() -> new RuntimeException("Image not found"));

        existingTripReviewPhoto.setTripReview(tripReview);
        existingTripReviewPhoto.setImage(image);

        TripReviewPhoto updatedTripReviewPhoto = tripReviewPhotoRepository.save(existingTripReviewPhoto);
        return TripReviewPhotoDto.toDto(updatedTripReviewPhoto);
    }

    // TripReviewPhoto 삭제 (TripReviewId와 ImageId로 조회)
    public void deleteTripReviewPhoto(Long tripReviewId, Long imageId) {
        tripReviewPhotoRepository.deleteByTripReviewIdAndImageId(tripReviewId, imageId);
    }

    // 특정 TripReviewId에 해당하는 모든 TripReviewPhoto 삭제
    public void deleteAllTripReviewPhotosByTripReviewId(Long tripReviewId) {
        tripReviewPhotoRepository.deleteByTripReviewId(tripReviewId);
    }
}
