package com.iuh.kidclothes.mapper;

import com.iuh.kidclothes.dto.request.ProductCreationRequest;
import com.iuh.kidclothes.dto.request.ProductUpdateRequest;
import com.iuh.kidclothes.dto.respone.ProductRespone;
import com.iuh.kidclothes.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(ProductCreationRequest request);

    ProductRespone toProductRespone(Product product);

    List<ProductRespone> toProductResponeList(List<Product> products);

    void updateProduct(@MappingTarget Product product, ProductUpdateRequest request);
}