package com.baipiao.api.tickets;

import java.util.List;
import java.util.stream.Collectors;

import com.baipiao.api.organizations.OrganizationRepository;
import com.baipiao.api.users.UserRepository;
import com.baipiao.api.tickets.TicketRepository;
import com.baipiao.api.tickets.dto.TicketCreateDTO;
import com.baipiao.api.tickets.dto.TicketDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    public List<TicketDTO> findAll() {
        return ticketRepository.findAll().stream().map(ticket -> {
            return new TicketDTO(ticket);
        }).collect(Collectors.toList());
    }

    public List<TicketDTO> findByEvent(Long eventId) {
        return ticketRepository.findByEventId(eventId).stream().map(ticket -> {
            return new TicketDTO(ticket);
        }).collect(Collectors.toList());
    }

    public List<TicketDTO> findByUser(Long userId) {
        return ticketRepository.findByUserId(userId).stream().map(ticket -> {
            return new TicketDTO(ticket);
        }).collect(Collectors.toList());
    }

    public TicketDTO find(Long eventId, Long userId) {
        return ticketRepository.findByEventAndUser(eventId, userId).map(ticket -> {
            return new TicketDTO(ticket);
        }).orElse(null);
    }

    public void deleteTicket(Long eventId, Long userId) {
        Ticket existingTicket = ticketRepository.findByEventAndUser(eventId, userId)
                .orElseThrow(() -> new TicketNotFoundException(eventId, userId));
        ticketRepository.deleteTicket(eventId, userId);
    }

    public void save(TicketCreateDTO newTicket) {
        ticketRepository.insertTicket(newTicket.getEvent(), newTicket.getUser(), newTicket.getRegistrationDate(),
                newTicket.getTicketNo(), newTicket.getStatus());
    }

    public void update(TicketCreateDTO ticket, Long eventId, Long userId) {
        Ticket existingTicket = ticketRepository.findByEventAndUser(eventId, userId)
                .orElseThrow(() -> new TicketNotFoundException(eventId, userId));
        ticketRepository.updateTicket(eventId, userId, ticket.getRegistrationDate(), ticket.getTicketNo(),
                ticket.getStatus());
    }
}
