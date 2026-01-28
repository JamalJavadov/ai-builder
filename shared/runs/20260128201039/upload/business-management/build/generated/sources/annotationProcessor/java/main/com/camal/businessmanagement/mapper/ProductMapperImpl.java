package com.camal.businessmanagement.mapper;

import com.camal.businessmanagement.dto.product.product.ProductCreateRequestDto;
import com.camal.businessmanagement.dto.product.product.ProductPatchRequestDto;
import com.camal.businessmanagement.dto.product.product.ProductResponseDto;
import com.camal.businessmanagement.dto.product.product.ProductUpdateRequestDto;
import com.camal.businessmanagement.entity.Product;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-24T21:21:09+0400",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.2.1.jar, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product toEntity(ProductCreateRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Product product = new Product();

        product.setUrl( dto.getUrl() );
        product.setProductName( dto.getProductName() );
        product.setBoughtPrice( dto.getBoughtPrice() );
        product.setSellPrice( dto.getSellPrice() );
        product.setDescription( dto.getDescription() );

        return product;
    }

    @Override
    public void updateFromUpdateDto(Product entity, ProductUpdateRequestDto dto) {
        if ( dto == null ) {
            return;
        }

        entity.setUrl( dto.getUrl() );
        entity.setProductName( dto.getProductName() );
        entity.setBoughtPrice( dto.getBoughtPrice() );
        entity.setSellPrice( dto.getSellPrice() );
        entity.setDescription( dto.getDescription() );
    }

    @Override
    public void updateFromPatchDto(Product entity, ProductPatchRequestDto dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getUrl() != null ) {
            entity.setUrl( dto.getUrl() );
        }
        if ( dto.getProductName() != null ) {
            entity.setProductName( dto.getProductName() );
        }
        if ( dto.getBoughtPrice() != null ) {
            entity.setBoughtPrice( dto.getBoughtPrice() );
        }
        if ( dto.getSellPrice() != null ) {
            entity.setSellPrice( dto.getSellPrice() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
    }

    @Override
    public ProductResponseDto toDto(Product entity) {
        if ( entity == null ) {
            return null;
        }

        ProductResponseDto productResponseDto = new ProductResponseDto();

        productResponseDto.setId( entity.getId() );
        productResponseDto.setVersion( entity.getVersion() );
        productResponseDto.setUrl( entity.getUrl() );
        productResponseDto.setProductName( entity.getProductName() );
        productResponseDto.setBoughtPrice( entity.getBoughtPrice() );
        productResponseDto.setSellPrice( entity.getSellPrice() );
        productResponseDto.setDescription( entity.getDescription() );
        productResponseDto.setCreatedAt( entity.getCreatedAt() );
        productResponseDto.setUpdatedAt( entity.getUpdatedAt() );

        return productResponseDto;
    }
}
