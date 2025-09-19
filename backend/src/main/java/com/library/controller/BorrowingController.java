package com.library.controller;

import com.library.dto.ApiResponse;
import com.library.dto.BorrowingRecordDto;
import com.library.entity.User;
import com.library.service.BorrowingService;
import com.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrowing")
@CrossOrigin(origins = "*")
public class BorrowingController {
    
    @Autowired
    private BorrowingService borrowingService;
    
    @Autowired
    private UserService userService;
    
    /**
     * Borrow a book
     */
    @PostMapping("/borrow/{inventoryId}")
    public ResponseEntity<ApiResponse<String>> borrowBook(@PathVariable Integer inventoryId, 
                                                         Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        ApiResponse<String> response = borrowingService.borrowBook(user.getUserId(), inventoryId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    
    /**
     * Return a book
     */
    @PostMapping("/return/{inventoryId}")
    public ResponseEntity<ApiResponse<String>> returnBook(@PathVariable Integer inventoryId, 
                                                         Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        ApiResponse<String> response = borrowingService.returnBook(user.getUserId(), inventoryId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    
    /**
     * Get user's borrowing history
     */
    @GetMapping("/history")
    public ResponseEntity<ApiResponse<List<BorrowingRecordDto>>> getBorrowingHistory(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        ApiResponse<List<BorrowingRecordDto>> response = borrowingService.getUserBorrowingHistory(user.getUserId());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    
    /**
     * Get user's active borrowings
     */
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<BorrowingRecordDto>>> getActiveBorrowings(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        ApiResponse<List<BorrowingRecordDto>> response = borrowingService.getUserActiveBorrowings(user.getUserId());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    
    /**
     * Get user's borrowing statistics
     */
    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<Object>> getBorrowingStats(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        ApiResponse<Object> response = borrowingService.getUserBorrowingStats(user.getUserId());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    
    /**
     * Health check
     */
    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> health() {
        return ResponseEntity.ok(ApiResponse.success("Borrowing service is running", null));
    }
}


