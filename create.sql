-- Enabling PostGIS Extension for spatial data types
CREATE EXTENSION IF NOT EXISTS postgis;

CREATE TABLE IF NOT EXISTS Venue (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    location GEOGRAPHY(POINT, 4326), -- Using PostGIS Point for geographical data
    description TEXT,
    address VARCHAR(255),
    capacity INT
);

CREATE TABLE IF NOT EXISTS "User" (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    display_name VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    user_type VARCHAR(50) NOT NULL,
    email VARCHAR(255) UNIQUE,
    phone_number VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE IF NOT EXISTS EventOrganizer (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    contact_email VARCHAR(255),
    phone_number VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS EventCategory (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

CREATE TABLE IF NOT EXISTS Event (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    registration_required BOOLEAN NOT NULL,
    venue INT REFERENCES Venue(id),
    category INT REFERENCES EventCategory(id),
    details TEXT,
    organizer INT REFERENCES EventOrganizer(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    capacity INT

);

CREATE TABLE IF NOT EXISTS EventTicket (
    event INT REFERENCES Event(id) ON DELETE CASCADE,
    "user" INT REFERENCES "User"(id) ON DELETE CASCADE,
    ticket_no VARCHAR(50) NOT NULL UNIQUE,
    registration_date DATE NOT NULL,
    status VARCHAR(50) NOT NULL,
    capacity INT,
    PRIMARY KEY (event, "user")
);




