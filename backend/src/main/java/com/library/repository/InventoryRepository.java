package com.library.repository;

import com.library.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    
    /**
     * Find inventories by ISBN
     */
    List<Inventory> findByIsbn(String isbn);
    
    /**
     * Find inventories by status
     */
    List<Inventory> findByStatus(Inventory.InventoryStatus status);
    
    /**
     * Find available inventories by ISBN
     */
    @Query("SELECT i FROM Inventory i WHERE i.isbn = :isbn AND i.status = 'AVAILABLE'")
    List<Inventory> findAvailableByIsbn(@Param("isbn") String isbn);
    
    /**
     * Find available inventories
     */
    @Query("SELECT i FROM Inventory i WHERE i.status = 'AVAILABLE'")
    List<Inventory> findAvailableInventories();
    
    /**
     * Find inventory by ID and status
     */
    @Query("SELECT i FROM Inventory i WHERE i.inventoryId = :inventoryId AND i.status = :status")
    Optional<Inventory> findByIdAndStatus(@Param("inventoryId") Integer inventoryId, 
                                        @Param("status") Inventory.InventoryStatus status);
    
    /**
     * Update inventory status
     */
    @Modifying
    @Transactional
    @Query("UPDATE Inventory i SET i.status = :status WHERE i.inventoryId = :inventoryId")
    void updateStatus(@Param("inventoryId") Integer inventoryId, 
                     @Param("status") Inventory.InventoryStatus status);
    
    /**
     * Borrow book using stored procedure
     */
    @Modifying
    @Transactional
    @Query(value = "CALL BorrowBook(:userId, :inventoryId)", nativeQuery = true)
    void borrowBook(@Param("userId") Integer userId, @Param("inventoryId") Integer inventoryId);
    
    /**
     * Return book using stored procedure
     */
    @Modifying
    @Transactional
    @Query(value = "CALL ReturnBook(:userId, :inventoryId)", nativeQuery = true)
    void returnBook(@Param("userId") Integer userId, @Param("inventoryId") Integer inventoryId);
}


