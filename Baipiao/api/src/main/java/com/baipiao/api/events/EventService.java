package com.baipiao.api.events;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baipiao.api.categories.CategoryRepository;
import com.baipiao.api.events.dtos.EventCreateDTO;
import com.baipiao.api.events.dtos.EventDTO;
import com.baipiao.api.organizations.OrganizationRepository;
import com.baipiao.api.venues.VenueRepository;

import jakarta.transaction.Transactional;

@Service
public class EventService {

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

    @Transactional
    public Event getEventWithTickets(Long eventId) {
        // Fetch the event with tickets
        return eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
    }
    public EventDTO save(EventCreateDTO newEvent) {
        Event event = new Event();
        if (newEvent.getId() != null) {
            event = eventRepository.findById(newEvent.getId()).orElseThrow(() -> new RuntimeException("Event not found"));
        }
        event.setId(newEvent.getId());
        event.setName(newEvent.getName());
        event.setDetails(newEvent.getDetails());
        event.setRegistrationRequired(newEvent.isRegistrationRequired());
        event.setRegistrationLink(newEvent.getRegistrationLink());
        event.setContactEmail(newEvent.getContactEmail());
        event.setContactPhoneNumber(newEvent.getContactPhoneNumber());
        event.setStatus(newEvent.getStatus());
        event.setCapacity(newEvent.getCapacity());
        event.setImage(newEvent.getImage());
        event.setVenue(venueRepository.findById(newEvent.getVenue()).orElse(null)); 
        event.setCategory(categoryRepository.findById(newEvent.getCategory()).orElse(null));
        event.setOrganizer(organizationRepository.findById(newEvent.getOrganizer()).orElse(null));
        event.setRegistrationDeadline(newEvent.getRegistrationDeadline());
        event.setStartDate(newEvent.getStartDate());
        event.setEndDate(newEvent.getEndDate());
        event = eventRepository.save(event);
        return new EventDTO(event);

    }
    public EventDTO find(Long id) {
        return eventRepository.findById(id).map(event -> {
            return new EventDTO(event);
        }).orElse(null);
    }
    public EventDTO deleteById(Long id) {
        eventRepository.findById(id).map(event->{
            eventRepository.delete(event);
            return new EventDTO(event);
        }).orElseThrow(() -> new EventNotFoundException(id));
        return null;
    }
    public boolean  existsById(Long id) {    
        return eventRepository.existsById(id);
    }
}