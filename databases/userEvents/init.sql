-- Create users table
CREATE TABLE users (
   id SERIAL PRIMARY KEY,
   name VARCHAR(100) UNIQUE NOT NULL,
   password VARCHAR(100) NOT NULL,
   email VARCHAR(100) UNIQUE NOT NULL
);

-- Create events table
CREATE TABLE events (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    host_id INT NOT NULL,
    description TEXT NOT NULL,
    location VARCHAR(100) NOT NULL,
    time TIMESTAMP NOT NULL,
    creation_time TIMESTAMP NOT NULL,
    FOREIGN KEY (host_id) REFERENCES users(id)
);

-- Create event_participants table
CREATE TABLE event_participants (
    event_id INT NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY (event_id, user_id),
    FOREIGN KEY (event_id) REFERENCES events(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);