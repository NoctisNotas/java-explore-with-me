package ru.practicum.explorewithme.controller.open;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.dto.category.CategoryDto;
import ru.practicum.explorewithme.service.CategoryService;
import ru.practicum.explorewithme.service.StatsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OpenCategoryController {

    private final CategoryService categoryService;
    private final StatsService statsService;

    @GetMapping("/categories")
    public List<CategoryDto> getCategories(
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size,
            HttpServletRequest request) {

        statsService.saveHit("main-service", request.getRequestURI(), request.getRemoteAddr());
        return categoryService.getCategories(from, size);
    }

    @GetMapping("/categories/{catId}")
    public CategoryDto getCategory(
            @PathVariable Long catId,
            HttpServletRequest request) {

        statsService.saveHit("main-service", request.getRequestURI(), request.getRemoteAddr());
        return categoryService.getCategory(catId);
    }
}