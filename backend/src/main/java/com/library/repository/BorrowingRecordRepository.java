package com.library.repository;

import com.library.entity.BorrowingRecord;
import com.library.entity.BorrowingRecordId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, BorrowingRecordId> {
    
    /**
     * Find borrowing records by user ID
     */
    List<BorrowingRecord> findByUserId(Integer userId);
    
    /**
     * Find borrowing records by inventory ID
     */
    List<BorrowingRecord> findByInventoryId(Integer inventoryId);
    
    /**
     * Find active borrowing record (not returned)
     */
    @Query("SELECT br FROM BorrowingRecord br WHERE br.userId = :userId AND br.inventoryId = :inventoryId AND br.returnTime IS NULL")
    Optional<BorrowingRecord> findActiveBorrowingRecord(@Param("userId") Integer userId, 
                                                       @Param("inventoryId") Integer inventoryId);
    
    /**
     * Find all active borrowing records for a user
     */
    @Query("SELECT br FROM BorrowingRecord br WHERE br.userId = :userId AND br.returnTime IS NULL")
    List<BorrowingRecord> findActiveBorrowingRecordsByUserId(@Param("userId") Integer userId);
    
    /**
     * Find active borrowing records with book details for a user
     */
    @Query("SELECT br, b FROM BorrowingRecord br JOIN br.inventory i JOIN i.book b WHERE br.userId = :userId AND br.returnTime IS NULL ORDER BY br.borrowingTime DESC")
    List<Object[]> findActiveBorrowingRecordsWithBookDetails(@Param("userId") Integer userId);
    
    /**
     * Find borrowing history for a user
     */
    @Query("SELECT br FROM BorrowingRecord br WHERE br.userId = :userId ORDER BY br.borrowingTime DESC")
    List<BorrowingRecord> findBorrowingHistoryByUserId(@Param("userId") Integer userId);
    
    /**
     * Find borrowing history with book details for a user
     */
    @Query("SELECT br, b FROM BorrowingRecord br JOIN br.inventory i JOIN i.book b WHERE br.userId = :userId ORDER BY br.borrowingTime DESC")
    List<Object[]> findBorrowingHistoryWithBookDetails(@Param("userId") Integer userId);
    
    /**
     * Find borrowing records by date range
     */
    @Query("SELECT br FROM BorrowingRecord br WHERE br.borrowingTime BETWEEN :startDate AND :endDate")
    List<BorrowingRecord> findBorrowingRecordsByDateRange(@Param("startDate") LocalDateTime startDate, 
                                                          @Param("endDate") LocalDateTime endDate);
    
    /**
     * Count active borrowings for a user
     */
    @Query("SELECT COUNT(br) FROM BorrowingRecord br WHERE br.userId = :userId AND br.returnTime IS NULL")
    long countActiveBorrowingsByUserId(@Param("userId") Integer userId);
    
    /**
     * Get user borrowing history using stored procedure
     */
    @Query(value = "CALL GetUserBorrowingHistory(:userId)", nativeQuery = true)
    List<Object[]> getUserBorrowingHistory(@Param("userId") Integer userId);
}
