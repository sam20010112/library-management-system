package com.library.dto;

import java.time.LocalDateTime;

public class BorrowingRecordDto {
    
    private Integer userId;
    private Integer inventoryId;
    private String isbn;
    private String bookName;
    private String author;
    private LocalDateTime borrowingTime;
    private LocalDateTime returnTime;
    private String status;
    
    // Constructors
    public BorrowingRecordDto() {}
    
    public BorrowingRecordDto(Integer userId, Integer inventoryId, String isbn, 
                             String bookName, String author, LocalDateTime borrowingTime, 
                             LocalDateTime returnTime, String status) {
        this.userId = userId;
        this.inventoryId = inventoryId;
        this.isbn = isbn;
        this.bookName = bookName;
        this.author = author;
        this.borrowingTime = borrowingTime;
        this.returnTime = returnTime;
        this.status = status;
    }
    
    // Getters and Setters
    public Integer getUserId() {
        return userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public Integer getInventoryId() {
        return inventoryId;
    }
    
    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public String getBookName() {
        return bookName;
    }
    
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public LocalDateTime getBorrowingTime() {
        return borrowingTime;
    }
    
    public void setBorrowingTime(LocalDateTime borrowingTime) {
        this.borrowingTime = borrowingTime;
    }
    
    public LocalDateTime getReturnTime() {
        return returnTime;
    }
    
    public void setReturnTime(LocalDateTime returnTime) {
        this.returnTime = returnTime;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "BorrowingRecordDto{" +
                "userId=" + userId +
                ", inventoryId=" + inventoryId +
                ", isbn='" + isbn + '\'' +
                ", bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", borrowingTime=" + borrowingTime +
                ", returnTime=" + returnTime +
                ", status='" + status + '\'' +
                '}';
    }
}


