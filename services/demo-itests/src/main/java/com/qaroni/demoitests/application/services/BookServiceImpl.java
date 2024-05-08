package com.qaroni.demoitests.application.services;

import com.qaroni.demoitests.domain.enums.APIError;
import com.qaroni.demoitests.domain.exception.APIException;
import com.qaroni.demoitests.domain.models.Author;
import com.qaroni.demoitests.domain.models.Book;
import com.qaroni.demoitests.domain.repository.BookRepository;
import com.qaroni.demoitests.infrastructure.ports.inbound.AuthorService;
import com.qaroni.demoitests.infrastructure.ports.inbound.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorService authorService;

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new APIException(APIError.BOOK_NOT_FOUND));
    }

    @Override
    public List<Book> findByAuthorId(Long authorId) {
        Author author = authorService.findById(authorId);
        return author.getBooks();
    }

    @Override
    public Book create(Book book) {
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw new APIException(APIError.BOOK_ALREADY_EXISTS);
        }

        return bookRepository.save(book);
    }

    @Override
    public Book update(Long id, Book book) {
        Book bookToUpdate = bookRepository.findById(id).orElseThrow(() -> new APIException(APIError.BOOK_NOT_FOUND));

        bookToUpdate.setIsbn(book.getIsbn());
        bookToUpdate.setTitle(book.getTitle());
        if (book.getAuthors() != null && !book.getAuthors().isEmpty()) {
            bookToUpdate.setAuthors(book.getAuthors());
        }
        return bookRepository.save(bookToUpdate);
    }
}
