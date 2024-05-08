package com.qaroni.demoitests.domain.dto;

import com.qaroni.demoitests.domain.models.Author;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FullAuthorDTO {

    private Long id;

    @NotBlank(message = "Required field")
    private String documentId;

    @NotBlank(message = "Required field")
    private String fullName;

    private List<BookDTO> books;

    public Author toEntity() {
        return Author.builder()
                .documentId(this.documentId)
                .fullName(this.fullName)
                .books(this.books.stream().map(BookDTO::toEntity).toList())
                .build();
    }

    public static FullAuthorDTO fromEntity(Author author) {
        return FullAuthorDTO.builder()
                .id(author.getId())
                .documentId(author.getDocumentId())
                .fullName(author.getFullName())
                .books(author.getBooks().stream().map(BookDTO::fromEntity).toList())
                .build();
    }
}
