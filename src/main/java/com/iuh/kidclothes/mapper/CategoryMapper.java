package com.iuh.kidclothes.mapper;

import com.iuh.kidclothes.dto.request.CategoryCreationRequest;
import com.iuh.kidclothes.dto.respone.CategoryRespone;
import com.iuh.kidclothes.entity.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryCreationRequest request);

    CategoryRespone toCategoryRespone(Category category);

    List<CategoryRespone> toCategoryResponeList(List<Category> categories);
}

