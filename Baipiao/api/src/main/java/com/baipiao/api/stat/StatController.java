package com.baipiao.api.stat;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.baipiao.api.stat.dto.*;

import java.util.List;

@RestController
@Tag(name = "Stat", description = "REST endpoints for managing tatistics")
public class StatController {

    @Autowired
    private final StatService statService;

    public StatController(StatService statService) {
        this.statService = statService;
    }
    /**
     * Retrieve a list of all categories.
     *
     * @return List of all categories in the repository.
     */
    @GetMapping("/eventcategories")
    public ResponseEntity<List<CategoryStat>> eventCategoriesCount() {
        List<CategoryStat> stats = statService.getCategoryStat();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/dailyEvents")
    public ResponseEntity<List<DailyStat>> dailyStats() {
        List<DailyStat> stats = statService.getDailyStat();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/eventorganizers")
    public ResponseEntity<List<OrganizerStat>> eventOrganizersCount() {
        List<OrganizerStat> stats = statService.getOrganizerStat();
        return ResponseEntity.ok(stats);
    }


   
}