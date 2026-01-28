package com.camal.businessmanagement.mapper;

import com.camal.businessmanagement.dto.product.allop.AllopRequestDto;
import com.camal.businessmanagement.dto.product.allop.AllopResponseDto;
import com.camal.businessmanagement.entity.Allop;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-24T21:21:09+0400",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.2.1.jar, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class AllopMapperImpl implements AllopMapper {

    @Override
    public Allop toEntity(AllopRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Allop allop = new Allop();

        allop.setUrl( dto.getUrl() );
        allop.setProductName( dto.getProductName() );
        allop.setBoughtPrice( dto.getBoughtPrice() );
        allop.setSellPrice( dto.getSellPrice() );
        allop.setDescription( dto.getDescription() );

        return allop;
    }

    @Override
    public void updateFromRequest(Allop entity, AllopRequestDto dto) {
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
    public AllopResponseDto toDto(Allop entity) {
        if ( entity == null ) {
            return null;
        }

        AllopResponseDto allopResponseDto = new AllopResponseDto();

        allopResponseDto.setId( entity.getId() );
        allopResponseDto.setVersion( entity.getVersion() );
        allopResponseDto.setUrl( entity.getUrl() );
        allopResponseDto.setProductName( entity.getProductName() );
        allopResponseDto.setBoughtPrice( entity.getBoughtPrice() );
        allopResponseDto.setSellPrice( entity.getSellPrice() );
        allopResponseDto.setDescription( entity.getDescription() );
        allopResponseDto.setCreatedAt( entity.getCreatedAt() );
        allopResponseDto.setUpdatedAt( entity.getUpdatedAt() );

        return allopResponseDto;
    }
}
