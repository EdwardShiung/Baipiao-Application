package com.baipiao.api.organizations;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.baipiao.api.organizations.dto.OrganizationCreateDTO;
import com.baipiao.api.organizations.dto.OrganizationDTO;

import java.util.List;

@RestController
@Tag(name = "Organizations", description = "REST endpoints for managing organizations")
public class OrganizationController {

    @Autowired
    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }
    /**
     * Retrieve a list of all organizations.
     *
     * @return List of all organizations in the repository.
     */
    @Operation(summary = "Get all organizations", description = "Retrieve a list of all organizations.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of organizations")
    @GetMapping("/organizations")
    public ResponseEntity<List<OrganizationDTO>> all() {
        List<OrganizationDTO> organizations = organizationService.getAll();
        return ResponseEntity.ok(organizations);
    }

    /**
     * Add a new organization to the repository.
     *
     * @param newOrganization Organization object containing the details of the new
     *                    organization.
     * @return The saved Organization object.
     */
    @Operation(summary = "Create a new organization", description = "Create and save a new organization with the provided details.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Organization successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping("/organizations")
    public ResponseEntity<Void> newOrganization(@Valid @RequestBody OrganizationCreateDTO newOrganization) {
        organizationService.save(newOrganization);
        return ResponseEntity.status(201).build();
    }

    /**
     * Retrieve a specific organization by providing its ID.
     *
     * @param id ID of the organization to be retrieved.
     * @return The Organization object with the specified ID.
     */
    @Operation(summary = "Get a organization by ID", description = "Retrieve a specific organization by providing its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the organization"),
            @ApiResponse(responseCode = "404", description = "Organization not found")
    })
    @GetMapping("/organizations/{id}")
    public ResponseEntity<OrganizationDTO> one(
            @Parameter(description = "ID of the organization to be retrieved") @PathVariable Long id) {

        OrganizationDTO organization = organizationService.find(id);

        if (organization == null) {
            throw new OrganizationNotFoundException(id); // Throw the exception instead of returning it
        } else {
            return ResponseEntity.ok(organization); // Properly build the response entity with the body
        }
    }

    /**
     * Update an existing organization or create a new one if the specified organization ID
     * doesn't exist.
     *
     * @param newOrganization Organization object containing updated details.
     * @param id          ID of the organization to be updated.
     * @return The updated or newly created Organization object.
     */
    @Operation(summary = "Update an existing organization", description = "Update the details of an existing organization or create a new one if it doesn't exist.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Organization successfully updated"),
            // @ApiResponse(responseCode = "201", description = "Organization created as it did
            // not exist"),
            // @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Organization not found")
    })
    @PutMapping("/organizations/{id}")
    public ResponseEntity<Void> replaceOrganization(
            @Valid @RequestBody OrganizationCreateDTO newOrganization,
            @PathVariable Long id) {

        organizationService.update(newOrganization, id);
        return ResponseEntity.ok().build(); // Return 200 OK with the updated organization
    }

    /**
     * Delete a organization by ID.
     *
     * @param id ID of the organization to be deleted.
     */
    @Operation(summary = "Delete a organization", description = "Delete a organization by providing its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Organization successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Organization not found")
    })
    @DeleteMapping("/organizations/{id}")
    public ResponseEntity<Void> deleteOrganization(@PathVariable Long id) {
        organizationService.deleteById(id);
        return ResponseEntity.status(204).build();
    }
}