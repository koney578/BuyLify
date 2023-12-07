package com.buylify.buylifyapp.category;

import com.buylify.buylifyapp.mappers.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    public void addCategory(CategoryDto categoryDto) {
        categoryRepository.save(categoryMapper.toEntity(categoryDto));
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public void editCategory(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id).orElseThrow();
        category.setName(categoryDto.getName());
        categoryRepository.save(category);
    }
}
