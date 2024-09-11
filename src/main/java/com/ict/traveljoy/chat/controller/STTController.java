package com.ict.traveljoy.chat.controller;

import java.io.IOException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ict.traveljoy.controller.CheckContainsUseremail;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stt")
@RequiredArgsConstructor
@Tag(name="STT, TTS 관련 API", description = "STT, TTS Open API관련 컨트롤러")
public class STTController {
	
	private final CheckContainsUseremail checkUser;

	//음성파일 업로드 되는지 확인하기. 파일 저장 물어보기(서정덕)
	@PostMapping(value = "/record", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> speechToText(HttpServletRequest request, @RequestPart("file") MultipartFile file) {
	    String useremail = checkUser.checkContainsUseremail(request);

	    System.out.println("----NaverClova STT record----");

	    String clientId = ""; // 공유금지
	    String clientSecret = "";

	    String language = "Kor";
	    String apiURL = "https://naveropenapi.apigw-pub.fin-ntruss.com/recog/v1/stt?lang=" + language;

	    try {
	        File voiceFile = File.createTempFile("voice", ".tmp");
	        try (FileOutputStream fos = new FileOutputStream(voiceFile)) {
	            fos.write(file.getBytes());  // MultipartFile을 사용해 파일 데이터를 받음
	            System.out.println("----file uploaded");
	        }

	        
	        URL url = new URL(apiURL);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setUseCaches(false);
	        conn.setDoOutput(true);
	        conn.setDoInput(true);
	        conn.setRequestProperty("Content-Type", "application/octet-stream");
	        conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
	        conn.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);

	        try (OutputStream outputStream = conn.getOutputStream();
	             FileInputStream inputStream = new FileInputStream(voiceFile)) {
	            byte[] buffer = new byte[4096];
	            int bytesRead;
	            while ((bytesRead = inputStream.read(buffer)) != -1) {
	                outputStream.write(buffer, 0, bytesRead);
	            }
	            outputStream.flush();
	            System.out.println("----hmmmmm");
	        }

	        BufferedReader br = null;
	        int responseCode = conn.getResponseCode();
	        if (responseCode == 200) {
	            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        } else {
	            br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	        }

	        StringBuilder response = new StringBuilder();
	        String inputLine;
	        while ((inputLine = br.readLine()) != null) {
	            response.append(inputLine);
	        }
	        System.out.println(response);

	        return ResponseEntity.status(200).body(response.toString());
	    } catch (Exception e) {
	        return ResponseEntity.status(500).body(e.getMessage());
	    }
	}
	
	@PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestPart("file") MultipartFile file) {

		System.out.println("----NaverClova STT upload----");
		
		try {
            // Naver Clova CSR API로 파일 전송 로직을 여기에 작성
            String language = "Kor";
            String clientId = ""; // 공유금지
    		String clientSecret = "";
    		
            String apiURL = "https://naveropenapi.apigw-pub.fin-ntruss.com/recog/v1/stt?lang=" + language;

            System.out.println("Uploaded file: " + file.getOriginalFilename());
            
            URL url = new URL(apiURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/octet-stream");
            conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            conn.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);

            // 파일 데이터를 Clova API로 전송
            try (OutputStream outputStream = conn.getOutputStream()) {
                outputStream.write(file.getBytes());
            }

            int responseCode = conn.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {  // 오류 발생
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE, "application/json").body(response.toString());

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
