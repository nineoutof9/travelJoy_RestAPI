package com.ict.traveljoy.service.tripReview;

import com.ict.traveljoy.repository.image.Image;
import com.ict.traveljoy.repository.image.ImageRepository;
import com.ict.traveljoy.repository.tripReview.TripReview;
import com.ict.traveljoy.repository.tripReview.TripReviewPhoto;
import com.ict.traveljoy.repository.tripReview.TripReviewPhotoRepository;
import com.ict.traveljoy.repository.tripReview.TripReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TripReviewPhotoService {

    private final TripReviewPhotoRepository tripReviewPhotoRepository;
    private final TripReviewRepository tripReviewRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public TripReviewPhotoService(
            TripReviewPhotoRepository tripReviewPhotoRepository,
            TripReviewRepository tripReviewRepository,
            ImageRepository imageRepository
    ) {
        this.tripReviewPhotoRepository = tripReviewPhotoRepository;
        this.tripReviewRepository = tripReviewRepository;
        this.imageRepository = imageRepository;
    }

    // 특정 TripReview ID로 모든 TripReviewPhoto 목록 조회
    public List<TripReviewPhotoDto> getTripReviewPhotosByTripReviewId(Long tripReviewId) {
        List<TripReviewPhoto> tripReviewPhotos = tripReviewPhotoRepository.findByTripReview_TripReviewId(tripReviewId);
        return tripReviewPhotos.stream()
                .map(TripReviewPhotoDto::toDto)
                .collect(Collectors.toList());
    }

    // 특정 Image ID로 모든 TripReviewPhoto 목록 조회
    public List<TripReviewPhotoDto> getTripReviewPhotosByImageId(Long imageId) {
        List<TripReviewPhoto> tripReviewPhotos = tripReviewPhotoRepository.findByImage_ImageId(imageId);
        return tripReviewPhotos.stream()
                .map(TripReviewPhotoDto::toDto)
                .collect(Collectors.toList());
    }

    // 특정 TripReview와 Image로 TripReviewPhoto 조회
    public TripReviewPhotoDto getTripReviewPhotoByTripReviewIdAndImageId(Long tripReviewId, Long imageId) {
        Optional<TripReviewPhoto> tripReviewPhotoOptional = tripReviewPhotoRepository.findByTripReview_TripReviewIdAndImage_ImageId(tripReviewId, imageId);
        return tripReviewPhotoOptional.map(TripReviewPhotoDto::toDto).orElse(null);
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
        TripReviewPhoto tripReviewPhoto = tripReviewPhotoRepository.findByTripReview_TripReviewIdAndImage_ImageId(tripReviewId, imageId)
                .orElseThrow(() -> new RuntimeException("TripReviewPhoto not found"));

        tripReviewPhotoRepository.delete(tripReviewPhoto);
    }

    // 특정 TripReviewId에 해당하는 모든 TripReviewPhoto 삭제
    public void deleteAllTripReviewPhotosByTripReviewId(Long tripReviewId) {
        List<TripReviewPhoto> tripReviewPhotos = tripReviewPhotoRepository.findByTripReview_TripReviewId(tripReviewId);
        if (!tripReviewPhotos.isEmpty()) {
            tripReviewPhotoRepository.deleteAll(tripReviewPhotos);
        } else {
            throw new RuntimeException("No TripReviewPhotos found for the given TripReviewId");
        }
    }
}
