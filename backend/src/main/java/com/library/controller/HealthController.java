package com.library.controller;

import com.library.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class HealthController {
    
    @Autowired
    private DataSource dataSource;
    
    /**
     * 基本健康檢查
     */
    @GetMapping("/health")
    public ResponseEntity<ApiResponse<Map<String, Object>>> health() {
        Map<String, Object> healthData = new HashMap<>();
        healthData.put("status", "UP");
        healthData.put("timestamp", LocalDateTime.now());
        healthData.put("service", "Library Management System");
        healthData.put("version", "1.0.0");
        
        return ResponseEntity.ok(ApiResponse.success("Service is running", healthData));
    }
    
    /**
     * 詳細健康檢查（包含數據庫連接）
     */
    @GetMapping("/health/detailed")
    public ResponseEntity<ApiResponse<Map<String, Object>>> detailedHealth() {
        Map<String, Object> healthData = new HashMap<>();
        healthData.put("timestamp", LocalDateTime.now());
        healthData.put("service", "Library Management System");
        healthData.put("version", "1.0.0");
        
        // 檢查數據庫連接
        Map<String, Object> database = new HashMap<>();
        try (Connection connection = dataSource.getConnection()) {
            database.put("status", "UP");
            database.put("url", connection.getMetaData().getURL());
            database.put("driver", connection.getMetaData().getDriverName());
            database.put("message", "Database connection successful");
        } catch (Exception e) {
            database.put("status", "DOWN");
            database.put("error", e.getMessage());
            database.put("message", "Database connection failed");
        }
        
        healthData.put("database", database);
        
        // 檢查系統信息
        Map<String, Object> system = new HashMap<>();
        system.put("javaVersion", System.getProperty("java.version"));
        system.put("osName", System.getProperty("os.name"));
        system.put("osVersion", System.getProperty("os.version"));
        system.put("availableProcessors", Runtime.getRuntime().availableProcessors());
        system.put("freeMemory", Runtime.getRuntime().freeMemory());
        system.put("totalMemory", Runtime.getRuntime().totalMemory());
        system.put("maxMemory", Runtime.getRuntime().maxMemory());
        
        healthData.put("system", system);
        
        return ResponseEntity.ok(ApiResponse.success("Detailed health check completed", healthData));
    }
    
    /**
     * 簡單的 ping 端點
     */
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }
}
