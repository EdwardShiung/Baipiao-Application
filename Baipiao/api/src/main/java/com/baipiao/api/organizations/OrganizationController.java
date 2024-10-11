package com.baipiao.api.organizations;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
@RestController
@Tag(name = "Organizations", description = "REST endpoints for managing Organizations")
public class OrganizationController {

    private final OrganizationRepository repository;

    @Autowired
    public OrganizationController(OrganizationRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieve a list of all organizers.
     *
     * @return List of all organizers in the repository.
     */
    @Operation(summary = "Get all organizers", description = "Retrieve a list of all organizers.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of organizers")
    @GetMapping("/organizers")
    public ResponseEntity<List<Organization>> all() {
        List<Organization> organizers = repository.findAll();
        return ResponseEntity.ok(organizers);
    }

    /**
     * Add a new organizer to the repository.
     *
     * @param newOrganization Organization object containing the details of the new organizer.
     * @return The saved Organization object.
     */
    @Operation(summary = "Create a new organizer", description = "Create and save a new organizer with the provided details.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Organization successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping("/organizers")
    public ResponseEntity<Organization> newOrganization(@Valid @RequestBody Organization newOrganization) {
        Organization savedOrganization = repository.save(newOrganization);
        return ResponseEntity.status(201).body(savedOrganization);
    }

    /**
     * Retrieve a specific organizer by providing its ID.
     *
     * @param id ID of the organizer to be retrieved.
     * @return The Organization object with the specified ID.
     */
    @Operation(summary = "Get an organizer by ID", description = "Retrieve a specific organizer by providing its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the organizer"),
            @ApiResponse(responseCode = "404", description = "Organization not found")
    })
    @GetMapping("/organizers/{id}")
    public Organization one(@Parameter(description = "ID of the organizer to be retrieved") @PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new OrganizationNotFoundException(id));
    }

    /**
     * Update an existing organizer or create a new one if the specified organizer ID doesn't exist.
     *
     * @param newOrganization Organization object containing updated details.
     * @param id ID of the organizer to be updated.
     * @return The updated or newly created Organization object.
     */
    @Operation(summary = "Update an existing organizer", description = "Update the details of an existing organizer or create a new one if it doesn't exist.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Organization successfully updated"),
            @ApiResponse(responseCode = "201", description = "Organization created as it did not exist"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PutMapping("/organizers/{id}")
    public ResponseEntity<Organization> replaceOrganization(@Valid @RequestBody Organization newOrganization, @PathVariable Long id) {

        return repository.findById(id)
                .map(organizer -> {
                    // Update fields
                    organizer.setName(newOrganization.getName());
                    organizer.setDescription(newOrganization.getDescription());
                    organizer.setEmail(newOrganization.getEmail());
                    organizer.setPhoneno(newOrganization.getPhoneno());
                    Organization updatedOrganization = repository.save(organizer);
                    return ResponseEntity.ok(updatedOrganization);
                })
                .orElseGet(() -> {
                    // If organizer doesn't exist, create a new one
                    newOrganization.setId(id);
                    Organization savedOrganization = repository.save(newOrganization);
                    return ResponseEntity.status(201).body(savedOrganization);
                });
    }

    /**
     * Delete an organizer by ID.
     *
     * @param id ID of the organizer to be deleted.
     */
    @Operation(summary = "Delete an organizer", description = "Delete an organizer by providing its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Organization successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Organization not found")
    })
    @DeleteMapping("/organizers/{id}")
    public ResponseEntity<Object> deleteOrganization(@PathVariable Long id) {
        return repository.findById(id)
                .map(organizer -> {
                    repository.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new OrganizationNotFoundException(id));
    }
}