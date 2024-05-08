package com.qaroni.demoitests.application.services;

import com.qaroni.demoitests.domain.enums.APIError;
import com.qaroni.demoitests.domain.exception.APIException;
import com.qaroni.demoitests.domain.models.Author;
import com.qaroni.demoitests.domain.repository.AuthorRepository;
import com.qaroni.demoitests.infrastructure.ports.inbound.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author findById(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new APIException(APIError.AUTHOR_NOT_FOUND));
    }

    @Override
    public Author create(Author author) {
        if (authorRepository.existsByDocumentId(author.getDocumentId())) {
            throw new APIException(APIError.AUTHOR_ALREADY_EXISTS);
        }

        return authorRepository.save(author);
    }

    @Override
    public Author update(Long id, Author author) {
        Author authorToUpdate = authorRepository.findById(id).orElseThrow(() -> new APIException(APIError.AUTHOR_NOT_FOUND));

        authorToUpdate.setDocumentId(author.getDocumentId());
        authorToUpdate.setFullName(author.getFullName());
        return authorRepository.save(authorToUpdate);
    }
}
