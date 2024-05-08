package com.qaroni.demoitests.domain.dto;

import com.qaroni.demoitests.domain.models.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreateBookDTO {
    @NotBlank(message = "Required field")
    private String isbn;

    @NotBlank(message = "Required field")
    private String title;


    @NotNull(message = "Required field")
    @NotEmpty(message = "Required at least one author")
    private List<Long> authorIds;

    public Book toEntity() {
        return Book.builder()
                .isbn(this.isbn)
                .title(this.title)
                .build();
    }
}

