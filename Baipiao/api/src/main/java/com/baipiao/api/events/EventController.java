package com.baipiao.api.events;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.baipiao.api.events.dto.EventDTO;
import com.baipiao.api.events.dto.EventCreateDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Events", description = "REST endpoints for managing events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Retrieve a list of all events.
     *
     * @return List of all events in the repository.
     */
    @Operation(summary = "Get all events", description = "Retrieve a list of all events.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of events")
    @GetMapping("/events")
    public ResponseEntity<List<EventDTO>> getEvents() {
        List<EventDTO> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    /**
     * Add a new event to the repository.
     *
     * @param newEvent Event object containing the details of the new event.
     * @return The saved Event object.
     */
    @Operation(summary = "Create a new event", description = "Create and save a new event with the provided details.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Event successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping("/events")
    public ResponseEntity<Void> newEvent(@Valid @RequestBody EventCreateDTO newEvent) {
        eventService.save(newEvent);
        return ResponseEntity.status(201).build(); // 201 Created
        
    }

    /**
     * Retrieve a specific event by providing its ID.
     *
     * @param id ID of the event to be retrieved.
     * @return The Event object with the specified ID.
     */
    @Operation(summary = "Get an event by ID", description = "Retrieve a specific event by providing its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the event"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @GetMapping("/events/{id}")
    public ResponseEntity<EventDTO> one(@Parameter(description = "ID of the event to be retrieved") @PathVariable Long id) {
        EventDTO event = eventService.find(id);

        if (event == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(event); // Properly build the response entity with the body
        }   
    }

    /**
     * Update an existing event or create a new one if the specified event ID doesn't exist.
     *
     * @param newEvent Event object containing updated details.
     * @param id ID of the event to be updated.
     * @return The updated or newly created Event object.
     */
    @Operation(summary = "Update an existing event", description = "Update the details of an existing event or create a new one if it doesn't exist.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Event successfully updated"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @PutMapping("/events/{id}")
    public ResponseEntity<Void> replaceEvent(@Valid @RequestBody EventCreateDTO newEvent, @PathVariable Long id) {
        try {
            eventService.update(id, newEvent);
            return ResponseEntity.status(200).build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        
    }

    /**
     * Delete an event by ID.
     *
     * @param id ID of the event to be deleted.
     */
    @Operation(summary = "Delete an event", description = "Delete an event by providing its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Event successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @DeleteMapping("/events/{id}")
    public ResponseEntity<EventDTO> deleteEvent(@PathVariable Long id) {
        if(eventService.existsById(id)) {
            EventDTO event = eventService.deleteById(id);
            return ResponseEntity.status(204).body(event);
        }else {
            return ResponseEntity.notFound().build();
        }    
    }
}