package com.baipiao.api.events;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll().stream().map(event -> {
            return new EventDTO(event);
        }).collect(Collectors.toList());
    }

    public EventDTO save(Event event) {
        event = eventRepository.save(event);
        return new EventDTO(event);

    }
    public EventDTO find(Long id) {
        return eventRepository.findById(id).map(event -> {
            return new EventDTO(event);
        }).orElse(null);
    }
    public void deleteById(Long id) {
        eventRepository.deleteById(id); // Delete the event by ID(id);
    }
}