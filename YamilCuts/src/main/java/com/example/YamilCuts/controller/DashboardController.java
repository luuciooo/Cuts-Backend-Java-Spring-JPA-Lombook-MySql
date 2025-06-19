package com.example.YamilCuts.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @GetMapping
    public ResponseEntity<Integer> verEstado() {
        return ResponseEntity.ok(2000);
    }
}

