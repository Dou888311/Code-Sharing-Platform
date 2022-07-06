package com.dou888311.codesharing.repository;

import com.dou888311.codesharing.entity.CodeBody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeBodyRepository extends JpaRepository<CodeBody, String> {

    CodeBody findCodeBodyById(String id);
}
