# Event Planner API
This API is used for event planning. 
You can use it to schedule events, invite your friends to the events, and get event notifications.

## Architecture

---
The project comprises the following key components:

* **Redis:** Redis used to store user sessions and log event notifications history.
* **PostgreSQL:** database utilized for storing information about registered users and event details.
* **EventPlanner:** The primary API serving as the core component. 
It orchestrates the functionality of the entire system.

## Setup Instructions

---
Ensure you have the following installed:
- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/install/) (required on some linux distributions)
- [Java Development Kit](https://jdk.java.net/21/) (optional)
- [Maven](https://maven.apache.org/download.cgi) (optional)

Clone the Repository:

```bash
git clone https://github.com/guyb25/EventPlanner.git
cd EventPlanner/
```

### Running with Docker
For a quickstart, you can run the whole system by executing the command:
```bash
docker compose up
```
make sure you have port 8080 available on your machine.

### Running for development
If you want to run the API locally, make sure to set up the events PostgresSQL and the Redis instance.
You can even do that with the designated docker compose under ``EventPlanner/databases`` like so:
```bash
cd databases
docker compose up
```
Make sure to also run the API itself either through your IDE, or by running:
```bash
mvn package
java -jar target/EventPlanner.jar
```
Make sure you have the following ports available: 8080, 5432, 6379

## API Endpoints

---

You can access the swagger documentation at ``http://localhost:8080``
after running the API.

*Note:* a lot of the routes that seem like they should be GET routes are POST 
routes due to the need to transfer data like sessionId in the request body, which would
otherwise be insecure as part of the query String.

### Account Management Routes
> **POST /accounts/login**

**Description:** Logs the user into the system by creating a session. The session will expire in 30 minutes.

**Request:**
* name: String
* password: String

**Responses:**
* 201 CREATED: ${sessionId}
* 401 UNAUTHORIZED: Wrong username or password

<br></br>
> **POST /accounts/create**

**Description:** Registers a new account to the system.  

**Request:** 
* name: String
* password: String
* email: String

**Responses:**
* 201 CREATED: User created successfully
* 409 CONFLICT: Username already taken
* 409 CONFLICT: Email already taken

<br></br>
> **POST /accounts/terminate**

**Description:** Deletes the user from the database. Will also end the user's session and
removed the user from any events they're participating.

**Request:**
* sessionId: String

**Responses:**
* 200 OK: User deleted
* 401 UNAUTHORIZED: Invalid session

<br></br>
> **POST /accounts/logout**

**Description:** Logs out the user by deleting its session.

**Request:**
* sessionId: String

**Responses:**
* 200 OK: Ended session
* 401 UNAUTHORIZED: Invalid session

### Event Management Routes

> **POST /events/update/specific**

**Description:** updates the information about a specific event. Can only be done if the user
owns the event.

**Request:**
* sessionId: String
* eventId: Long
* name: String (optional - will override existing event name)
* description: String (optional - will override existing event description)
* location: String (optional - will override existing event description)
* time: String, format: YYYY-MM-DDThh:mm:ss (optional - will override existing event time)
* participants: List<String> (optional - will override current participant list)

**Responses:**
* 200 OK
* 401 UNAUTHORIZED: Invalid session
* 401 UNAUTHORIZED: Unauthorized (if the session doesn't belong to the event owner)

<br></br>
> **POST /events/retrieve/specific**

**Description:** retrieves the information about a specific event. Can only be done if the
user owns or is a participant of the event.

**Request:**
* sessionId: String
* eventId: Long

**Responses:**
* 200 OK: [EventDataDto](#eventdatadto)
* 401 UNAUTHORIZED: Invalid session
* 401 UNAUTHORIZED: Unauthorized (if the session doesn't belong to the event owner or participant)
* 404 NOT_FOUND: Event not found: ${eventId}

<br></br>
> **POST /events/retrieve/owned**

**Description:** retrieves the information about all the events that the user owns.

**Request:**
* sessionId: String

**Responses:**
* 200 OK: List<[EventDataDto](#eventdatadto)>
* 401 UNAUTHORIZED: Invalid session

<br></br>
> **POST /events/retrieve/authorized**

**Description:** retrieves the information about all the events that the user either owns or is invited to.
It's possible to provide a sort method in case you wish to retrieve to response in a sorted way.  
* NONE - unsorted
* POPULARITY - events with the most participants first
* DATE - events that are scheduled the earliest
* CREATION_TIME - events that were scheduled the earliest


**Request:**
* sessionId: String
* eventSortMethod: String (NONE | POPULARITY | DATE | CREATION_TIME) (optional)

**Responses:**
* 200 OK: List<[EventDataDto](#eventdatadto)>
* 401 UNAUTHORIZED: Invalid session

<br></br>
> **POST /events/retrieve/location**

**Description:** retrieves the information about all the events that happen in a location, and that the user is 
authorized to view (participant or owner).

**Request:**
* sessionId: String

**Responses:**
* 200 OK: List<[EventDataDto](#eventdatadto)>
* 401 UNAUTHORIZED: Invalid session

<br></br>
> **POST /events/create**

**Description:** creates a new event.

**Request:**
* name: String
* sessionId: String
* description: String
* location: String
* time: String, format: YYYY-MM-DDThh:mm:ss
* participants: List<String>

**Responses:**
* 201 CREATED: List<[EventDataDto](#eventdatadto)>
* 401 UNAUTHORIZED: Invalid session
* 404 NOT_FOUND: Some of the participants are invalid. (if a participant can't be found)

<br></br>
> **POST /events/delete/specific**

**Description:** deletes an existing event. Can only be done by the event owner.

**Request:**
* eventId: Long
* sessionId: String

**Responses:**
* 200 OK: List<[EventDataDto](#eventdatadto)>
* 401 UNAUTHORIZED: Invalid session
* 401 UNAUTHORIZED: Unauthorized. (if the user isn't the event owner)

### Data Transfer Objects

---
#### EventDataDto:
| Field name   | Field Type   | Format              |
|--------------|--------------|---------------------|
| id           | Long         | -                   |
| name         | String       | -                   |
| host         | String       | -                   |
| description  | String       | -                   |
| location     | String       | -                   |
| date         | String       | YYYY-MM-DDThh:mm:ss |
| creationTime | String       | YYYY-MM-DDThh:mm:ss |
| participants | List<String> | -                   |