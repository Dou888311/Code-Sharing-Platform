package com.dou888311.codesharing.service;

import com.dou888311.codesharing.entity.CodeBody;
import com.dou888311.codesharing.repository.CodeBodyRepository;
import com.dou888311.codesharing.support.LocalDateTimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class APIService {

    @Autowired
    CodeBodyRepository codeRepository;

    public ResponseEntity<CodeBody> getCode(String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        CodeBody temp = codeRepository.findCodeBodyById(id);
        if (!temp.isAlwaysVisible()) {
            if (temp.getTime() != 0) {
                LocalDateTime timeOfCreation = LocalDateTimeConverter.reverseConvert(temp.getDate());
                temp.setTime(temp.getTime() - ChronoUnit.SECONDS.between(timeOfCreation, LocalDateTime.now()));
            }
            if (temp.isViewsLimited()) {
                temp.setViews(temp.getViews() - 1);
            }
            if (temp.getTime() < 0 | (temp.isViewsLimited() && temp.getViews() < 0)) {
                codeRepository.delete(temp);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            codeRepository.save(temp);
        }
        return ResponseEntity.ok()
                .headers(headers)
                .body(temp);
    }

    public List<CodeBody> getLatest() {
        return codeRepository.findAll().stream()
                .filter(CodeBody::isAlwaysVisible)
                .sorted((x, y) -> (int) Duration.between(x.getCreationTime(), y.getCreationTime()).toMillis())
                .limit(10)
                .collect(Collectors.toList());
    }

    public Map<String, String> postNew(CodeBody newCode) {
        CodeBody temp = new CodeBody();
        temp.setCode(newCode.getCode());
        temp.setDate(LocalDateTimeConverter.convert(LocalDateTime.now()));
        temp.setId(UUID.randomUUID().toString());
        temp.setTime(newCode.getTime());
        temp.setViews(newCode.getViews());
        temp.setCreationTime(LocalDateTime.now());
        if (temp.getViews() > 0) temp.setViewsLimited(true);
        if (temp.getTime() < 0) temp.setTime(0);
        if (temp.getViews() < 0) temp.setViews(0);
        if (temp.getTime() == 0 && temp.getViews() == 0) temp.setAlwaysVisible(true);
        codeRepository.save(temp);
        return Map.of("id", String.valueOf(temp.getId()));
    }
}
