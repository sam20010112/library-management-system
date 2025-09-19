package com.library.service;

import com.library.dto.ApiResponse;
import com.library.dto.BorrowingRecordDto;
import com.library.entity.Book;
import com.library.entity.BorrowingRecord;
import com.library.entity.Inventory;
import com.library.entity.User;
import com.library.repository.BorrowingRecordRepository;
import com.library.repository.InventoryRepository;
import com.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class BorrowingService {
    
    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;
    
    @Autowired
    private InventoryRepository inventoryRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    /**
     * Borrow a book
     */
    public ApiResponse<String> borrowBook(Integer userId, Integer inventoryId) {
        try {
            // Check if user exists
            Optional<User> userOpt = userRepository.findById(userId);
            if (userOpt.isEmpty()) {
                return ApiResponse.error("User not found", 404);
            }
            
            // Check if inventory exists
            Optional<Inventory> inventoryOpt = inventoryRepository.findById(inventoryId);
            if (inventoryOpt.isEmpty()) {
                return ApiResponse.error("Book not found", 404);
            }
            
            // Check if inventory is available
            if (inventoryOpt.get().getStatus() != Inventory.InventoryStatus.AVAILABLE) {
                return ApiResponse.error("Book is not available for borrowing", 400);
            }
            
            // Check if user already borrowed this book
            Optional<BorrowingRecord> existingRecord = borrowingRecordRepository.findActiveBorrowingRecord(userId, inventoryId);
            if (existingRecord.isPresent()) {
                return ApiResponse.error("User has already borrowed this book", 400);
            }
            
            // Use stored procedure to borrow book (with transaction)
            inventoryRepository.borrowBook(userId, inventoryId);
            
            return ApiResponse.success("Book borrowed successfully", null);
            
        } catch (Exception e) {
            e.printStackTrace(); // Add logging for debugging
            return ApiResponse.error("Failed to borrow book: " + e.getMessage(), 500);
        }
    }
    
    /**
     * Return a book
     */
    public ApiResponse<String> returnBook(Integer userId, Integer inventoryId) {
        try {
            // Check if user exists
            Optional<User> userOpt = userRepository.findById(userId);
            if (userOpt.isEmpty()) {
                return ApiResponse.error("User not found", 404);
            }
            
            // Check if borrowing record exists and is active
            Optional<BorrowingRecord> recordOpt = borrowingRecordRepository.findActiveBorrowingRecord(userId, inventoryId);
            if (recordOpt.isEmpty()) {
                return ApiResponse.error("No active borrowing record found", 400);
            }
            
            // Log before calling stored procedure
            System.out.println("Attempting to return book - UserId: " + userId + ", InventoryId: " + inventoryId);
            
            // Use stored procedure to return book (with transaction)
            inventoryRepository.returnBook(userId, inventoryId);
            
            // Log after successful stored procedure call
            System.out.println("Stored procedure executed successfully");
            
            // Verify the inventory status was updated
            Optional<Inventory> inventoryOpt = inventoryRepository.findById(inventoryId);
            if (inventoryOpt.isPresent()) {
                String currentStatus = inventoryOpt.get().getStatus().toString();
                System.out.println("Current inventory status: " + currentStatus);
                
                if (!"AVAILABLE".equals(currentStatus)) {
                    return ApiResponse.error("Inventory status not updated correctly. Current status: " + currentStatus, 500);
                }
            }
            
            return ApiResponse.success("Book returned successfully", null);
            
        } catch (Exception e) {
            System.err.println("Error in returnBook: " + e.getMessage());
            e.printStackTrace();
            return ApiResponse.error("Failed to return book: " + e.getMessage(), 500);
        }
    }
    
    /**
     * Get user's borrowing history
     */
    public ApiResponse<List<BorrowingRecordDto>> getUserBorrowingHistory(Integer userId) {
        try {
            // Check if user exists
            Optional<User> userOpt = userRepository.findById(userId);
            if (userOpt.isEmpty()) {
                return ApiResponse.error("User not found", 404);
            }
            
            List<Object[]> results = borrowingRecordRepository.findBorrowingHistoryWithBookDetails(userId);
            List<BorrowingRecordDto> recordDtos = new ArrayList<>();
            
            for (Object[] result : results) {
                BorrowingRecord record = (BorrowingRecord) result[0];
                Book book = (Book) result[1];
                
                BorrowingRecordDto dto = new BorrowingRecordDto();
                dto.setUserId(record.getUserId());
                dto.setInventoryId(record.getInventoryId());
                dto.setBorrowingTime(record.getBorrowingTime());
                dto.setReturnTime(record.getReturnTime());
                dto.setIsbn(book.getIsbn());
                dto.setBookName(book.getName());
                dto.setAuthor(book.getAuthor());
                
                // Get inventory status
                Optional<Inventory> inventoryOpt = inventoryRepository.findById(record.getInventoryId());
                if (inventoryOpt.isPresent()) {
                    dto.setStatus(inventoryOpt.get().getStatus().getValue());
                }
                
                recordDtos.add(dto);
            }
            
            return ApiResponse.success(recordDtos);
            
        } catch (Exception e) {
            return ApiResponse.error("Failed to get borrowing history: " + e.getMessage(), 500);
        }
    }
    
    /**
     * Get user's active borrowings
     */
    public ApiResponse<List<BorrowingRecordDto>> getUserActiveBorrowings(Integer userId) {
        try {
            // Check if user exists
            Optional<User> userOpt = userRepository.findById(userId);
            if (userOpt.isEmpty()) {
                return ApiResponse.error("User not found", 404);
            }
            
            List<Object[]> results = borrowingRecordRepository.findActiveBorrowingRecordsWithBookDetails(userId);
            List<BorrowingRecordDto> recordDtos = new ArrayList<>();
            
            for (Object[] result : results) {
                BorrowingRecord record = (BorrowingRecord) result[0];
                Book book = (Book) result[1];
                
                BorrowingRecordDto dto = new BorrowingRecordDto();
                dto.setUserId(record.getUserId());
                dto.setInventoryId(record.getInventoryId());
                dto.setBorrowingTime(record.getBorrowingTime());
                dto.setReturnTime(record.getReturnTime());
                dto.setIsbn(book.getIsbn());
                dto.setBookName(book.getName());
                dto.setAuthor(book.getAuthor());
                
                // Get inventory status
                Optional<Inventory> inventoryOpt = inventoryRepository.findById(record.getInventoryId());
                if (inventoryOpt.isPresent()) {
                    dto.setStatus(inventoryOpt.get().getStatus().getValue());
                }
                
                recordDtos.add(dto);
            }
            
            return ApiResponse.success(recordDtos);
            
        } catch (Exception e) {
            return ApiResponse.error("Failed to get active borrowings: " + e.getMessage(), 500);
        }
    }
    
    /**
     * Get borrowing statistics for user
     */
    public ApiResponse<Object> getUserBorrowingStats(Integer userId) {
        try {
            // Check if user exists
            Optional<User> userOpt = userRepository.findById(userId);
            if (userOpt.isEmpty()) {
                return ApiResponse.error("User not found", 404);
            }
            
            long activeBorrowings = borrowingRecordRepository.countActiveBorrowingsByUserId(userId);
            List<BorrowingRecord> allRecords = borrowingRecordRepository.findBorrowingHistoryByUserId(userId);
            
            Map<String, Object> stats = new HashMap<>();
            stats.put("activeBorrowings", activeBorrowings);
            stats.put("totalBorrowings", allRecords.size());
            stats.put("returnedBorrowings", allRecords.size() - activeBorrowings);
            
            return ApiResponse.success(stats);
            
        } catch (Exception e) {
            return ApiResponse.error("Failed to get borrowing statistics: " + e.getMessage(), 500);
        }
    }
}
