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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ict.traveljoy.controller.CheckContainsUseremail;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/st")
@RequiredArgsConstructor
@Tag(name="STT, TTS 관련 API", description = "STT, TTS Open API관련 컨트롤러")
public class STTController {
	
	private final CheckContainsUseremail checkUser;

	//음성파일 업로드 되는지 확인하기. 파일 저장 물어보기(서정덕)
	@PostMapping(value="/stt", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> speechToText(HttpServletRequest request, @RequestPart List<MultipartFile> files){
		
		String useremail = checkUser.checkContainsUseremail(request);
		
		System.out.println("----NaverClova STT----");
		
		String clientId = "client_id"; // 공유금지
		String clientSecret = "client_secret";
		
		String filedir = "파일 경로";
		File voiceFile = new File(filedir);
		
		String language = "Kor";
		String apiURL = "https://naveropenapi.apigw-pub.fin-ntruss.com/recog/v1/stt?lang=" + language;
        try {
			URL url = new URL(apiURL);
			
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/octet-stream");
            conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            conn.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
            OutputStream outputStream = conn.getOutputStream();
            FileInputStream inputStream = new FileInputStream(voiceFile);
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            inputStream.close();
            BufferedReader br = null;
            int responseCode = conn.getResponseCode();
            if(responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {  // 오류 발생
                System.out.println("error!! responseCode= " + responseCode);
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            }
            String inputLine;

            if(br != null) {
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                System.out.println(response.toString());
                return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE,"application/json").body(response.toString());
            } else {
            	return ResponseEntity.status(500).body("br이 null이다카이");
            }
            
            
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).body(e.toString());
        }
		
		

		
	}
}
