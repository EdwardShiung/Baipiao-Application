package com.baipiao.api.tickets;

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

import com.baipiao.api.events.EventNotFoundException;
import com.baipiao.api.events.EventRepository;
import com.baipiao.api.events.EventService;
import com.baipiao.api.tickets.dto.TicketCreateDTO;
import com.baipiao.api.tickets.dto.TicketDTO;
import com.baipiao.api.users.UserNotFoundException;
import com.baipiao.api.users.UserRepository;
import com.baipiao.api.users.UserService;
import com.baipiao.api.users.dto.UserCreateDTO;
import com.baipiao.api.users.dto.UserDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Tickets", description = "REST endpoints for managing Tickets")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }
    /**
     * Retrieve a list of all tickets.
     *
     * @return List of all tickets in the repository.
     */
    @Operation(summary = "Get all tickets", description = "Retrieve a list of all tickets.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of tickets")
    @GetMapping("/tickets")
    public ResponseEntity<List<TicketDTO>> all() {
        List<TicketDTO> tickets = ticketService.findAll();
        return ResponseEntity.ok(tickets);
    }
     /**
     * Retrieve tickets for a specific eventby providing its event ID.
     *
     * @param eventId ID of the event to be retrieved.
     * @return All Ticket objects with the specified event ID.
     */
    @Operation(summary = "Get all ticket by event ID", description = "Retrieve a specific ticket by providing its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the ticket"),
            @ApiResponse(responseCode = "404", description = "Ticket not found")
    })
    @GetMapping("/tickets/event/{eventId}")
    public List<TicketDTO> findByEvent(@Parameter(description = "ID of the ticket to be retrieved") @PathVariable Long eventId) {
        return ticketService.findByEvent(eventId);
    }


    /**
     * Retrieve tickets for a specific user by providing its user  ID.
     *
     * @param userId ID of the user to be retrieved.
     * @return All Ticket objects with the specified user ID.
     */
    @Operation(summary = "Get all ticket by user+ ID", description = "Retrieve tickets for a specific user by providing its user ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the ticket"),
            @ApiResponse(responseCode = "404", description = "Ticket not found")
    })
    @GetMapping("/tickets/user/{userId}")
    public List<TicketDTO> findByUser(@Parameter(description = "userId of the ticket to be retrieved") @PathVariable Long userId) {
        return ticketService.findByUser(userId);
    }



    /**
     * Retrieve tickets for a specific user by providing its user  ID.
     *
     * @param eventId ID of the user to be retrieved.
     * @param userId ID of the user to be retrieved.
     * @return All Ticket objects with the specified user ID.
     */
    @Operation(summary = "Get a specific ticket by user ID and event ID", description = "Retrieve a tickets for a specific user and event ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the ticket"),
            @ApiResponse(responseCode = "404", description = "Ticket not found")
    })
    @GetMapping("/tickets/event/{eventId}/user/{userId}")
    public ResponseEntity<TicketDTO> findTicekt(@Parameter(description = "userId of the ticket to be retrieved") @PathVariable Long userId, @Parameter(description = "eventId of the ticket to be retrieved") @PathVariable Long eventId) {
        TicketDTO ticket = ticketService.find(eventId,userId);
        if (ticket == null) {
            return ResponseEntity.status(404).build();
        } else {
            return ResponseEntity.ok(ticket); // Properly build the response entity with the body
        }
    }


    /**
     * Add a new ticket to the repository.
     *
     * @param newTicket Ticket object containing the details of the new ticket.
     * @return The saved Ticket object.
     */
    @Operation(summary = "Create a new ticket", description = "Create and save a new ticket with the provided details.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ticket successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping("/tickets")
    public ResponseEntity<Void> newTicket(@Valid @RequestBody TicketCreateDTO newTicket) {
        ticketService.save(newTicket);
        return ResponseEntity.status(201).build();
    }

   

    /**
     * Update an existing ticket or create a new one if the specified ticket ID doesn't exist.
     *
     * @param newTicket Ticket object containing updated details.
     * @param id ID of the ticket to be updated.
     * @return The updated or newly created Ticket object.
     */
    @Operation(summary = "Update an existing ticket", description = "Update the details of an existing ticket or create a new one if it doesn't exist.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ticket successfully updated"),
            @ApiResponse(responseCode = "201", description = "Ticket created as it did not exist"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PutMapping("/tickets/event/{eventId}/user/{userId}")
    public ResponseEntity<Void> replaceTicket(@Valid @RequestBody TicketCreateDTO newTicket, @PathVariable Long userId, @PathVariable Long eventId) {
    
        ticketService.update(newTicket, eventId, userId); // Save the updated ticket
        return ResponseEntity.ok().build(); 

    }
    /**
     * Delete a ticket by ID.
     *
     * @param id ID of the ticket to be deleted.
     */
    @Operation(summary = "Delete a ticket", description = "Delete a ticket by providing its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Ticket successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Ticket not found")
    })
    @DeleteMapping("/tickets/event/{eventId}/user/{userId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long userId, @PathVariable Long eventId) {
        ticketService.deleteTicket(eventId, userId);
        return ResponseEntity.status(204).build();

    }
}
