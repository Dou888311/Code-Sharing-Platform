package com.dou888311.codesharing.controller;

import com.dou888311.codesharing.entity.CodeBody;
import com.dou888311.codesharing.service.APIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class APIController {

    @Autowired
    APIService apiService;

    @PostMapping("/api/code/new")
    public Map<String, String> postNewCode(@RequestBody CodeBody newCode) {
        return apiService.postNew(newCode);
    }

    @GetMapping("/api/code/{n}")
    public ResponseEntity<CodeBody> getCode(@PathVariable String n) {
        return apiService.getCode(n);
    }

    @GetMapping("/api/code/latest")
    public List<CodeBody> getLatest() {
        return apiService.getLatest();
    }
}