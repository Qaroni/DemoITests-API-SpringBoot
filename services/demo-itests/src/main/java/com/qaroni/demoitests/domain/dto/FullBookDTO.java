package com.qaroni.demoitests.domain.dto;

import com.qaroni.demoitests.domain.models.Book;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FullBookDTO {
    private Long id;

    @NotBlank(message = "Required field")
    private String isbn;

    @NotBlank(message = "Required field")
    private String title;

    private List<AuthorDTO> authors;

    public Book toEntity() {
        return Book.builder()
                .isbn(this.isbn)
                .title(this.title)
                .authors(this.authors.stream().map(AuthorDTO::toEntity).toList())
                .build();
    }

    public static FullBookDTO fromEntity(Book book) {
        return FullBookDTO.builder()
                .id(book.getId())
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .authors(book.getAuthors().stream().map(AuthorDTO::fromEntity).toList())
                .build();
    }
}
