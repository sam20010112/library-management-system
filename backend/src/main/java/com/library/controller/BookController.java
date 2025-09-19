package com.library.controller;

import com.library.dto.ApiResponse;
import com.library.dto.BookDto;
import com.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
public class BookController {
    
    @Autowired
    private BookService bookService;
    
    /**
     * Get all available books
     */
    @GetMapping("/available")
    public ResponseEntity<ApiResponse<List<BookDto>>> getAvailableBooks() {
        ApiResponse<List<BookDto>> response = bookService.getAvailableBooks();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    
    /**
     * Search books by keyword
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<BookDto>>> searchBooks(@RequestParam String keyword) {
        ApiResponse<List<BookDto>> response = bookService.searchBooks(keyword);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    
    /**
     * Get book by ISBN
     */
    @GetMapping("/{isbn}")
    public ResponseEntity<ApiResponse<BookDto>> getBookByIsbn(@PathVariable String isbn) {
        ApiResponse<BookDto> response = bookService.getBookByIsbn(isbn);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    
    /**
     * Get all books
     */
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<BookDto>>> getAllBooks() {
        ApiResponse<List<BookDto>> response = bookService.getAllBooks();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    
    /**
     * Health check
     */
    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> health() {
        return ResponseEntity.ok(ApiResponse.success("Book service is running", null));
    }
}


