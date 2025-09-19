package com.library.entity;

import java.io.Serializable;

public class BorrowingRecordId implements Serializable {
    private Integer userId;
    private Integer inventoryId;
    
    public BorrowingRecordId() {}
    
    public BorrowingRecordId(Integer userId, Integer inventoryId) {
        this.userId = userId;
        this.inventoryId = inventoryId;
    }
    
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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BorrowingRecordId that = (BorrowingRecordId) o;
        return userId.equals(that.userId) && inventoryId.equals(that.inventoryId);
    }
    
    @Override
    public int hashCode() {
        return userId.hashCode() + inventoryId.hashCode();
    }
}


