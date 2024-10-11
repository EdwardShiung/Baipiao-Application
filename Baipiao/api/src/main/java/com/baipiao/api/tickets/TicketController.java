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
import com.baipiao.api.users.UserNotFoundException;
import com.baipiao.api.users.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Tickets", description = "REST endpoints for managing Tickets")
public class TicketController {

    private final TicketRepository repository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Autowired
    public TicketController(TicketRepository repository, EventRepository eventRepository, UserRepository userRepository) {
        this.repository = repository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    /**
     * Retrieve a list of all tickets.
     *
     * @return List of all tickets in the repository.
     */
    @Operation(summary = "Get all tickets", description = "Retrieve a list of all tickets.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of tickets")
    @GetMapping("/tickets")
    public ResponseEntity<List<Ticket>> all() {
        List<Ticket> tickets = repository.findAll();
        return ResponseEntity.ok(tickets);
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
    public ResponseEntity<Ticket> newTicket(@Valid @RequestBody Ticket newTicket) {
        Ticket savedTicket = repository.save(newTicket);
        return ResponseEntity.status(201).body(savedTicket);
    }

    /**
     * Retrieve a specific ticket by providing its ID.
     *
     * @param id ID of the ticket to be retrieved.
     * @return The Ticket object with the specified ID.
     */
    @Operation(summary = "Get a ticket by ID", description = "Retrieve a specific ticket by providing its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the ticket"),
            @ApiResponse(responseCode = "404", description = "Ticket not found")
    })
    @GetMapping("/tickets/{id}")
    public Ticket one(@Parameter(description = "ID of the ticket to be retrieved") @PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException(id));
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
    @PutMapping("/tickets/{event_id}/{user_id}")
    public ResponseEntity<Ticket> replaceTicket(@Valid @RequestBody Ticket newTicket, @PathVariable Long user_id, @PathVariable Long event_id) {
    
        
        return repository.findByUserAndEvent(user_id, event_id)
                .map(ticket -> {
                    // Update fields from the provided newTicket object
                    ticket.setEvent(newTicket.getEvent());
                    ticket.setRegistrationDate(newTicket.getRegistrationDate());
                    ticket.setStatus(newTicket.getStatus());
                    ticket.setTicketNo(newTicket.getTicketNo());
                    ticket.setUser(newTicket.getUser());
                    
                    Ticket updatedTicket = repository.save(ticket); // Save the updated ticket
                    return ResponseEntity.ok(updatedTicket);
                })
                .orElseGet(() -> {
                    // If ticket doesn't exist, create a new one
                    newTicket.setUser(userRepository.findById(user_id).orElseThrow(() -> new UserNotFoundException(user_id))); 
                    newTicket.setEvent(eventRepository.findById(event_id).orElseThrow(() -> new EventNotFoundException(event_id)));
                    
                    Ticket savedTicket = repository.save(newTicket); // Save the new ticket
                    return ResponseEntity.status(201).body(savedTicket);
                });
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
    @DeleteMapping("/tickets/{id}")
    public ResponseEntity<Object> deleteTicket(@PathVariable Long id) {
        return repository.findById(id)
                .map(ticket -> {
                    repository.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new TicketNotFoundException(id));
    }
}
