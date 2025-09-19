package com.library.repository;

import com.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    
    /**
     * Find books by name containing (case insensitive)
     */
    List<Book> findByNameContainingIgnoreCase(String name);
    
    /**
     * Find books by author containing (case insensitive)
     */
    List<Book> findByAuthorContainingIgnoreCase(String author);
    
    /**
     * Find books by name or author containing (case insensitive)
     */
    @Query("SELECT b FROM Book b WHERE LOWER(b.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Book> findByNameOrAuthorContainingIgnoreCase(@Param("keyword") String keyword);
    
    /**
     * Find book by ISBN
     */
    Optional<Book> findByIsbn(String isbn);
}


