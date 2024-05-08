package com.qaroni.demoitests.infrastructure.ports.inbound;

import com.qaroni.demoitests.domain.models.Author;

import java.util.List;

public interface AuthorService {
    List<Author> findAll();

    Author findById(Long id);

    Author create(Author author);

    Author update(Long id, Author author);
}
