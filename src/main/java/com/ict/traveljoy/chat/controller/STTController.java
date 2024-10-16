package com.ict.traveljoy.chat.controller;

import java.io.IOException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@Tag(name = "STT 관련 API", description = "STT Open API관련 컨트롤러")
public class STTController {

	private final CheckContainsUseremail checkUser;
	
	@Value("${stt-api-key.clientId}")
	private String clientId;
	@Value("${stt-api-key.clientSecret}")
    private String clientSecret;

	@PostMapping(value = "/record", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> speechToText(HttpServletRequest request, @RequestParam("file") MultipartFile fileData) {

		String useremail = checkUser.checkContainsUseremail(request);

		// 파일 데이터가 제대로 전송되었는지 확인
		if (fileData != null || fileData.getSize() != 0) {
			try {
				// 파일의 이름과 크기 등을 로그로 출력
				System.out.println("Received file - File size: " + fileData.getSize() + " bytes");

			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		} else {
			return ResponseEntity.badRequest().build();
		}

		// 파일 데이터를 처리하는 로직

		try {
			String imgFile = "음성 파일 경로";
			File convertFile = new File(fileData.getOriginalFilename());
			convertFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(convertFile);
			fos.write(fileData.getBytes());
			fos.close();
			
			File voiceFile = convertFile;

			String language = "Kor"; // 언어 코드 ( Kor, Jpn, Eng, Chn )
			String apiURL = "https://naveropenapi.apigw.ntruss.com/recog/v1/stt?lang=" + language;
			URL url = new URL(apiURL);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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
			if (responseCode == 200) { // 정상 호출
				br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else { // 오류 발생
				System.out.println("error!!!!!!! responseCode= " + responseCode);
				br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			}
			String inputLine;

			if (br != null) {
				StringBuffer response = new StringBuffer();
				while ((inputLine = br.readLine()) != null) {
					response.append(inputLine);
				}
				br.close();
				return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE, "application/json")
						.body(response.toString());
			} else {
				System.out.println("error !!!");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return ResponseEntity.status(500).body(null);
	}

	// -------------- upload --------------
	@PostMapping("/upload")
	public ResponseEntity<String> handleFileUpload(@RequestPart("file") MultipartFile incomingFile) {

		// 파일이 정상적으로 수신되었는지 확인
		if (!incomingFile.isEmpty()) {
			try {
				// 파일의 이름과 크기 등을 로그로 출력
				System.out.println("Received file: " + incomingFile.getOriginalFilename() + ", " + "File size: "
						+ incomingFile.getSize() + " bytes");

			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		} else {
			return ResponseEntity.badRequest().build();
		}

		try {
			File voiceFile = new File(System.getProperty("java.io.tmpdir") + "/" + incomingFile.getOriginalFilename());
			incomingFile.transferTo(voiceFile);

			String language = "Kor"; // 언어 코드 ( Kor, Jpn, Eng, Chn )
			String apiURL = "https://naveropenapi.apigw.ntruss.com/recog/v1/stt?lang=" + language;
			URL url = new URL(apiURL);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

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
			if (responseCode == 200) { // 정상 호출
				br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else { // 오류 발생
				System.out.println("error!!!!!!! responseCode= " + responseCode);
				br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			}
			String inputLine;

			if (br != null) {
				StringBuffer response = new StringBuffer();
				while ((inputLine = br.readLine()) != null) {
					response.append(inputLine);
				}
				br.close();
				return ResponseEntity.status(200).header(HttpHeaders.CONTENT_TYPE, "application/json")
						.body(response.toString());
			} else {
				System.out.println("error !!!");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return ResponseEntity.status(500).body(null);

	}
}
