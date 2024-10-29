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

import com.baipiao.api.users.dto.UserCreateDTO;
import com.baipiao.api.users.dto.UserDTO;

import java.util.List;

@RestController
@Tag(name = "Users", description = "REST endpoints for managing users")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    /**
     * Retrieve a list of all users.
     *
     * @return List of all users in the repository.
     */
    @Operation(summary = "Get all users", description = "Retrieve a list of all users.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of users")
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> all() {
        List<UserDTO> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    /**
     * Add a new user to the repository.
     *
     * @param newUser User object containing the details of the new
     *                    user.
     * @return The saved User object.
     */
    @Operation(summary = "Create a new user", description = "Create and save a new user with the provided details.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping("/users")
    public ResponseEntity<Void> newUser(@Valid @RequestBody UserCreateDTO newUser) {
        userService.save(newUser);
        return ResponseEntity.status(201).build();
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
    public ResponseEntity<UserDTO> one(
            @Parameter(description = "ID of the user to be retrieved") @PathVariable Long id) {

        UserDTO user = userService.find(id);

        if (user == null) {
            throw new UserNotFoundException(id); // Throw the exception instead of returning it
        } else {
            return ResponseEntity.ok(user); // Properly build the response entity with the body
        }
    }

    /**
     * Update an existing user or create a new one if the specified user ID
     * doesn't exist.
     *
     * @param newUser User object containing updated details.
     * @param id          ID of the user to be updated.
     * @return The updated or newly created User object.
     */
    @Operation(summary = "Update an existing user", description = "Update the details of an existing user or create a new one if it doesn't exist.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User successfully updated"),
            // @ApiResponse(responseCode = "201", description = "User created as it did
            // not exist"),
            // @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/users/{id}")
    public ResponseEntity<Void> replaceUser(
            @Valid @RequestBody UserCreateDTO newUser,
            @PathVariable Long id) {

        userService.update(newUser, id);
        return ResponseEntity.ok().build(); // Return 200 OK with the updated user
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
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.status(204).build();
    }
    /**
     * Login a user by username and password.
     *
     * @param loginRequest The login request containing username and password.
     * @return The User object if credentials are valid, else 401 Unauthorized.
     */
    @Operation(summary = "Login a user", description = "Login a user by username and password.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login Successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid userName or password")
    })
    @PostMapping("/users/login")
    public ResponseEntity<UserDTO> login(@RequestBody User.LoginRequest loginRequest) {
        String userName = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        UserDTO user = userService.login(userName, password);
        if (user == null) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }
        return ResponseEntity.ok(user);
    }
}