package com.baipiao.api.config;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baipiao.api.events.Event;
import com.baipiao.api.events.EventRepository;
import com.baipiao.api.organizations.Organization;
import com.baipiao.api.organizations.OrganizationRepository;
import com.baipiao.api.users.User;
import com.baipiao.api.users.UserRepository;
import com.baipiao.api.venues.Venue;
import com.baipiao.api.venues.VenueRepository;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    private final GeometryFactory geometryFactory = new GeometryFactory();

    @Bean
    CommandLineRunner initDatabase(EventRepository eventRepository, UserRepository userRepository,
                                   VenueRepository venueRepository, OrganizationRepository organizationRepository) {

        return args -> {
            // Users
            log.info("Preloading " + userRepository.save(new User("john.hokie@vt.edu", "johnhokie", "password1", "USER", "John", "Hokie")));
            log.info("Preloading " + userRepository.save(new User("jane.hokie@vt.edu", "janehokie", "password2", "USER", "Jane", "Hokie")));

            // Organizations
            Organization vtOrg = new Organization("Virginia Tech", "Virginia Tech organization", "http://vt.edu", "info@vt.edu", "555-123-9876", "Blacksburg, VA");
            Organization townBlacksburg = new Organization("Town of Blacksburg", "Blacksburg town organization", "http://blacksburg.gov", "info@blacksburg.gov", "555-654-1234", "Blacksburg, VA");

            log.info("Preloading " + organizationRepository.save(vtOrg));
            log.info("Preloading " + organizationRepository.save(townBlacksburg));

            // Venues
            Point laneStadiumLocation = geometryFactory.createPoint(new Coordinate(-80.4181, 37.2296)); // Lane Stadium
            Point squiresLocation = geometryFactory.createPoint(new Coordinate(-80.4191, 37.2289)); // Squires Student Center
            Point downtownBlacksburg = geometryFactory.createPoint(new Coordinate(-80.4139, 37.2297)); // Downtown Blacksburg

            Venue laneStadium = new Venue("Lane Stadium", "Virginia Tech's football stadium", laneStadiumLocation);
            Venue squiresStudentCenter = new Venue("Squires Student Center", "Central building for events and student activities", squiresLocation);
            Venue downtown = new Venue("Downtown Blacksburg", "Main downtown area of Blacksburg", downtownBlacksburg);

            log.info("Preloading " + venueRepository.save(laneStadium));
            log.info("Preloading " + venueRepository.save(squiresStudentCenter));
            log.info("Preloading " + venueRepository.save(downtown));

            // Events related to Virginia Tech and Blacksburg
            log.info("Preloading " + eventRepository.save(new Event(
                    "Virginia Tech Hokie Football Game",  // Event Name
                    true,  // Registration Required
                    "Come watch the Hokies take on our rivals at Lane Stadium in Blacksburg, VA!",  // Details
                    "tickets@hokiesports.com",  // Contact Email
                    "555-123-4567",  // Contact Phone Number
                    "Scheduled",  // Status
                    66000,  // Capacity
                    "http://hokiesports.com/register/footballgame",  // Registration Link
                    "http://hokiesports.com/images/football.jpg",  // Image URL
                    vtOrg,  // Organizer
                    laneStadium  // Venue
            )));

            log.info("Preloading " + eventRepository.save(new Event(
                    "Virginia Tech Career Fair",
                    true,
                    "Meet top employers looking for talented Hokies at the Virginia Tech Career Fair. Open to all students.",
                    "career@vt.edu",
                    "555-987-6543",
                    "Scheduled",
                    5000,
                    "http://career.vt.edu/register/careerfair",
                    "http://career.vt.edu/images/careerfair.jpg",
                    vtOrg,
                    squiresStudentCenter
            )));

            log.info("Preloading " + eventRepository.save(new Event(
                    "Blacksburg Art Festival",
                    false,
                    "Explore the best local art from students and residents at the Blacksburg Art Festival. Open to everyone.",
                    "blacksburgartfest@example.com",
                    "555-654-3210",
                    "Ongoing",
                    2000,
                    "http://blacksburgartfest.com/register",
                    "http://blacksburgartfest.com/images/artfestival.jpg",
                    townBlacksburg,
                    downtown
            )));

            log.info("Preloading " + eventRepository.save(new Event(
                    "Virginia Tech Engineering Expo",
                    true,
                    "Showcase your engineering skills and meet top companies in the field at the Engineering Expo.",
                    "engineering@vt.edu",
                    "555-321-9876",
                    "Scheduled",
                    3000,
                    "http://engineering.vt.edu/register/expo",
                    "http://engineering.vt.edu/images/expo.jpg",
                    vtOrg,
                    squiresStudentCenter
            )));

            log.info("Preloading " + eventRepository.save(new Event(
                    "Blacksburg Music Festival",
                    false,
                    "Join us for a night of live music from Virginia Tech students and local bands at the Blacksburg Music Festival.",
                    "musicfest@blacksburg.com",
                    "555-789-1234",
                    "Upcoming",
                    500,
                    "http://blacksburgmusicfest.com/register",
                    "http://blacksburgmusicfest.com/images/musicfestival.jpg",
                    townBlacksburg,
                    downtown
            )));
        };
    }
}