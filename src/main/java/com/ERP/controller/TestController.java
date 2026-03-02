package com.ERP.controller;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ERP.entity.ConnectionTest;
import com.ERP.repository.ConnectionTestRepository;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ConnectionTestRepository connectionTestRepository;

    /**
     * Test 1: Cek apakah koneksi database berhasil
     */
    @GetMapping("/db-connection")
    public ResponseEntity<Map<String, Object>> testDbConnection() {
        Map<String, Object> response = new HashMap<>();
        try (Connection conn = dataSource.getConnection()) {
            response.put("status", "SUCCESS");
            response.put("message", "Koneksi PostgreSQL berhasil!");
            response.put("database", conn.getCatalog());
            response.put("url", conn.getMetaData().getURL());
            response.put("driverVersion", conn.getMetaData().getDriverVersion());
            response.put("timestamp", LocalDateTime.now());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "FAILED");
            response.put("message", "Koneksi gagal: " + e.getMessage());
            response.put("timestamp", LocalDateTime.now());
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * Test 2: Cek apakah JPA/Hibernate bisa write ke database
     */
    @PostMapping("/db-write")
    public ResponseEntity<Map<String, Object>> testDbWrite() {
        Map<String, Object> response = new HashMap<>();
        try {
            ConnectionTest saved = connectionTestRepository.save(
                new ConnectionTest("Test koneksi berhasil pada " + LocalDateTime.now())
            );
            response.put("status", "SUCCESS");
            response.put("message", "Write ke PostgreSQL berhasil!");
            response.put("savedRecord", saved.toString());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "FAILED");
            response.put("message", "Write gagal: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * Test 3: Cek apakah JPA/Hibernate bisa read dari database
     */
    @GetMapping("/db-read")
    public ResponseEntity<Map<String, Object>> testDbRead() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<ConnectionTest> records = connectionTestRepository.findAll();
            response.put("status", "SUCCESS");
            response.put("message", "Read dari PostgreSQL berhasil!");
            response.put("totalRecords", records.size());
            response.put("records", records.stream().map(ConnectionTest::toString).toList());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "FAILED");
            response.put("message", "Read gagal: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * Test 4: Health check sederhana
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "ERP System berjalan normal");
        response.put("timestamp", LocalDateTime.now());
        return ResponseEntity.ok(response);
    }
}