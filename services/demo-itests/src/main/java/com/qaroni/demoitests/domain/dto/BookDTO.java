package com.qaroni.demoitests.domain.dto;

import com.qaroni.demoitests.domain.models.Book;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.Collections;

@Data
@Builder
public class BookDTO {
    private Long id;

    @NotBlank(message = "Required field")
    private String isbn;

    @NotBlank(message = "Required field")
    private String title;

    public Book toEntity() {
        return Book.builder()
                .isbn(this.isbn)
                .title(this.title)
                .authors(Collections.emptyList())
                .build();
    }

    public static BookDTO fromEntity(Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .build();
    }
}
