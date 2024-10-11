package com.baipiao.api.events;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Events", description = "REST endpoints for managing events")
public class EventController {

    private final EventRepository repository;

    @Autowired
    public EventController(EventRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieve a list of all events.
     *
     * @return List of all events in the repository.
     */
    @Operation(summary = "Get all events", description = "Retrieve a list of all events.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of events")
    @GetMapping("/events")
    public ResponseEntity<List<Event>> getEvents() {
        List<Event> events = repository.findAll();
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
    public ResponseEntity<Event> newEvent(@Valid @RequestBody Event newEvent) {
        Event savedEvent = repository.save(newEvent);
        return ResponseEntity.status(201).body(savedEvent);
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
    public Event one(@Parameter(description = "ID of the event to be retrieved") @PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
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
            @ApiResponse(responseCode = "201", description = "Event created as it did not exist"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PutMapping("/events/{id}")
    public ResponseEntity<Event> replaceEvent(@Valid @RequestBody Event newEvent, @PathVariable Long id) {

        return repository.findById(id)
                .map(event -> {
                    // Update fields
                    event.setName(newEvent.getName());
                    event.setRegistrationRequired(newEvent.isRegistrationRequired());
                    event.setDetails(newEvent.getDetails());
                    event.setContactEmail(newEvent.getContactEmail());
                    event.setContactPhoneNumber(newEvent.getContactPhoneNumber());
                    event.setCapacity(newEvent.getCapacity());
                    event.setStatus(newEvent.getStatus());
                    event.setRegistrationLink(newEvent.getRegistrationLink());
                    Event updatedEvent = repository.save(event);
                    return ResponseEntity.ok(updatedEvent);
                })
                .orElseGet(() -> {
                    // If event doesn't exist, create a new one
                    newEvent.setId(id);
                    Event savedEvent = repository.save(newEvent);
                    return ResponseEntity.status(201).body(savedEvent);
                });
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
    public ResponseEntity<Object> deleteEvent(@PathVariable Long id) {
        return repository.findById(id)
                .map(event -> {
                    repository.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new EventNotFoundException(id));
    }
}