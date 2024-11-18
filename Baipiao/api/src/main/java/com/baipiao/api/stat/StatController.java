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

    @GetMapping("/userscount")
    public ResponseEntity<Integer> usersCount() {
        Integer stats = statService.getUserCount();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/organizationscount")
    public ResponseEntity<Integer> organizationsCount() {
        Integer stats = statService.getOrganizationsCount();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/eventscount")
    public ResponseEntity<Integer> eventsCount() {
        Integer stats = statService.getEventsCount();
        return ResponseEntity.ok(stats);
    }
    @GetMapping("/avgEventCapacity")
    public ResponseEntity<Double> avgEventCapacity() {
        Double stats = statService.getAvgEventCapacity();
        return ResponseEntity.ok(stats);
    }
    @GetMapping("/minEventCapacity")
    public ResponseEntity<Integer> minEventCapacity() {
        Integer stats = statService.getMinEventCapacity();
        return ResponseEntity.ok(stats);
    }
    @GetMapping("/maxEventCapacity")
    public ResponseEntity<Integer> maxEventCapacity() {
        Integer stats = statService.getMaxEventCapacity();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/venuesCount")
    public ResponseEntity<Integer> venuesCount() {
        Integer stats = statService.getVenuesCount();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/eventsCountToday")
    public ResponseEntity<Integer> eventsCountToday() {
        Integer stats = statService.getEventsCountToday();
        return ResponseEntity.ok(stats);
    }
    @GetMapping("/eventsCountThisWeek")
    public ResponseEntity<Integer> eventsCountThisWeek() {
        Integer stats = statService.getEventsCountThisWeek();
        return ResponseEntity.ok(stats);
    }
    @GetMapping("/eventsCountNextWeek")
    public ResponseEntity<Integer> eventsCountNextWeek() {
        Integer stats = statService.getEventsCountNextWeek();
        return ResponseEntity.ok(stats);
    }
   
}