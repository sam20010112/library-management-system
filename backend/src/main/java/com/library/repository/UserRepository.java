package com.library.repository;

import com.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    /**
     * Find user by phone number
     */
    Optional<User> findByPhoneNumber(String phoneNumber);
    
    /**
     * Check if phone number exists
     */
    boolean existsByPhoneNumber(String phoneNumber);
    
    /**
     * Update last login time
     */
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.lastLoginTime = :loginTime WHERE u.userId = :userId")
    void updateLastLoginTime(@Param("userId") Integer userId, @Param("loginTime") LocalDateTime loginTime);
    
    /**
     * Register new user using stored procedure
     */
    @Modifying
    @Transactional
    @Query(value = "CALL RegisterUser(:phoneNumber, :password, :userName)", nativeQuery = true)
    void registerUser(@Param("phoneNumber") String phoneNumber, 
                     @Param("password") String password, 
                     @Param("userName") String userName);
}


