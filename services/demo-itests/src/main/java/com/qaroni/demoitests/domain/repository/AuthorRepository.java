package com.qaroni.demoitests.domain.repository;

import com.qaroni.demoitests.domain.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    boolean existsByDocumentId(String fullName);
}
