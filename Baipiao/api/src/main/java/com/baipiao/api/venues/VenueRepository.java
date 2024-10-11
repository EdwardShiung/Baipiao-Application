package com.baipiao.api.venues;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VenueRepository extends JpaRepository<Venue, Long> {

    @Override
    @Query(value = "SELECT * FROM venue", nativeQuery = true)
    List<Venue> findAll();
}