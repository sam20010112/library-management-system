-- Library Management System Database Schema
-- DDL (Data Definition Language)

-- Create database
CREATE DATABASE IF NOT EXISTS library_system;
USE library_system;

-- User table
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    phone_number VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    user_name VARCHAR(100) NOT NULL,
    registration_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login_time TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Book table
CREATE TABLE books (
    isbn VARCHAR(20) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    introduction TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Inventory table
CREATE TABLE inventory (
    inventory_id INT AUTO_INCREMENT PRIMARY KEY,
    isbn VARCHAR(20) NOT NULL,
    store_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('AVAILABLE', 'BORROWED', 'PROCESSING', 'LOST_DAMAGED', 'DISPOSED') DEFAULT 'AVAILABLE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (isbn) REFERENCES books(isbn) ON DELETE CASCADE
);

-- Borrowing Record table
CREATE TABLE borrowing_records (
    user_id INT NOT NULL,
    inventory_id INT NOT NULL,
    borrowing_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    return_time TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, inventory_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (inventory_id) REFERENCES inventory(inventory_id) ON DELETE CASCADE
);

-- Create indexes for better performance
CREATE INDEX idx_users_phone ON users(phone_number);
CREATE INDEX idx_borrowing_user ON borrowing_records(user_id);
CREATE INDEX idx_borrowing_inventory ON borrowing_records(inventory_id);
CREATE INDEX idx_inventory_status ON inventory(status);
CREATE INDEX idx_inventory_isbn ON inventory(isbn);

-- Stored Procedures

-- Procedure to register a new user
DELIMITER //
CREATE PROCEDURE RegisterUser(
    IN p_phone_number VARCHAR(20),
    IN p_password VARCHAR(255),
    IN p_user_name VARCHAR(100)
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    
    START TRANSACTION;
    
    INSERT INTO users (phone_number, password, user_name)
    VALUES (p_phone_number, p_password, p_user_name);
    
    COMMIT;
END //
DELIMITER ;

-- Procedure to borrow a book
DELIMITER //
CREATE PROCEDURE BorrowBook(
    IN p_user_id INT,
    IN p_inventory_id INT
)
BEGIN
    DECLARE v_status VARCHAR(20);
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    
    START TRANSACTION;
    
    -- Check if inventory is available
    SELECT status INTO v_status FROM inventory WHERE inventory_id = p_inventory_id;
    
    IF v_status != 'AVAILABLE' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Book is not available for borrowing';
    END IF;
    
    -- Update inventory status
    UPDATE inventory SET status = 'BORROWED' WHERE inventory_id = p_inventory_id;
    
    -- Insert borrowing record with borrowing_time
    INSERT INTO borrowing_records (user_id, inventory_id, borrowing_time) 
    VALUES (p_user_id, p_inventory_id, CURRENT_TIMESTAMP);
    
    COMMIT;
END //
DELIMITER ;

-- Procedure to return a book
DELIMITER //
CREATE PROCEDURE ReturnBook(
    IN p_user_id INT,
    IN p_inventory_id INT
)
BEGIN
    DECLARE v_return_time TIMESTAMP;
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    
    START TRANSACTION;
    
    -- Check if record exists and is not returned
    SELECT return_time INTO v_return_time 
    FROM borrowing_records 
    WHERE user_id = p_user_id AND inventory_id = p_inventory_id;
    
    IF v_return_time IS NOT NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Book has already been returned';
    END IF;
    
    -- Update borrowing record
    UPDATE borrowing_records 
    SET return_time = CURRENT_TIMESTAMP 
    WHERE user_id = p_user_id AND inventory_id = p_inventory_id;
    
    -- Update inventory status
    UPDATE inventory SET status = 'AVAILABLE' WHERE inventory_id = p_inventory_id;
    
    COMMIT;
END //
DELIMITER ;

-- Procedure to get user borrowing history
DELIMITER //
CREATE PROCEDURE GetUserBorrowingHistory(IN p_user_id INT)
BEGIN
    SELECT 
        br.user_id,
        br.inventory_id,
        b.isbn,
        b.name as book_name,
        b.author,
        br.borrowing_time,
        br.return_time,
        i.status
    FROM borrowing_records br
    JOIN inventory i ON br.inventory_id = i.inventory_id
    JOIN books b ON i.isbn = b.isbn
    WHERE br.user_id = p_user_id
    ORDER BY br.borrowing_time DESC;
END //
DELIMITER ;

-- Procedure to get available books
DELIMITER //
CREATE PROCEDURE GetAvailableBooks()
BEGIN
    SELECT 
        i.inventory_id,
        b.isbn,
        b.name,
        b.author,
        b.introduction,
        i.store_time
    FROM inventory i
    JOIN books b ON i.isbn = b.isbn
    WHERE i.status = 'AVAILABLE'
    ORDER BY b.name;
END //
DELIMITER ;


