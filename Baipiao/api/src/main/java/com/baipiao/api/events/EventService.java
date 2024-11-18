package com.baipiao.api.events;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baipiao.api.categories.CategoryRepository;
import com.baipiao.api.organizations.OrganizationRepository;
import com.baipiao.api.tickets.TicketRepository;
import com.baipiao.api.tickets.TicketService;
import com.baipiao.api.users.UserRepository;
import com.baipiao.api.venues.PointDeserializer;
import com.baipiao.api.venues.PointSerializer;
import com.baipiao.api.venues.VenueRepository;

import jakarta.transaction.Transactional;
import com.baipiao.api.events.dto.EventDTO;
import com.baipiao.api.events.dto.EventCreateDTO;

@Service
public class EventService {

    @Autowired
    private TicketService ticketService;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private VenueRepository venueRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private OrganizationRepository organizationRepository;

    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll().stream().map(event -> {
            return new EventDTO(event);
        }).collect(Collectors.toList());
    }

    public Event getEventWithTickets(Long eventId) {
        // Fetch the event with tickets
        return eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
    }
    public void save(EventCreateDTO newEvent) {
        eventRepository.insertEvent(
                newEvent.getName(), // String
                newEvent.getDetails(), // String
                newEvent.isRegistrationRequired(), // Boolean
                newEvent.getRegistrationLink(), // String
                newEvent.getContactEmail(), // String
                newEvent.getContactPhoneNumber(), // String
                newEvent.getStatus(), // String
                newEvent.getCapacity(), // Integer
                newEvent.getImage(), // String
                newEvent.getRegistrationDeadline(), // LocalDateTime
                newEvent.getStartDate(), // LocalDateTime
                newEvent.getEndDate(), // LocalDateTime
                LocalDateTime.now(), // createDate (current timestamp)
                newEvent.getVenueId(), // Long
                newEvent.getCategoryId(), // Long
                newEvent.getOrganizerId() // Long
        );
    }

    public void update(Long id, EventCreateDTO newEvent) {
        if (!eventRepository.existsById(id)) {
            throw new EventNotFoundException(id);
        } else {
            eventRepository.updateEvent(
                    id,
                    newEvent.getName(),
                    newEvent.getDetails(),
                    newEvent.isRegistrationRequired(),
                    newEvent.getRegistrationLink(),
                    newEvent.getContactEmail(),
                    newEvent.getContactPhoneNumber(),
                    newEvent.getStatus(),
                    newEvent.getCapacity(),
                    newEvent.getImage(),
                    newEvent.getRegistrationDeadline(),
                    newEvent.getStartDate(),
                    newEvent.getEndDate(),
                    newEvent.getVenueId(),
                    newEvent.getCategoryId(),
                    newEvent.getOrganizerId());
        }
    }

    public EventDTO find(Long id) {
        return eventRepository.findById(id).map(event -> {
            return new EventDTO(event);
        }).orElse(null);
    }

    public EventDTO deleteById(Long id) {
        eventRepository.findById(id).map(event -> {
            eventRepository.delete(event);
            return new EventDTO(event);
        }).orElseThrow(() -> new EventNotFoundException(id));
        return null;
    }

    public boolean existsById(Long id) {
        return eventRepository.existsById(id);
    }

    public List<EventDTO> getEventsByArea(double centerX, double centerY, double radius) {
        return eventRepository.getEventsByArea(centerX, centerY, radius).stream().map(event -> {
            return new EventDTO(event);
        }).collect(Collectors.toList());
    }

    public Boolean isRegistered(Long eventId, String username) {
        return eventRepository.isRegistered(eventId, username);
    }

    public void register(Long eventId, String username) {
        ticketService.createTicket(eventId, username);
    }

    public void unregister(Long eventId, String username) {
        ticketService.deleteTicket(eventId, username);
    }
}