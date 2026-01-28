package com.camal.businessmanagement.mapper;

import com.camal.businessmanagement.dto.product.guard.GuardCreateRequestDto;
import com.camal.businessmanagement.dto.product.guard.GuardPatchRequestDto;
import com.camal.businessmanagement.dto.product.guard.GuardResponseDto;
import com.camal.businessmanagement.dto.product.guard.GuardUpdateRequestDto;
import com.camal.businessmanagement.entity.Guard;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-24T21:21:09+0400",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.2.1.jar, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class GuardMapperImpl implements GuardMapper {

    @Override
    public Guard toEntity(GuardCreateRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Guard guard = new Guard();

        guard.setUrl( dto.getUrl() );
        guard.setProductName( dto.getProductName() );
        guard.setBoughtPrice( dto.getBoughtPrice() );
        guard.setSellPrice( dto.getSellPrice() );
        guard.setDescription( dto.getDescription() );

        return guard;
    }

    @Override
    public void updateFromUpdateDto(Guard entity, GuardUpdateRequestDto dto) {
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
    public void updateFromPatchDto(Guard entity, GuardPatchRequestDto dto) {
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
    public GuardResponseDto toDto(Guard entity) {
        if ( entity == null ) {
            return null;
        }

        GuardResponseDto guardResponseDto = new GuardResponseDto();

        guardResponseDto.setId( entity.getId() );
        guardResponseDto.setVersion( entity.getVersion() );
        guardResponseDto.setUrl( entity.getUrl() );
        guardResponseDto.setProductName( entity.getProductName() );
        guardResponseDto.setBoughtPrice( entity.getBoughtPrice() );
        guardResponseDto.setSellPrice( entity.getSellPrice() );
        guardResponseDto.setDescription( entity.getDescription() );
        guardResponseDto.setCreatedAt( entity.getCreatedAt() );
        guardResponseDto.setUpdatedAt( entity.getUpdatedAt() );

        return guardResponseDto;
    }
}
