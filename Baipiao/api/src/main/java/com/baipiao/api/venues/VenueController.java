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

import com.baipiao.api.venues.dto.VenueCreateDTO;
import com.baipiao.api.venues.dto.VenueDTO;

import java.util.List;

@RestController
@Tag(name = "Venues", description = "REST endpoints for managing venues")
public class VenueController {

    @Autowired
    private final VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }
    /**
     * Retrieve a list of all venues.
     *
     * @return List of all venues in the repository.
     */
    @Operation(summary = "Get all venues", description = "Retrieve a list of all venues.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of venues")
    @GetMapping("/venues")
    public ResponseEntity<List<VenueDTO>> all() {
        List<VenueDTO> venues = venueService.getAll();
        return ResponseEntity.ok(venues);
    }

    /**
     * Add a new venue to the repository.
     *
     * @param newVenue Venue object containing the details of the new
     *                    venue.
     * @return The saved Venue object.
     */
    @Operation(summary = "Create a new venue", description = "Create and save a new venue with the provided details.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Venue successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping("/venues")
    public ResponseEntity<Void> newVenue(@Valid @RequestBody VenueCreateDTO newVenue) {
        venueService.save(newVenue);
        return ResponseEntity.status(201).build();
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
    public ResponseEntity<VenueDTO> one(
            @Parameter(description = "ID of the venue to be retrieved") @PathVariable Long id) {

        VenueDTO venue = venueService.find(id);

        if (venue == null) {
            throw new VenueNotFoundException(id); // Throw the exception instead of returning it
        } else {
            return ResponseEntity.ok(venue); // Properly build the response entity with the body
        }
    }

    /**
     * Update an existing venue or create a new one if the specified venue ID
     * doesn't exist.
     *
     * @param newVenue Venue object containing updated details.
     * @param id          ID of the venue to be updated.
     * @return The updated or newly created Venue object.
     */
    @Operation(summary = "Update an existing venue", description = "Update the details of an existing venue or create a new one if it doesn't exist.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Venue successfully updated"),
            // @ApiResponse(responseCode = "201", description = "Venue created as it did
            // not exist"),
            // @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Venue not found")
    })
    @PutMapping("/venues/{id}")
    public ResponseEntity<Void> replaceVenue(
            @Valid @RequestBody VenueCreateDTO newVenue,
            @PathVariable Long id) {

        venueService.update(newVenue, id);
        return ResponseEntity.ok().build(); // Return 200 OK with the updated venue
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
    public ResponseEntity<Void> deleteVenue(@PathVariable Long id) {
        venueService.deleteById(id);
        return ResponseEntity.status(204).build();
    }
}