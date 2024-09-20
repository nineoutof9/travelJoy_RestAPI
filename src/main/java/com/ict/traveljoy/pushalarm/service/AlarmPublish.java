package com.ict.traveljoy.pushalarm.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


public class AlarmPublish {
	
	public boolean sendAlarm(String senderemail, String receiveremail,String title,LocalDateTime sendDate) {
		
		String url = "http://localhost:8000/alarms";
		RestTemplate restTemplate = new RestTemplate();
		
		String topic = receiveremail.substring(0, receiveremail.indexOf('@'));
		String sender = "ADMIN"; //"SYSTEM
		String receiver = receiveremail;
		
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
		
        //topic, message
        Map<String,Object> requestBody = new HashMap<>();

        requestBody.put("topic", topic);
        
        //sender, recipient, content
        Map<String,Object> message = new HashMap<>();
        message.put("sender",sender);
        message.put("recipient", receiveremail);
        
        // title, sendDate
        Map<String, String> content = new HashMap<>();
        
        content.put("title", title);
        content.put("sendDate", sendDate.toString());
        
        System.out.println("sender,recipient,title,sendDate:"+senderemail+receiveremail+title+sendDate.toString());
        
        message.put("content", content);
        
        requestBody.put("message", message);
        
        //HttpEntity 객체에 요청 데이터 및 헤더추가
        HttpEntity<Map<String,Object>> request = new HttpEntity<>(requestBody,headers);
        
        //Post 요청 전송 및 응답 처리
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        if(response.getStatusCode().is2xxSuccessful()) {
        	System.out.println("성공이당당당숭구리당당 "+response.getBody());
        }
        else {
        	System.out.println("ㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠ"+response.getStatusCode());
        }
		
		return true;
	}

}

//		sender: str
//	    recipient: str
//      content: str