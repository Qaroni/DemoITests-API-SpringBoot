package com.qaroni.demoitests.domain.dto;

import com.qaroni.demoitests.domain.models.Author;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.Collections;

@Data
@Builder
public class AuthorDTO {
    private Long id;

    @NotBlank(message = "Required field")
    private String documentId;

    @NotBlank(message = "Required field")
    private String fullName;

    public Author toEntity() {
        return Author.builder()
                .documentId(this.documentId)
                .fullName(this.fullName)
                .books(Collections.emptyList())
                .build();
    }

    public static AuthorDTO fromEntity(Author author) {
        return AuthorDTO.builder()
                .id(author.getId())
                .documentId(author.getDocumentId())
                .fullName(author.getFullName())
                .build();
    }
}