package com.baipiao.api.categories;

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
@Tag(name = "Categories", description = "REST endpoints for managing categories")
public class CategoryController {

    private final CategoryRepository repository;

    @Autowired
    public CategoryController(CategoryRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieve a list of all categories.
     *
     * @return List of all categories in the repository.
     */
    @Operation(summary = "Get all categories", description = "Retrieve a list of all categories.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of categories")
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> all() {
        List<Category> categories = repository.findAll();
        return ResponseEntity.ok(categories);
    }

    /**
     * Add a new category to the repository.
     *
     * @param newCategory Category object containing the details of the new category.
     * @return The saved Category object.
     */
    @Operation(summary = "Create a new category", description = "Create and save a new category with the provided details.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Category successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping("/categories")
    public ResponseEntity<Category> newCategory(@Valid @RequestBody Category newCategory) {
        Category savedCategory = repository.save(newCategory);
        return ResponseEntity.status(201).body(savedCategory);
    }

    /**
     * Retrieve a specific category by providing its ID.
     *
     * @param id ID of the category to be retrieved.
     * @return The Category object with the specified ID.
     */
    @Operation(summary = "Get a category by ID", description = "Retrieve a specific category by providing its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the category"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @GetMapping("/categories/{id}")
    public Category one(@Parameter(description = "ID of the category to be retrieved") @PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    /**
     * Update an existing category or create a new one if the specified category ID doesn't exist.
     *
     * @param newCategory Category object containing updated details.
     * @param id ID of the category to be updated.
     * @return The updated or newly created Category object.
     */
    @Operation(summary = "Update an existing category", description = "Update the details of an existing category or create a new one if it doesn't exist.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category successfully updated"),
            @ApiResponse(responseCode = "201", description = "Category created as it did not exist"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PutMapping("/categories/{id}")
    public ResponseEntity<Category> replaceCategory(@Valid @RequestBody Category newCategory, @PathVariable Long id) {

        return repository.findById(id)
                .map(category -> {
                    // Update fields
                    category.setName(newCategory.getName());
                    category.setDescription(newCategory.getDescription());
                    Category updatedCategory = repository.save(category);
                    return ResponseEntity.ok(updatedCategory);
                })
                .orElseGet(() -> {
                    // If category doesn't exist, create a new one
                    newCategory.setId(id);
                    Category savedCategory = repository.save(newCategory);
                    return ResponseEntity.status(201).body(savedCategory);
                });
    }

    /**
     * Delete a category by ID.
     *
     * @param id ID of the category to be deleted.
     */
    @Operation(summary = "Delete a category", description = "Delete a category by providing its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Category successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable Long id) {
        return repository.findById(id)
                .map(category -> {
                    repository.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }
}