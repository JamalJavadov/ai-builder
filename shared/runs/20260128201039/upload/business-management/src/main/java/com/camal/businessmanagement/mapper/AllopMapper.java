package com.camal.businessmanagement.mapper;

import com.camal.businessmanagement.dto.product.allop.AllopRequestDto;
import com.camal.businessmanagement.dto.product.allop.AllopResponseDto;
import com.camal.businessmanagement.entity.Allop;
import java.util.*;
import java.util.stream.Collectors;
import org.mapstruct.*;

@jakarta.annotation.Generated("java-project-crud.py")
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        builder = @org.mapstruct.Builder(disableBuilder = true)
)
public interface AllopMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "version", ignore = true)
    Allop toEntity(AllopRequestDto dto);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateFromRequest(@MappingTarget Allop entity, AllopRequestDto dto);


    AllopResponseDto toDto(Allop entity);


}
