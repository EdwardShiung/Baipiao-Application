-- Function to generate random text
CREATE OR REPLACE FUNCTION random_text(length INTEGER) RETURNS TEXT AS
$$
DECLARE
  chars TEXT := 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
  result TEXT := '';
  i INTEGER := 0;
BEGIN
  FOR i IN 1..length LOOP
    result := result || substr(chars, floor(random() * length(chars) + 1)::INTEGER, 1);
  END LOOP;
  RETURN result;
END;
$$ LANGUAGE plpgsql;

-- Clear existing data
TRUNCATE TABLE tickets, events, organizations, venues, categories, users RESTART IDENTITY CASCADE;

-- Insert sample data for users
INSERT INTO users (username, display_name, password, user_type, email, phone_number)
SELECT
  'username' || n,
  'display_name ' || n,
  md5(random()::text),
  CASE WHEN random() < 0.2 THEN 'admin' ELSE 'regular' END,
  'user' || n || '@vt.edu',
  '+1' || floor(random() * (999999999 - 100000000) + 100000000)::text
FROM generate_series(1, 30) n;

-- Insert sample data for categories
INSERT INTO categories (name, description)
SELECT
  'Category ' || n,
  'Description for category ' || n
FROM generate_series(1, 30) n;

-- Insert sample data for venues
INSERT INTO venues (name, location, description, address, capacity)
SELECT
  'Venue ' || n,
  ST_GeomFromText('POINT(' || (random() * 360 - 180) || ' ' || (random() * 180 - 90) || ')', 4326), -- Use PostGIS to generate a random geography POINT
  'Description for venue ' || n,
  n || ' Main Street, City ' || floor(random() * 100)::text,
  floor(random() * (1000 - 100) + 100)
FROM generate_series(1, 30) n;

-- Insert sample data for organizations
INSERT INTO organizations (name, description, contact_email, phone_number)
SELECT
  'Organization ' || n,
  'Description for organization ' || n,
  'org' || n || '@vt.edu',
  '+1' || floor(random() * (999999999 - 100000000) + 100000000)::text
FROM generate_series(1, 30) n;

-- Insert sample data for events
INSERT INTO events (
  name, image, venue_id, status, registration_deadline, 
  category_id, start_date, contact_email, organizer_id, 
  end_date, capacity, registration_link, registration_required, 
  details, update_date, contact_phone_number
)
SELECT
  'Event ' || n,
  'http://vt.com/image' || n || '.jpg',
  floor(random() * 30) + 1,  -- venue_id must match an existing venue id
  CASE 
    WHEN random() < 0.3 THEN 'draft'
    WHEN random() < 0.6 THEN 'published'
    ELSE 'completed'
  END,
  CURRENT_DATE + (floor(random() * 30) || ' days')::INTERVAL,
  floor(random() * 30) + 1,  -- category_id must match an existing category id
  CURRENT_DATE + (floor(random() * 60) || ' days')::INTERVAL,
  'event' || n || '@vt.edu',
  floor(random() * 30) + 1,  -- organizer_id must match an existing organization id
  CURRENT_DATE + (floor(random() * 90) || ' days')::INTERVAL,
  floor(random() * (500 - 50) + 50),
  'http://vt.com/register' || n,
  random() > 0.5,
  'Detailed description for event ' || n,
  CURRENT_TIMESTAMP,
  '+1' || floor(random() * (999999999 - 100000000) + 100000000)::text
FROM generate_series(1, 30) n;

-- Insert sample data for tickets
INSERT INTO tickets (event_id, user_id, ticket_no, status)
SELECT
  event_id,
  user_id,
  'TIK-' || event_id || '-' || user_id,
  CASE 
    WHEN random() < 0.7 THEN 'confirmed'
    WHEN random() < 0.9 THEN 'pending'
    ELSE 'cancelled'
  END
FROM (
  SELECT 
    floor(random() * 30) + 1 as event_id,  -- event_id must match an existing event id
    floor(random() * 30) + 1 as user_id    -- user_id must match an existing user id
  FROM generate_series(1, 30) n
) as subquery;

-- Drop the temporary function
DROP FUNCTION IF EXISTS random_text(INTEGER);

-- Verify the number of records in each table
SELECT 
  (SELECT COUNT(*) FROM users) as users_count,
  (SELECT COUNT(*) FROM categories) as categories_count,
  (SELECT COUNT(*) FROM venues) as venues_count,
  (SELECT COUNT(*) FROM organizations) as organizations_count,
  (SELECT COUNT(*) FROM events) as events_count,
  (SELECT COUNT(*) FROM tickets) as tickets_count;