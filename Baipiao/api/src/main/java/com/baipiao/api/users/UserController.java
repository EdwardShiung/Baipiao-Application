package com.baipiao.api.users;

import com.baipiao.api.users.dto.UserCreateDTO;
import com.baipiao.api.users.dto.UserDTO;
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
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(user);
    }

    /**
     * Register a new user.
     *
     * @param registerRequest The registration details of the user.
     * @return The created UserDTO if successful, else 400 Bad Request.
     */
    @Operation(summary = "Register a new user", description = "Register a user by providing userName, email, password, phoneNumber, and userType.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid registration data")
    })
    @PostMapping("/users/register")
    public ResponseEntity<UserDTO> register(@RequestBody User.RegisterRequest registerRequest) {

        UserDTO newUser = userService.registerWithoutEncoding(registerRequest);
        if (newUser == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(201).body(newUser);
    }
    /**
     * Retrieve a specific user by providing its ID as a query parameter.
     *
     * @param id The ID of the user to be retrieved.
     * @return The User object with the specified ID.
     */
    @Operation(summary = "Get a user by ID (Query Parameter)", description = "Retrieve a specific user by providing its ID as a query parameter.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the user"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid query parameter")
    })
    @GetMapping("/users/retrieve")
    public ResponseEntity<UserDTO> retrieveUserById(@RequestParam Long id) {
        if (id == null) {
            // Return 400 Bad Request if ID is null
            return ResponseEntity.badRequest().build();
        }

        UserDTO retrievedUser = userService.find(id);
        if (retrievedUser == null) {
            throw new UserNotFoundException(id);
        }
        return ResponseEntity.ok(retrievedUser);
    }


    /**
     * Update a user's password.
     *
     * @param updatePasswordRequest Object containing the user ID and new password.
     * @return A ResponseEntity indicating the status of the operation.
     */
    @Operation(summary = "Update user password", description = "Update a user's password by providing their ID and new password in the request body.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Password updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PutMapping("/users/update-password")
    public ResponseEntity<Void> updatePassword(
            @RequestBody User.UpdatePasswordRequest updatePasswordRequest) {

        // Validate the request parameters
        if (updatePasswordRequest.getId() == null || updatePasswordRequest.getNewPassword() == null || updatePasswordRequest.getNewPassword().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Call the service layer to update the password
        userService.updatePassword(updatePasswordRequest.getId(), updatePasswordRequest.getNewPassword());

        return ResponseEntity.ok().build(); // Return 200 if the password is updated successfully
    }
}