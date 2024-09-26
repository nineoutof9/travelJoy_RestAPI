package com.ict.traveljoy.image.controller;  // 패키지 이름은 프로젝트 구조에 맞게 수정

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/image")
public class ImagesController {

    // 이미지가 저장될 폴더 경로
    private final Path rootLocation = Paths.get("processed_images");

    public ImagesController() throws IOException {
        Files.createDirectories(rootLocation);  // 디렉토리가 없으면 생성
    }

    // 이미지 업로드 및 처리
    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // 파일 저장을 위한 고유 파일명 생성
            String filename = UUID.randomUUID().toString() + ".jpg";
            Path filePath = this.rootLocation.resolve(filename);
            
            // 파일 저장
            Files.copy(file.getInputStream(), filePath);

            // 이미지 처리 (예: 모자이크 처리)
            BufferedImage originalImage = ImageIO.read(filePath.toFile());
            BufferedImage processedImage = mosaicImage(originalImage);

            // 처리된 이미지 저장
            File outputFile = new File(rootLocation.toString() + "/" + filename);
            ImageIO.write(processedImage, "jpg", outputFile);

            // 처리된 이미지의 URL 반환
            String imageUrl = "/api/image/files/" + filename;
            return ResponseEntity.ok().body("{\"processed_image_url\": \"" + imageUrl + "\"}");

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image processing failed");
        }
    }

    // 이미지 파일 반환 (GET 요청)
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 이미지 모자이크 처리 로직
    private BufferedImage mosaicImage(BufferedImage image) {
        int mosaicSize = 10;  // 모자이크 크기

        for (int y = 0; y < image.getHeight(); y += mosaicSize) {
            for (int x = 0; x < image.getWidth(); x += mosaicSize) {
                // 각 모자이크 블록의 평균 색상 계산
                int pixel = image.getRGB(x, y);

                // 모자이크 블록을 적용
                for (int dy = 0; dy < mosaicSize; dy++) {
                    for (int dx = 0; dx < mosaicSize; dx++) {
                        if (x + dx < image.getWidth() && y + dy < image.getHeight()) {
                            image.setRGB(x + dx, y + dy, pixel);
                        }
                    }
                }
            }
        }
        return image;
    }
}
