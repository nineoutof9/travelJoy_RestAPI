package com.ict.traveljoy.pushalarm.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;


public class AlarmPublish {
	
	public boolean sendAlarm(String useremail,String title, String content) {
		
		String url = "http://localhost:8000/alarms";
		RestTemplate restTemplate = new RestTemplate();
		
		String topic = useremail.substring(0, useremail.indexOf('@')+1);
		String sender = "ADMIN"; //"SYSTEM
		String receiver = useremail;
		
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
		
        Map<String,String> requestBody = new HashMap<>();
        requestBody.put("topic", topic);
        requestBody.put("message", message);
		
		return true;
	}

}
