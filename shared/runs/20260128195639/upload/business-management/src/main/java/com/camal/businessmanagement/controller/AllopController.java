package com.camal.businessmanagement.controller;

import com.camal.businessmanagement.dto.product.allop.AllopRequestDto;
import com.camal.businessmanagement.dto.product.allop.AllopResponseDto;
import com.camal.businessmanagement.service.AllopService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@jakarta.annotation.Generated("java-project-crud.py")
@RestController
@RequestMapping("/api/v1/allops")
public class AllopController {

    private final AllopService service;

    public AllopController(AllopService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AllopResponseDto> create(@Valid @RequestBody AllopRequestDto request) {
        AllopResponseDto response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public AllopResponseDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<AllopResponseDto> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public AllopResponseDto update(@PathVariable Long id, @Valid @RequestBody AllopRequestDto request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
