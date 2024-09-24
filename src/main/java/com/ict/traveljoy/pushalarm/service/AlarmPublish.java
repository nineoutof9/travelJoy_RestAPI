package com.ict.traveljoy.pushalarm.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class AlarmPublish {
	

	public boolean sendAlarm(String senderemail, String[] emailsToSend,String title,LocalDateTime sendDate) {

		
		String url = "http://localhost:8000/alarms";
		RestTemplate restTemplate = new RestTemplate();
		
		List<String> topic = new ArrayList<String>();
		for(String email:emailsToSend) {
			topic.add(email.substring(0,email.indexOf('@')));
		}
		String sender = "ADMIN"; //"SYSTEM
		String[] receiver = emailsToSend;

		
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
		
        //topic, message
        Map<String,Object> requestBody = new HashMap<>();

        requestBody.put("topic", topic);
        
        //sender, recipient, content
        Map<String,Object> message = new HashMap<>();
        message.put("sender",sender);
        message.put("recipient", emailsToSend);

        
        // title, sendDate
        Map<String, String> content = new HashMap<>();
        
        content.put("title", title);
        content.put("sendDate", sendDate.toString());

        message.put("content", content);
        
        requestBody.put("message", message);
        
        //HttpEntity 객체에 요청 데이터 및 헤더추가
        HttpEntity<Map<String,Object>> request = new HttpEntity<>(requestBody,headers);
        
        //Post 요청 전송 및 응답 처리
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        if(response.getStatusCode().is2xxSuccessful()) {
        	System.out.println(response.getBody());
        }
        else {
        	System.out.println(response.getStatusCode());

        }
		
		return true;
	}
	
	public void scheduleAlarm(String senderemail, String[] emailsToSend, String title, LocalDateTime sendDate) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                sendAlarm(senderemail, emailsToSend, title, sendDate);
                timer.cancel();
            }
        };

        // 현재 시간과 예약 시간의 차이를 계산하여 타이머 설정
        LocalDateTime now = LocalDateTime.now();
        long delay = Duration.between(now, sendDate).toMillis();

        // 타이머에 작업 예약 (한 번만 실행)
        timer.schedule(task, delay);

        System.out.println("Alarm scheduled for: " + sendDate.toString());
    }

}