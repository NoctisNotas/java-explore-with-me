package ru.practicum.explorewithme.mapper;

import ru.practicum.explorewithme.dto.category.CategoryDto;
import ru.practicum.explorewithme.dto.category.NewCategoryDto;
import ru.practicum.explorewithme.model.Category;

public class CategoryMapper {

    public static Category toCategory(NewCategoryDto newCategoryDto) {
        Category category = new Category();
        category.setName(newCategoryDto.getName());
        return category;
    }

    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName()
        );
    }

    public static Category updateCategoryFromDto(CategoryDto categoryDto, Category category) {
        category.setName(categoryDto.getName());
        return category;
    }
}