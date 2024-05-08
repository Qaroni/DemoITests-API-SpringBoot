package com.qaroni.demoitests.domain.repository;

import com.qaroni.demoitests.domain.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByIsbn(String isbn);
}
