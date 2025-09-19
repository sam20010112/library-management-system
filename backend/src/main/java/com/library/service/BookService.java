package com.library.service;

import com.library.dto.ApiResponse;
import com.library.dto.BookDto;
import com.library.entity.Book;
import com.library.entity.Inventory;
import com.library.repository.BookRepository;
import com.library.repository.InventoryRepository;
import com.library.util.SqlInjectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService {
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private InventoryRepository inventoryRepository;
    
    @Autowired
    private SqlInjectionUtil sqlInjectionUtil;
    
    /**
     * Get all available books
     */
    public ApiResponse<List<BookDto>> getAvailableBooks() {
        try {
            List<Inventory> availableInventories = inventoryRepository.findAvailableInventories();
            List<BookDto> bookDtos = new ArrayList<>();
            
            for (Inventory inventory : availableInventories) {
                Optional<Book> bookOpt = bookRepository.findById(inventory.getIsbn());
                if (bookOpt.isPresent()) {
                    Book book = bookOpt.get();
                    BookDto bookDto = new BookDto();
                    bookDto.setIsbn(book.getIsbn());
                    bookDto.setName(book.getName());
                    bookDto.setAuthor(book.getAuthor());
                    bookDto.setIntroduction(book.getIntroduction());
                    bookDto.setInventoryId(inventory.getInventoryId());
                    bookDto.setStatus(inventory.getStatus().getValue());
                    bookDto.setStoreTime(inventory.getStoreTime() != null ? inventory.getStoreTime().toString() : null);
                    bookDtos.add(bookDto);
                }
            }
            
            return ApiResponse.success(bookDtos);
            
        } catch (Exception e) {
            return ApiResponse.error("Failed to get available books: " + e.getMessage(), 500);
        }
    }
    
    /**
     * Search books by keyword
     */
    public ApiResponse<List<BookDto>> searchBooks(String keyword) {
        try {
            // Check for SQL injection
            if (sqlInjectionUtil.containsSqlInjection(keyword)) {
                return ApiResponse.error("Invalid search keyword", 400);
            }
            
            List<Book> books = bookRepository.findByNameOrAuthorContainingIgnoreCase(keyword);
            List<BookDto> bookDtos = new ArrayList<>();
            
            for (Book book : books) {
                BookDto bookDto = new BookDto();
                bookDto.setIsbn(book.getIsbn());
                bookDto.setName(book.getName());
                bookDto.setAuthor(book.getAuthor());
                bookDto.setIntroduction(book.getIntroduction());
                
                // Check if book has available inventory
                List<Inventory> availableInventories = inventoryRepository.findAvailableByIsbn(book.getIsbn());
                if (!availableInventories.isEmpty()) {
                    bookDto.setInventoryId(availableInventories.get(0).getInventoryId());
                    bookDto.setStatus("AVAILABLE");
                } else {
                    bookDto.setStatus("NOT_AVAILABLE");
                }
                
                bookDtos.add(bookDto);
            }
            
            return ApiResponse.success(bookDtos);
            
        } catch (Exception e) {
            return ApiResponse.error("Failed to search books: " + e.getMessage(), 500);
        }
    }
    
    /**
     * Get book by ISBN
     */
    public ApiResponse<BookDto> getBookByIsbn(String isbn) {
        try {
            // Check for SQL injection
            if (sqlInjectionUtil.containsSqlInjection(isbn)) {
                return ApiResponse.error("Invalid ISBN", 400);
            }
            
            Optional<Book> bookOpt = bookRepository.findByIsbn(isbn);
            
            if (bookOpt.isEmpty()) {
                return ApiResponse.error("Book not found", 404);
            }
            
            Book book = bookOpt.get();
            BookDto bookDto = new BookDto();
            bookDto.setIsbn(book.getIsbn());
            bookDto.setName(book.getName());
            bookDto.setAuthor(book.getAuthor());
            bookDto.setIntroduction(book.getIntroduction());
            
            // Check if book has available inventory
            List<Inventory> availableInventories = inventoryRepository.findAvailableByIsbn(book.getIsbn());
            if (!availableInventories.isEmpty()) {
                bookDto.setInventoryId(availableInventories.get(0).getInventoryId());
                bookDto.setStatus("AVAILABLE");
            } else {
                bookDto.setStatus("NOT_AVAILABLE");
            }
            
            return ApiResponse.success(bookDto);
            
        } catch (Exception e) {
            return ApiResponse.error("Failed to get book: " + e.getMessage(), 500);
        }
    }
    
    /**
     * Get all books
     */
    public ApiResponse<List<BookDto>> getAllBooks() {
        try {
            List<Book> books = bookRepository.findAll();
            List<BookDto> bookDtos = new ArrayList<>();
            
            for (Book book : books) {
                BookDto bookDto = new BookDto();
                bookDto.setIsbn(book.getIsbn());
                bookDto.setName(book.getName());
                bookDto.setAuthor(book.getAuthor());
                bookDto.setIntroduction(book.getIntroduction());
                
                // Check if book has available inventory
                List<Inventory> availableInventories = inventoryRepository.findAvailableByIsbn(book.getIsbn());
                if (!availableInventories.isEmpty()) {
                    bookDto.setInventoryId(availableInventories.get(0).getInventoryId());
                    bookDto.setStatus("AVAILABLE");
                } else {
                    bookDto.setStatus("NOT_AVAILABLE");
                }
                
                bookDtos.add(bookDto);
            }
            
            return ApiResponse.success(bookDtos);
            
        } catch (Exception e) {
            return ApiResponse.error("Failed to get books: " + e.getMessage(), 500);
        }
    }
}
