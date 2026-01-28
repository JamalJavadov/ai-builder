package com.camal.businessmanagement.mapper;

import com.camal.businessmanagement.dto.product.test.TestCreateRequestDto;
import com.camal.businessmanagement.dto.product.test.TestPatchRequestDto;
import com.camal.businessmanagement.dto.product.test.TestResponseDto;
import com.camal.businessmanagement.dto.product.test.TestUpdateRequestDto;
import com.camal.businessmanagement.entity.Test;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-24T21:21:09+0400",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.2.1.jar, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class TestMapperImpl implements TestMapper {

    @Override
    public Test toEntity(TestCreateRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Test test = new Test();

        test.setUrl( dto.getUrl() );
        test.setProductName( dto.getProductName() );
        test.setBoughtPrice( dto.getBoughtPrice() );
        test.setSellPrice( dto.getSellPrice() );
        test.setDescription( dto.getDescription() );

        return test;
    }

    @Override
    public void updateFromUpdateDto(Test entity, TestUpdateRequestDto dto) {
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
    public void updateFromPatchDto(Test entity, TestPatchRequestDto dto) {
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
    public TestResponseDto toDto(Test entity) {
        if ( entity == null ) {
            return null;
        }

        TestResponseDto testResponseDto = new TestResponseDto();

        testResponseDto.setId( entity.getId() );
        testResponseDto.setVersion( entity.getVersion() );
        testResponseDto.setUrl( entity.getUrl() );
        testResponseDto.setProductName( entity.getProductName() );
        testResponseDto.setBoughtPrice( entity.getBoughtPrice() );
        testResponseDto.setSellPrice( entity.getSellPrice() );
        testResponseDto.setDescription( entity.getDescription() );
        testResponseDto.setCreatedAt( entity.getCreatedAt() );
        testResponseDto.setUpdatedAt( entity.getUpdatedAt() );

        return testResponseDto;
    }
}
