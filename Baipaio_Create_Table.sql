-- Drop tables if they exist
DROP TABLE IF EXISTS tickets, ticket;
DROP TABLE IF EXISTS events, event;
DROP TABLE IF EXISTS organizations, organization;
DROP TABLE IF EXISTS venues, venue;
DROP TABLE IF EXISTS categories, category;
DROP TABLE IF EXISTS users, user, "User";

-- Create users table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    display_name VARCHAR(100),
    password VARCHAR(255) NOT NULL,
    user_type VARCHAR(20) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone_number VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create categories table
CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description TEXT
);

-- Create venues table
CREATE TABLE venues (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    location GEOGRAPHY(POINT, 4326) NOT NULL,
    description TEXT,
    address VARCHAR(255),
    capacity INTEGER
);

-- Create organizations table
CREATE TABLE organizations (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    contact_email VARCHAR(100),
    phone_number VARCHAR(20)
);

-- Create events table
CREATE TABLE events (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    image VARCHAR(255),
    venue_id INTEGER REFERENCES venues(id),
    status VARCHAR(20),
    registration_deadline TIMESTAMP,
    category_id INTEGER REFERENCES categories(id),
    start_date TIMESTAMP,
    contact_email VARCHAR(100),
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    organizer_id INTEGER REFERENCES organizations(id),
    end_date TIMESTAMP,
    capacity INTEGER,
    registration_link VARCHAR(255),
    registration_required BOOLEAN,
    details TEXT,
    update_date TIMESTAMP,
    contact_phone_number VARCHAR(20)
);

-- Create tickets table
CREATE TABLE tickets (
    event_id INTEGER REFERENCES events(id),
    user_id INTEGER REFERENCES users(id),
    ticket_no VARCHAR(50),
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20),
    PRIMARY KEY (event_id, user_id)
);

-- Add indexes for better query performance
CREATE INDEX idx_events_category ON events(category_id);
CREATE INDEX idx_events_venue ON events(venue_id);
CREATE INDEX idx_events_organizer ON events(organizer_id);

