package com.qaroni.demoitests.infrastructure.gateway;

import com.qaroni.demoitests.domain.dto.BookDTO;
import com.qaroni.demoitests.domain.dto.CreateBookDTO;
import com.qaroni.demoitests.domain.dto.FullBookDTO;
import com.qaroni.demoitests.domain.models.Book;
import com.qaroni.demoitests.infrastructure.ports.inbound.AuthorService;
import com.qaroni.demoitests.infrastructure.ports.inbound.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @GetMapping()
    public ResponseEntity<List<FullBookDTO>> findAll() {
        return ResponseEntity.ok(bookService.findAll().stream().map(FullBookDTO::fromEntity).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullBookDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(FullBookDTO.fromEntity(bookService.findById(id)));
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<FullBookDTO>> findByAuthorId(@PathVariable Long authorId) {
        return ResponseEntity.ok(bookService.findByAuthorId(authorId).stream().map(FullBookDTO::fromEntity).toList());
    }

    @PostMapping()
    public ResponseEntity<FullBookDTO> create(@Valid @RequestBody CreateBookDTO createBookDTO) {
        Book book = createBookDTO.toEntity();
        book.setAuthors(createBookDTO.getAuthorIds().stream().map(id -> authorService.findById(id)).toList());
        return new ResponseEntity<FullBookDTO>(FullBookDTO.fromEntity(bookService.create(book)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FullBookDTO> update(@PathVariable Long id, @Valid @RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(FullBookDTO.fromEntity(bookService.update(id, bookDTO.toEntity())));
    }
}
