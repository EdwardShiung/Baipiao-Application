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

import com.baipiao.api.categories.dto.CategoryCreateDTO;
import com.baipiao.api.categories.dto.CategoryDTO;

import java.util.List;

@RestController
@Tag(name = "Categories", description = "REST endpoints for managing categories")
public class CategoryController {

    @Autowired
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    /**
     * Retrieve a list of all categories.
     *
     * @return List of all categories in the repository.
     */
    @Operation(summary = "Get all categories", description = "Retrieve a list of all categories.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of categories")
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> all() {
        List<CategoryDTO> categories = categoryService.getAll();
        return ResponseEntity.ok(categories);
    }

    /**
     * Add a new category to the repository.
     *
     * @param newCategory Category object containing the details of the new
     *                    category.
     * @return The saved Category object.
     */
    @Operation(summary = "Create a new category", description = "Create and save a new category with the provided details.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Category successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping("/categories")
    public ResponseEntity<CategoryDTO> newCategory(@Valid @RequestBody CategoryCreateDTO newCategory) {
        CategoryDTO savedCategory = categoryService.save(newCategory);
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
    public ResponseEntity<CategoryDTO> one(
            @Parameter(description = "ID of the category to be retrieved") @PathVariable Long id) {

        CategoryDTO category = categoryService.find(id);

        if (category == null) {
            throw new CategoryNotFoundException(id); // Throw the exception instead of returning it
        } else {
            return ResponseEntity.ok(category); // Properly build the response entity with the body
        }
    }

    /**
     * Update an existing category or create a new one if the specified category ID
     * doesn't exist.
     *
     * @param newCategory Category object containing updated details.
     * @param id          ID of the category to be updated.
     * @return The updated or newly created Category object.
     */
    @Operation(summary = "Update an existing category", description = "Update the details of an existing category or create a new one if it doesn't exist.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category successfully updated"),
            // @ApiResponse(responseCode = "201", description = "Category created as it did
            // not exist"),
            // @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @PutMapping("/categories/{id}")
    public ResponseEntity<Void> replaceCategory(
            @Valid @RequestBody CategoryCreateDTO newCategory,
            @PathVariable Long id) {

        categoryService.update(newCategory, id);
        return ResponseEntity.ok().build(); // Return 200 OK with the updated category
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
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.status(204).build();
    }
}