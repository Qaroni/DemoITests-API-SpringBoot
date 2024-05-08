package com.qaroni.demoitests.infrastructure.gateway;

import com.qaroni.demoitests.domain.dto.AuthorDTO;
import com.qaroni.demoitests.domain.dto.FullAuthorDTO;
import com.qaroni.demoitests.infrastructure.ports.inbound.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping()
    public ResponseEntity<List<FullAuthorDTO>> findAll() {
        return ResponseEntity.ok(authorService.findAll().stream().map(FullAuthorDTO::fromEntity).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullAuthorDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(FullAuthorDTO.fromEntity(authorService.findById(id)));
    }

    @PostMapping()
    public ResponseEntity<FullAuthorDTO> create(@Valid @RequestBody AuthorDTO author) {
        return new ResponseEntity<FullAuthorDTO>(FullAuthorDTO.fromEntity(authorService.create(author.toEntity())), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FullAuthorDTO> update(@PathVariable Long id, @Valid @RequestBody AuthorDTO author) {
        return ResponseEntity.ok(FullAuthorDTO.fromEntity(authorService.update(id, author.toEntity())));
    }
}
