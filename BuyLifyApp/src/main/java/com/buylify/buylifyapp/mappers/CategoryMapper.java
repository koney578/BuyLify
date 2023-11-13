package com.buylify.buylifyapp.mappers;

import com.buylify.buylifyapp.category.Category;
import com.buylify.buylifyapp.category.CategoryDto;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {

    CategoryDto toDto(Category category);
}
