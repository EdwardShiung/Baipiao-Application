package com.baipiao.api.users;

import java.time.LocalDateTime;
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
@Tag(name = "Users", description = "REST endpoints for managing users")
public class UserController {

    private final UserRepository repository;

    @Autowired
    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieve a list of all users.
     *
     * @return List of all users in the repository.
     */
    @Operation(summary = "Get all users", description = "Retrieve a list of all users.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of users")
    @GetMapping("/users")
    public ResponseEntity<List<User>> all() {
        List<User> users = repository.findAll();
        return ResponseEntity.ok(users);
    }

    /**
     * Add a new user to the repository.
     *
     * @param newUser User object containing the details of the new user.
     * @return The saved User object.
     */
    @Operation(summary = "Create a new user", description = "Create and save a new user with the provided details.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping("/users")
    public ResponseEntity<User> newUser(@Valid @RequestBody User newUser) {
        // Set timestamps
        newUser.setCreatedAt(LocalDateTime.now());
        User savedUser = repository.save(newUser);
        return ResponseEntity.status(201).body(savedUser);
    }

    /**
     * Retrieve a specific user by providing its ID.
     *
     * @param id ID of the user to be retrieved.
     * @return The User object with the specified ID.
     */
    @Operation(summary = "Get a user by ID", description = "Retrieve a specific user by providing its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the user"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/users/{id}")
    public User one(@Parameter(description = "ID of the user to be retrieved") @PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    /**
     * Update an existing user or create a new one if the specified user ID doesn't exist.
     *
     * @param newUser User object containing updated details.
     * @param id ID of the user to be updated.
     * @return The updated or newly created User object.
     */
    @Operation(summary = "Update an existing user", description = "Update the details of an existing user or create a new one if it doesn't exist.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User successfully updated"),
            @ApiResponse(responseCode = "201", description = "User created as it did not exist"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PutMapping("/users/{id}")
    public ResponseEntity<User> replaceUser(@Valid @RequestBody User newUser, @PathVariable Long id) {

        return repository.findById(id)
                .map(user -> {
                    // Update fields
                    user.setCreatedAt(newUser.getCreatedAt());
                    user.setDisplayName(newUser.getDisplayName());
                    user.setEmail(newUser.getEmail());
                    user.setPhoneNumber(newUser.getPhoneNumber());
                    user.setUserType(newUser.getUserType());
                    user.setEmail(newUser.getEmail());
                    user.setUserName(newUser.getUserName());

                    // Update password if it's changed
                    if (newUser.getPassword() != null && !newUser.getPassword().isEmpty()) {
                        user.setPassword(newUser.getPassword());
                    }
                    ///TODO
                    return ResponseEntity.ok(user);
                })
                .orElseGet(() -> {
                    newUser.setCreatedAt(LocalDateTime.now());
                    User savedUser = repository.save(newUser);
                    return ResponseEntity.status(201).body(savedUser);
                });
    }

    /**
     * Delete a user by ID.
     *
     * @param id ID of the user to be deleted.
     */
    @Operation(summary = "Delete a user", description = "Delete a user by providing its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User successfully deleted"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        return repository.findById(id)
                .map(user -> {
                    repository.deleteById(id);
                    return ResponseEntity.noContent().build();  // 204 No Content for successful deletion
                })
                .orElseThrow(() -> new UserNotFoundException(id));  // 404 Not Found if the user does not exist
    }
}