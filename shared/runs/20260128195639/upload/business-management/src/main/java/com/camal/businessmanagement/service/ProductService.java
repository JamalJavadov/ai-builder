package com.camal.businessmanagement.service;

import com.camal.businessmanagement.dto.product.product.ProductCreateRequestDto;
import com.camal.businessmanagement.dto.product.product.ProductPatchRequestDto;
import com.camal.businessmanagement.dto.product.product.ProductResponseDto;
import com.camal.businessmanagement.dto.product.product.ProductUpdateRequestDto;
import com.camal.businessmanagement.entity.Product;
import com.camal.businessmanagement.exception.BadRequestException;
import com.camal.businessmanagement.exception.ConflictException;
import com.camal.businessmanagement.exception.ProductNotFound;
import com.camal.businessmanagement.mapper.ProductMapper;
import com.camal.businessmanagement.repository.ProductRepository;
import com.camal.businessmanagement.spec.ProductSpecifications;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;

/**
 * Service layer for Product operations.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ProductResponseDto create(ProductCreateRequestDto dto) {
        Product entity = mapper.toEntity(dto);

        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    public ProductResponseDto update(Long id, ProductUpdateRequestDto dto) {
        Product entity = repository.findById(id)
                .orElseThrow(() -> new ProductNotFound(id));

        if (dto.getVersion() == null) {
            throw new BadRequestException("version is required");
        }
        if (entity.getVersion() != null && !Objects.equals(entity.getVersion(), dto.getVersion())) {
            throw new ConflictException("version mismatch");
        }

        mapper.updateFromUpdateDto(entity, dto);

        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    public ProductResponseDto patch(Long id, ProductPatchRequestDto dto) {
        Product entity = repository.findById(id)
                .orElseThrow(() -> new ProductNotFound(id));

        if (dto.getVersion() == null) {
            throw new BadRequestException("version is required");
        }
        if (entity.getVersion() != null && !Objects.equals(entity.getVersion(), dto.getVersion())) {
            throw new ConflictException("version mismatch");
        }

        mapper.updateFromPatchDto(entity, dto);

        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public ProductResponseDto get(Long id) {
        Product entity = repository.findById(id)
                .orElseThrow(() -> new ProductNotFound(id));
        return mapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponseDto> list(Map<String, String> params, Pageable pageable) {
        var spec = ProductSpecifications.fromParams(params);
        return repository.findAll(spec, pageable).map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponseDto> search(Map<String, String> params, Pageable pageable) {
        return list(params, pageable);
    }

    public void delete(Long id) {
        Product entity = repository.findById(id)
                .orElseThrow(() -> new ProductNotFound(id));
        entity.setDeleted(true);
        repository.save(entity);
    }
}
