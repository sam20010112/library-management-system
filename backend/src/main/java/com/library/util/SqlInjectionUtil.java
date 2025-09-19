package com.library.util;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class SqlInjectionUtil {
    
    private static final Pattern[] SQL_INJECTION_PATTERNS = {
        Pattern.compile(".*(union|select|insert|update|delete|drop|create|alter|exec|execute).*", Pattern.CASE_INSENSITIVE),
        Pattern.compile(".*(or|and).*=.*", Pattern.CASE_INSENSITIVE),
        Pattern.compile(".*'.*or.*'.*", Pattern.CASE_INSENSITIVE),
        Pattern.compile(".*'.*and.*'.*", Pattern.CASE_INSENSITIVE),
        Pattern.compile(".*'.*union.*'.*", Pattern.CASE_INSENSITIVE),
        Pattern.compile(".*'.*select.*'.*", Pattern.CASE_INSENSITIVE),
        Pattern.compile(".*'.*insert.*'.*", Pattern.CASE_INSENSITIVE),
        Pattern.compile(".*'.*update.*'.*", Pattern.CASE_INSENSITIVE),
        Pattern.compile(".*'.*delete.*'.*", Pattern.CASE_INSENSITIVE),
        Pattern.compile(".*'.*drop.*'.*", Pattern.CASE_INSENSITIVE),
        Pattern.compile(".*'.*create.*'.*", Pattern.CASE_INSENSITIVE),
        Pattern.compile(".*'.*alter.*'.*", Pattern.CASE_INSENSITIVE),
        Pattern.compile(".*'.*exec.*'.*", Pattern.CASE_INSENSITIVE),
        Pattern.compile(".*'.*execute.*'.*", Pattern.CASE_INSENSITIVE),
        Pattern.compile(".*;.*", Pattern.CASE_INSENSITIVE),
        Pattern.compile(".*--.*", Pattern.CASE_INSENSITIVE),
        Pattern.compile(".*/\\*.*\\*/.*", Pattern.CASE_INSENSITIVE),
        Pattern.compile(".*xp_.*", Pattern.CASE_INSENSITIVE),
        Pattern.compile(".*sp_.*", Pattern.CASE_INSENSITIVE)
    };
    
    /**
     * Check if input contains SQL injection patterns
     */
    public boolean containsSqlInjection(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }
        
        for (Pattern pattern : SQL_INJECTION_PATTERNS) {
            if (pattern.matcher(input).matches()) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Sanitize input by removing potentially dangerous characters
     */
    public String sanitizeInput(String input) {
        if (input == null) {
            return null;
        }
        
        // Remove common SQL injection characters
        return input.replaceAll("[';\"\\\\]", "")
                   .replaceAll("--", "")
                   .replaceAll("/\\*", "")
                   .replaceAll("\\*/", "")
                   .trim();
    }
    
    /**
     * Validate input for SQL injection
     */
    public boolean isValidInput(String input) {
        return !containsSqlInjection(input);
    }
}
