package com.ict.traveljoy.tripReview.service;

import com.ict.traveljoy.tripReview.repository.TripReviewPhoto;
import com.ict.traveljoy.tripReview.repository.TripReviewPhotoRepository;
import com.ict.traveljoy.image.repository.Image;
import com.ict.traveljoy.image.repository.ImageRepository;
import com.ict.traveljoy.tripReview.repository.TripReview;
import com.ict.traveljoy.tripReview.repository.TripReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TripReviewPhotoService {

    private final TripReviewPhotoRepository tripReviewPhotoRepository;
    private final ImageRepository imageRepository;
    private final TripReviewRepository tripReviewRepository;

    @Autowired
    public TripReviewPhotoService(TripReviewPhotoRepository tripReviewPhotoRepository, ImageRepository imageRepository, TripReviewRepository tripReviewRepository) {
        this.tripReviewPhotoRepository = tripReviewPhotoRepository;
        this.imageRepository = imageRepository;
        this.tripReviewRepository = tripReviewRepository;
    }

    // 특정 TripReview ID로 모든 TripReviewPhoto 찾기
    public List<TripReviewPhotoDto> getPhotosByTripReviewId(Long tripReviewId) {
        List<TripReviewPhoto> tripReviewPhotos = tripReviewPhotoRepository.findByTripReview_TripReviewId(tripReviewId);
        return tripReviewPhotos.stream()
                .map(TripReviewPhotoDto::toDto)
                .collect(Collectors.toList());
    }

    // 특정 Image ID로 모든 TripReviewPhoto 찾기
    public List<TripReviewPhotoDto> getPhotosByImageId(Long imageId) {
        List<TripReviewPhoto> tripReviewPhotos = tripReviewPhotoRepository.findByImage_Id(imageId);
        return tripReviewPhotos.stream()
                .map(TripReviewPhotoDto::toDto)
                .collect(Collectors.toList());
    }

    // 특정 TripReview와 Image로 TripReviewPhoto 찾기
    public TripReviewPhotoDto getPhotoByTripReviewIdAndImageId(Long tripReviewId, Long imageId) {
        Optional<TripReviewPhoto> tripReviewPhotoOpt = tripReviewPhotoRepository.findByTripReview_TripReviewIdAndImage_Id(tripReviewId, imageId);
        return tripReviewPhotoOpt.map(TripReviewPhotoDto::toDto).orElse(null);
    }

    // TripReviewPhoto ID로 TripReviewPhoto 찾기
    public TripReviewPhotoDto getPhotoById(Long tripReviewPhotoId) {
        Optional<TripReviewPhoto> tripReviewPhotoOpt = tripReviewPhotoRepository.findByTripReviewPhotoId(tripReviewPhotoId);
        return tripReviewPhotoOpt.map(TripReviewPhotoDto::toDto).orElse(null);
    }

    // 새로운 TripReviewPhoto 저장
    /*
    public TripReviewPhotoDTO savePhoto(TripReviewPhotoDto tripReviewPhotoDto) {
        Optional<TripReview> tripReviewOpt = tripReviewRepository.findById(tripReviewPhotoDto.getTripReview());
        Optional<Image> imageOpt = imageRepository.findById(tripReviewPhotoDTO.getImage());

        if (tripReviewOpt.isPresent() && imageOpt.isPresent()) {
            TripReviewPhoto tripReviewPhoto = TripReviewPhoto.builder()
                    .tripReview(tripReviewOpt.get())
                    .image(imageOpt.get())
                    .build();
            TripReviewPhoto savedPhoto = tripReviewPhotoRepository.save(tripReviewPhoto);
            return TripReviewPhotoDTO.toDTO(savedPhoto);
        }

        throw new IllegalArgumentException("TripReview or Image not found");
    }
    */

    // TripReviewPhoto 업데이트
    /*
    public TripReviewPhotoDTO updatePhoto(Long tripReviewPhotoId, TripReviewPhotoDto updatedDto) {
        Optional<TripReviewPhoto> tripReviewPhotoOpt = tripReviewPhotoRepository.findByTripReviewPhotoId(tripReviewPhotoId);
        Optional<Image> newImageOpt = imageRepository.findByImage_Id(updatedDTO.getImage());

        if (tripReviewPhotoOpt.isPresent() && newImageOpt.isPresent()) {
            TripReviewPhoto tripReviewPhoto = tripReviewPhotoOpt.get();
            tripReviewPhoto.setImage(newImageOpt.get());
            TripReviewPhoto updatedPhoto = tripReviewPhotoRepository.save(tripReviewPhoto);
            return TripReviewPhotoDTO.toDTO(updatedPhoto);
        }

        throw new IllegalArgumentException("TripReviewPhoto or Image not found");
    }
    */

    // TripReviewPhoto 삭제
    public void deletePhoto(Long tripReviewPhotoId) {
        tripReviewPhotoRepository.deleteById(tripReviewPhotoId);
    }
}
