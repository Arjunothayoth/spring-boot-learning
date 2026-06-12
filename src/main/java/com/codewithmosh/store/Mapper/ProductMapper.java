package com.codewithmosh.store.Mapper;

import com.codewithmosh.store.Dtos.ProductDto;
import com.codewithmosh.store.Dtos.ProductRequest;
import com.codewithmosh.store.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "categoryId", source = "category.id")
    ProductDto productDto(Product product);

    Product toEntity(ProductDto productDto);

    @Mapping(target = "id",ignore = true)
    void update(ProductDto productDto, @MappingTarget Product product);
}
