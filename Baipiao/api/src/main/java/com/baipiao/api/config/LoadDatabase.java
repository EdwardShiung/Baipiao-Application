package com.baipiao.api.config;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baipiao.api.categories.Category;
import com.baipiao.api.categories.CategoryRepository;
import com.baipiao.api.events.Event;
import com.baipiao.api.events.EventRepository;
import com.baipiao.api.organizations.Organization;
import com.baipiao.api.organizations.OrganizationRepository;
import com.baipiao.api.tickets.Ticket;
import com.baipiao.api.tickets.TicketRepository;
import com.baipiao.api.users.User;
import com.baipiao.api.users.UserRepository;
import com.baipiao.api.venues.Venue;
import com.baipiao.api.venues.VenueRepository;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    private final GeometryFactory geometryFactory = new GeometryFactory();
    private final Random random = new Random();

    @Bean
    CommandLineRunner initDatabase(EventRepository eventRepository, UserRepository userRepository, CategoryRepository categoryRepository,
                                   VenueRepository venueRepository, OrganizationRepository organizationRepository, TicketRepository ticketRepository) {

        return args -> {
            // Random user data
            List<String> firstNames = Arrays.asList("John", "Jane", "Alex", "Emily", "Michael", "Sara", "Robert", "Anna", "David", "Laura");
            List<String> lastNames = Arrays.asList("Smith", "Johnson", "Brown", "Davis", "Miller", "Wilson", "Taylor", "Anderson", "Thomas", "Moore");

            // Random organization data
            List<String> orgNames = Arrays.asList("Tech Innovators", "Blacksburg Council", "Hokie Sports", "Blacksburg Arts", "VT Alumni Association");

            // Random venue data
            List<String> venueNames = Arrays.asList("Lane Stadium", "Squires Student Center", "Downtown Blacksburg", "Blacksburg Park", "Tech Hall", "VT Sports Arena", "Blacksburg Mall", "Heritage Museum", "The Lyric Theater", "Blacksburg Library", "Cassell Coliseum", "VT Quad", "Main Street Plaza", "University Plaza", "Research Park");

            List<String> categoryNames = Arrays.asList("Sports", "Music", "Entertainment", "Food", "Community", "Community", "Community", "Community", "Community", "Community", "Community", "Community", "Community", "Community", "Community");
            
            List<String> eventStatusList = Arrays.asList("active", "upcoming", "past");

            List<String> userTypeList = Arrays.asList("user", "admin", "organization");

            // Users (10 users with unique emails)
            List<User> users = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                String firstName = firstNames.get(random.nextInt(firstNames.size()));
                String lastName = lastNames.get(random.nextInt(lastNames.size()));
                // Generate a unique email by appending the index `i`
                String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + i + "@example.com";
                String username = firstName.toLowerCase() + random.nextInt(1000);
                User user = new User(email, username, "password" + random.nextInt(1000), userTypeList.get(random.nextInt(userTypeList.size())), firstName + " " + lastName, "555-" + (1000 + random.nextInt(9000)), LocalDateTime.now());
                users.add(user);
                log.info("Preloading " + userRepository.save(user));
            }

            // Organizations (5 organizations)
            List<Organization> organizations = new ArrayList<>();
            for (String orgName : orgNames) {
                Organization org = new Organization(
                    orgName, 
                    orgName + " organization", 
                    "info@" + orgName.toLowerCase().replace(" ", "") + ".com", 
                    "555-" + (1000 + random.nextInt(9000))
                );
                organizations.add(org);
                log.info("Preloading " + organizationRepository.save(org));
            }

            // Venues (15 venues with random locations around Blacksburg)
            List<Venue> venues = new ArrayList<>();
            for (String venueName : venueNames) {
                Point location = geometryFactory.createPoint(new Coordinate(
                    -80.4200 + (random.nextDouble() * 0.02),  // Longitude
                    37.2300 + (random.nextDouble() * 0.02)   // Latitude
                ));
                User createdBy =  users.get(random.nextInt(users.size()));
                User updatedBy =  users.get(random.nextInt(users.size()));
                Venue venue = new Venue(venueName, "Description of " + venueName, location, createdBy, updatedBy);
                venues.add(venue);
                log.info("Preloading " + venueRepository.save(venue));
            }
            List<Category> categories = new ArrayList<>();
            for (String categoryName : categoryNames) {
                Category category = new Category(categoryName, "Description of " + categoryName);
                categories.add(category);
                log.info("Preloading " + categoryRepository.save(category));
            }
            
            // Events (50 events)
            List<Event> events = new ArrayList<>();
            for (int i = 1; i <= 50; i++) {
                Organization organizer = organizations.get(random.nextInt(organizations.size())); // Random organizer
                Venue venue = venues.get(random.nextInt(venues.size())); // Random venue

                Category category = categories.get(random.nextInt(venues.size())); // Random venue
                
                LocalDateTime registrationDeadline = LocalDateTime.now().plusDays(random.nextInt(30));  // Random registration deadline
                Event event = new Event(
                        "Event " + i,  // Event Name
                        "Details of Event " + i,        // Event Details
                        random.nextBoolean(),  // Randomly assign registration requirement
                        "http://event" + i + ".com/register",  // Registration Link
                        "contact" + i + "@event.com",  // Contact Email
                        "555-000-" + i,  // Contact Phone Number
                        eventStatusList.get(random.nextInt(eventStatusList.size())), // Random event status
                        100 + random.nextInt(900),  // Random capacity between 100 and 1000
                        "http://event" + i + ".com/images/event" + i + ".jpg",  // Image URL
                        registrationDeadline,   // Registration Deadline
                        registrationDeadline.plusDays(1),      // Start Date
                        registrationDeadline.plusDays(1+random.nextInt(5)),      // End Date
                        venue,  // Venue
                        category,
                        organizer  // Organizer
                );
                events.add(event);
                log.info("Preloading " + eventRepository.save(event));
            }


            for (int i = 1; i <= 20; i++) {
                Ticket ticket = new Ticket(events.get(random.nextInt(events.size())), users.get(random.nextInt(users.size())), "T"+ random.nextInt(1000), LocalDateTime.now().minusHours(random.nextInt(500)), "active");
                log.info("Preloading " + ticketRepository.save(ticket));
            }
        };
    }
}