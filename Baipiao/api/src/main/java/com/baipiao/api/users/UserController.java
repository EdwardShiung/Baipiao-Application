package com.baipiao.api.users;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
        newUser.setUpdatedAt(LocalDateTime.now());

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
                    user.setEmail(newUser.getEmail());
                    user.setUserName(newUser.getUserName());

                    // Update password if it's changed
                    if (newUser.getPassword() != null && !newUser.getPassword().isEmpty()) {
                        user.setPassword(newUser.getPassword());
                    }

                    user.setRole(newUser.getRole());
                    user.setProfilePicture(newUser.getProfilePicture());
                    user.setActive(newUser.isActive());
                    user.setVerified(newUser.isVerified());
                    user.setFirstName(newUser.getFirstName());
                    user.setLastName(newUser.getLastName());
                    user.setDisplayName(newUser.getDisplayName());
                    user.setPhone(newUser.getPhone());
                    user.setBio(newUser.getBio());
                    user.setWebsite(newUser.getWebsite());
                    // Update the timestamp
                    user.setUpdatedAt(LocalDateTime.now());
                    User updatedUser = repository.save(user);
                    return ResponseEntity.ok(updatedUser);
                })
                .orElseGet(() -> {
                    // If user doesn't exist, create a new one
                    newUser.setId(id);
                    newUser.setCreatedAt(LocalDateTime.now());
                    newUser.setUpdatedAt(LocalDateTime.now());

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