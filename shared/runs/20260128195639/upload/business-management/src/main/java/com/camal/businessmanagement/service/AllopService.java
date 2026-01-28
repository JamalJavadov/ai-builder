package com.camal.businessmanagement.service;

import com.camal.businessmanagement.dto.product.allop.AllopRequestDto;
import com.camal.businessmanagement.dto.product.allop.AllopResponseDto;
import com.camal.businessmanagement.entity.Allop;
import com.camal.businessmanagement.exception.AllopNotFound;
import com.camal.businessmanagement.mapper.AllopMapper;
import com.camal.businessmanagement.repository.AllopRepository;
import com.camal.businessmanagement.spec.AllopSpecifications;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@jakarta.annotation.Generated("java-project-crud.py")
@Service
@RequiredArgsConstructor
@Transactional
public class AllopService {

    private final AllopRepository repository;
private final AllopMapper mapper;

    public AllopResponseDto create(AllopRequestDto dto) {
        Allop entity = mapper.toEntity(dto);

        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    public AllopResponseDto update(Long id, AllopRequestDto dto) {
        Allop entity = repository.findById(id)
                .orElseThrow(() -> new AllopNotFound(id));

        mapper.updateFromRequest(entity, dto);

        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public AllopResponseDto getById(Long id) {
        Allop entity = repository.findById(id)
                .orElseThrow(() -> new AllopNotFound(id));
        return mapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public List<AllopResponseDto> getAll() {
        var spec = AllopSpecifications.notDeleted();
        return repository.findAll(spec).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public void delete(Long id) {
        Allop entity = repository.findById(id)
                .orElseThrow(() -> new AllopNotFound(id));
        entity.setDeleted(true);
        repository.save(entity);
    }
}
