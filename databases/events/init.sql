-- Create users table
CREATE TABLE users (
   id SERIAL PRIMARY KEY,
   name VARCHAR(100),
   password VARCHAR(100),
   email VARCHAR(100)
);

-- Create events table
CREATE TABLE events (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    description TEXT,
    location VARCHAR(100),
    time TIMESTAMP,
    creationTime TIMESTAMP
);

-- Create event_participants table
CREATE TABLE event_participants (
    event_id INT NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY (event_id, user_id),
    FOREIGN KEY (event_id) REFERENCES events(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);