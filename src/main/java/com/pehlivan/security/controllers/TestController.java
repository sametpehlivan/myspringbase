package com.pehlivan.security.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class TestController {
    @GetMapping("/test")
    @PreAuthorize("hasAuthority('TEST:SAASDFK')")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok().body("test");
    }
    @GetMapping("/test2")
    public ResponseEntity<String> test2(){
        return ResponseEntity.ok().body("test");
    }
    @GetMapping("/test3")
    public ResponseEntity<String> test4(){
        return ResponseEntity.ok().body("test");
    }
}
