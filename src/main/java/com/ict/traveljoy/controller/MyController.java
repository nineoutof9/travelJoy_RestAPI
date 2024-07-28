package com.ict.traveljoy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class MyController {
    
    @GetMapping("/world")
    public String hello() {
        return "hello, world";
    }
    
    @PostMapping("/echo")
    public String echo(@RequestBody Map<String, String> map) {
        return "Received:" + map.get("message");
    }
    
}
