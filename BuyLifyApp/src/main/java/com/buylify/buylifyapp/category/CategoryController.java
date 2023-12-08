package com.buylify.buylifyapp.category;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PreAuthorize("hasAuthority('administrator')")
    @PostMapping
    public void addCategory(@RequestBody @Validated CategoryDto categoryDto) {
        categoryService.addCategory(categoryDto);
    }

    @PreAuthorize("hasAuthority('administrator')")
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
    }

    @PreAuthorize("hasAuthority('administrator')")
    @PutMapping("/{id}")
    public void editCategory(@PathVariable("id") Long id, @RequestBody @Validated CategoryDto categoryDto) {
        categoryService.editCategory(id, categoryDto);
    }
}
