package com.qaroni.demoitests.infrastructure.ports.inbound;

import com.qaroni.demoitests.domain.models.Book;

import java.util.List;

public interface BookService {
    List<Book> findAll();

    Book findById(Long id);

    List<Book> findByAuthorId(Long authorId);

    Book create(Book book);

    Book update(Long id, Book book);
}
