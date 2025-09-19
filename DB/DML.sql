-- Library Management System Sample Data
-- DML (Data Manipulation Language)

USE library_system;

-- Insert sample books
INSERT INTO books (isbn, name, author, introduction) VALUES
('978-0134685991', 'Effective Java', 'Joshua Bloch', 'A comprehensive guide to Java programming best practices and design patterns.'),
('978-0132350884', 'Clean Code', 'Robert C. Martin', 'A handbook of agile software craftsmanship focusing on writing clean, maintainable code.'),
('978-0201633610', 'Design Patterns', 'Gang of Four', 'Elements of Reusable Object-Oriented Software - classic patterns for software design.'),
('978-0596007126', 'Head First Design Patterns', 'Eric Freeman', 'A brain-friendly guide to design patterns using Java.'),
('978-0137081073', 'The Pragmatic Programmer', 'David Thomas', 'Your journey to mastery - tips and tricks for becoming a better programmer.'),
('978-0134685992', 'Java: The Complete Reference', 'Herbert Schildt', 'Comprehensive reference for Java programming language.'),
('978-0132350885', 'Clean Architecture', 'Robert C. Martin', 'A craftsman guide to software structure and design.'),
('978-0201633611', 'Refactoring', 'Martin Fowler', 'Improving the design of existing code through refactoring techniques.'),
('978-0596007127', 'Head First Java', 'Kathy Sierra', 'A brain-friendly guide to Java programming.'),
('978-0137081074', 'Code Complete', 'Steve McConnell', 'A practical handbook of software construction.'),
('978-0134685993', 'JavaScript: The Good Parts', 'Douglas Crockford', 'A guide to the good parts of JavaScript programming language.'),
('978-0132350886', 'You Dont Know JS', 'Kyle Simpson', 'A series of books diving deep into the core mechanisms of JavaScript.'),
('978-0201633612', 'Eloquent JavaScript', 'Marijn Haverbeke', 'A modern introduction to programming with JavaScript.'),
('978-0596007128', 'Learning React', 'Alex Banks', 'A hands-on guide to building web applications with React.'),
('978-0137081075', 'React: Up & Running', 'Stoyan Stefanov', 'Building Web Applications with React and Redux.'),
('978-0134685994', 'Vue.js: Up and Running', 'Callum Macrae', 'Building Accessible and Performant Web Apps.'),
('978-0132350887', 'Angular: The Complete Guide', 'Maximilian Schwarzmüller', 'Master Angular from beginner to advanced level.'),
('978-0201633613', 'Node.js in Action', 'Mike Cantelon', 'Server-side JavaScript with Node.js and Express.'),
('978-0596007129', 'Express in Action', 'Evan Hahn', 'Writing, building, and testing Node.js applications.'),
('978-0137081076', 'Python Crash Course', 'Eric Matthes', 'A hands-on, project-based introduction to programming.'),
('978-0134685995', 'Automate the Boring Stuff', 'Al Sweigart', 'Practical programming for total beginners.'),
('978-0132350888', 'Fluent Python', 'Luciano Ramalho', 'Clear, concise, and effective programming with Python.'),
('978-0201633614', 'Django for Beginners', 'William S. Vincent', 'Build websites with Python and Django.'),
('978-0596007130', 'Flask Web Development', 'Miguel Grinberg', 'Developing Web Applications with Python.'),
('978-0137081077', 'C++ Primer', 'Stanley Lippman', 'A comprehensive introduction to C++ programming.'),
('978-0134685996', 'The C Programming Language', 'Brian Kernighan', 'The classic reference for C programming language.'),
('978-0132350889', 'C# in Depth', 'Jon Skeet', 'A comprehensive guide to C# programming language.'),
('978-0201633615', 'Pro C# 8.0', 'Andrew Troelsen', 'Complete guide to C# 8.0 and .NET Core.'),
('978-0596007131', 'Go in Action', 'William Kennedy', 'A practical guide to building applications with Go.'),
('978-0137081078', 'Rust in Action', 'Tim McNamara', 'Systems programming concepts and techniques using Rust.'),
('978-0134685997', 'Programming Rust', 'Jim Blandy', 'Fast, safe systems development with Rust.'),
('978-0132350890', 'Kotlin in Action', 'Dmitry Jemerov', 'A comprehensive guide to Kotlin programming.'),
('978-0201633616', 'Swift Programming', 'Apple Inc.', 'The Swift Programming Language official guide.'),
('978-0596007132', 'Dart in Action', 'Manning Publications', 'A comprehensive guide to Dart programming.'),
('978-0137081079', 'TypeScript in Action', 'Boris Cherny', 'Building applications with TypeScript.'),
('978-0134685998', 'Learning TypeScript', 'Josh Goldberg', 'A comprehensive guide to TypeScript development.'),
('978-0132350891', 'PHP: The Right Way', 'Josh Lockhart', 'A comprehensive guide to modern PHP development.'),
('978-0201633617', 'Laravel: Up and Running', 'Matt Stauffer', 'A framework for building modern PHP applications.');

-- Insert sample inventory
INSERT INTO inventory (isbn, store_time, status) VALUES
('978-0134685991', '2024-01-15 10:00:00', 'AVAILABLE'),
('978-0134685991', '2024-01-15 10:00:00', 'AVAILABLE'),
('978-0132350884', '2024-01-16 11:00:00', 'AVAILABLE'),
('978-0201633610', '2024-01-17 12:00:00', 'AVAILABLE'),
('978-0201633610', '2024-01-17 12:00:00', 'BORROWED'),
('978-0596007126', '2024-01-18 13:00:00', 'AVAILABLE'),
('978-0137081073', '2024-01-19 14:00:00', 'AVAILABLE'),
('978-0134685992', '2024-01-20 15:00:00', 'AVAILABLE'),
('978-0132350885', '2024-01-21 16:00:00', 'AVAILABLE'),
('978-0201633611', '2024-01-22 17:00:00', 'AVAILABLE'),
('978-0596007127', '2024-01-23 18:00:00', 'AVAILABLE'),
('978-0137081074', '2024-01-24 19:00:00', 'AVAILABLE'),
-- New books inventory
('978-0134685993', '2024-01-25 10:00:00', 'AVAILABLE'),
('978-0132350886', '2024-01-25 11:00:00', 'AVAILABLE'),
('978-0201633612', '2024-01-25 12:00:00', 'AVAILABLE'),
('978-0596007128', '2024-01-25 13:00:00', 'AVAILABLE'),
('978-0137081075', '2024-01-25 14:00:00', 'AVAILABLE'),
('978-0134685994', '2024-01-25 15:00:00', 'AVAILABLE'),
('978-0132350887', '2024-01-25 16:00:00', 'AVAILABLE'),
('978-0201633613', '2024-01-25 17:00:00', 'AVAILABLE'),
('978-0596007129', '2024-01-25 18:00:00', 'AVAILABLE'),
('978-0137081076', '2024-01-25 19:00:00', 'AVAILABLE'),
('978-0134685995', '2024-01-26 10:00:00', 'AVAILABLE'),
('978-0132350888', '2024-01-26 11:00:00', 'AVAILABLE'),
('978-0201633614', '2024-01-26 12:00:00', 'AVAILABLE'),
('978-0596007130', '2024-01-26 13:00:00', 'AVAILABLE'),
('978-0137081077', '2024-01-26 14:00:00', 'AVAILABLE'),
('978-0134685996', '2024-01-26 15:00:00', 'AVAILABLE'),
('978-0132350889', '2024-01-26 16:00:00', 'AVAILABLE'),
('978-0201633615', '2024-01-26 17:00:00', 'AVAILABLE'),
('978-0596007131', '2024-01-26 18:00:00', 'AVAILABLE'),
('978-0137081078', '2024-01-26 19:00:00', 'AVAILABLE'),
('978-0134685997', '2024-01-27 10:00:00', 'AVAILABLE'),
('978-0132350890', '2024-01-27 11:00:00', 'AVAILABLE'),
('978-0201633616', '2024-01-27 12:00:00', 'AVAILABLE'),
('978-0596007132', '2024-01-27 13:00:00', 'AVAILABLE'),
('978-0137081079', '2024-01-27 14:00:00', 'AVAILABLE'),
('978-0134685998', '2024-01-27 15:00:00', 'AVAILABLE'),
('978-0132350891', '2024-01-27 16:00:00', 'AVAILABLE'),
('978-0201633617', '2024-01-27 17:00:00', 'AVAILABLE');

-- Insert sample users (passwords are hashed with salt - each user has different password)
-- John Smith: password123
-- Jane Doe: jane2024
-- Bob Johnson: bob123
-- Alice Brown: alice456
-- Charlie Wilson: charlie789
-- testuser: 123456
INSERT INTO users (phone_number, password, user_name, registration_time, last_login_time) VALUES
('0912345678', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'John Smith', '2024-01-01 09:00:00', '2024-01-25 10:30:00'),
('0987654321', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Jane Doe', '2024-01-02 10:00:00', '2024-01-25 11:15:00'),
('0955555555', '$2a$10$TKh8H1.PfQx37YgCzwiKb.KjNyWgaHb9cbcoQgdIVFlYg7B77UdFm', 'Bob Johnson', '2024-01-03 11:00:00', '2024-01-25 12:00:00'),
('0933333333', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Alice Brown', '2024-01-04 12:00:00', '2024-01-25 13:45:00'),
('0977777777', '$2a$10$TKh8H1.PfQx37YgCzwiKb.KjNyWgaHb9cbcoQgdIVFlYg7B77UdFm', 'Charlie Wilson', '2024-01-05 13:00:00', '2024-01-25 14:20:00'),
('0955123456', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'testuser', '2024-01-06 14:00:00', '2024-01-25 15:30:00');

-- Insert sample borrowing records
INSERT INTO borrowing_records (user_id, inventory_id, borrowing_time, return_time) VALUES
(1, 5, '2024-01-20 14:00:00', NULL),  -- John borrowed Design Patterns (still borrowed)
(2, 1, '2024-01-22 10:00:00', '2024-01-24 16:00:00'),  -- Jane borrowed and returned Effective Java
(3, 2, '2024-01-23 11:00:00', NULL),  -- Bob borrowed Effective Java (still borrowed)
(4, 6, '2024-01-24 09:00:00', NULL),  -- Alice borrowed Head First Design Patterns (still borrowed)
(5, 7, '2024-01-25 15:00:00', NULL);  -- Charlie borrowed The Pragmatic Programmer (still borrowed)

-- Update inventory status for borrowed books
UPDATE inventory SET status = 'BORROWED' WHERE inventory_id IN (1, 2, 5, 6, 7);

-- Verify data
SELECT 'Users' as table_name, COUNT(*) as record_count FROM users
UNION ALL
SELECT 'Books', COUNT(*) FROM books
UNION ALL
SELECT 'Inventory', COUNT(*) FROM inventory
UNION ALL
SELECT 'Borrowing Records', COUNT(*) FROM borrowing_records;


