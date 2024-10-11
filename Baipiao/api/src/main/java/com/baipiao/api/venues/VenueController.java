package com.baipiao.api.venues;

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
@Tag(name = "Venues", description = "REST endpoints for managing venues")
public class VenueController {

    private final VenueRepository repository;

    @Autowired
    public VenueController(VenueRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieve a list of all venues.
     *
     * @return List of all venues in the repository.
     */
    @Operation(summary = "Get all venues", description = "Retrieve a list of all venues.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of venues")
    @GetMapping("/venues")
    public ResponseEntity<List<Venue>> all() {
        List<Venue> venues = repository.findAll();
        return ResponseEntity.ok(venues);
    }

    /**
     * Add a new venue to the repository.
     *
     * @param newVenue Venue object containing the details of the new venue.
     * @return The saved Venue object.
     */
    @Operation(summary = "Create a new venue", description = "Create and save a new venue with the provided details.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Venue successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping("/venues")
    public ResponseEntity<Venue> newVenue(@Valid @RequestBody Venue newVenue) {
        Venue savedVenue = repository.save(newVenue);
        return ResponseEntity.status(201).body(savedVenue);
    }

    /**
     * Retrieve a specific venue by providing its ID.
     *
     * @param id ID of the venue to be retrieved.
     * @return The Venue object with the specified ID.
     */
    @Operation(summary = "Get a venue by ID", description = "Retrieve a specific venue by providing its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the venue"),
            @ApiResponse(responseCode = "404", description = "Venue not found")
    })
    @GetMapping("/venues/{id}")
    public Venue one(@Parameter(description = "ID of the venue to be retrieved") @PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new VenueNotFoundException(id));
    }

    /**
     * Update an existing venue or create a new one if the specified venue ID doesn't exist.
     *
     * @param newVenue Venue object containing updated details.
     * @param id ID of the venue to be updated.
     * @return The updated or newly created Venue object.
     */
    @Operation(summary = "Update an existing venue", description = "Update the details of an existing venue or create a new one if it doesn't exist.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Venue successfully updated"),
            @ApiResponse(responseCode = "201", description = "Venue created as it did not exist"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PutMapping("/venues/{id}")
    public ResponseEntity<Venue> replaceVenue(@Valid @RequestBody Venue newVenue, @PathVariable Long id) {
        return repository.findById(id)
                .map(venue -> {
                    // Update fields
                    venue.setName(newVenue.getName());
                    venue.setDescription(newVenue.getDescription());
                    venue.setLocation(newVenue.getLocation());
                    venue.setUpdatedAt(newVenue.getUpdatedAt());
                    Venue updatedVenue = repository.save(venue);
                    return ResponseEntity.ok(updatedVenue);
                })
                .orElseGet(() -> {
                    // If venue doesn't exist, create a new one
                    newVenue.setId(id);
                    Venue savedVenue = repository.save(newVenue);
                    return ResponseEntity.status(201).body(savedVenue);
                });
    }

    /**
     * Delete a venue by ID.
     *
     * @param id ID of the venue to be deleted.
     */
    @Operation(summary = "Delete a venue", description = "Delete a venue by providing its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Venue successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Venue not found")
    })
    @DeleteMapping("/venues/{id}")
    public ResponseEntity<Object> deleteVenue(@PathVariable Long id) {
        return repository.findById(id)
                .map(venue -> {
                    repository.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new VenueNotFoundException(id));
    }
}