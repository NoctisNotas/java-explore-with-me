package ru.practicum.explorewithme.controller.open;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.dto.compilation.CompilationDto;
import ru.practicum.explorewithme.service.CompilationService;
import ru.practicum.explorewithme.service.StatsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OpenCompilationController {

    private final CompilationService compilationService;
    private final StatsService statsService;

    @GetMapping("/compilations")
    public List<CompilationDto> getCompilations(
            @RequestParam(required = false) Boolean pinned,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size,
            HttpServletRequest request) {

        statsService.saveHit("main-service", request.getRequestURI(), request.getRemoteAddr());
        return compilationService.getCompilations(pinned, from, size);
    }

    @GetMapping("/compilations/{compId}")
    public CompilationDto getCompilation(
            @PathVariable Long compId,
            HttpServletRequest request) {

        statsService.saveHit("main-service", request.getRequestURI(), request.getRemoteAddr());
        return compilationService.getCompilation(compId);
    }
}