# Event Planner API
This API is used for event planning. 
You can use it to schedule events, and invite your friends to them.

## Architecture
The project comprises the following key components:

* **Sessions Redis:** Redis used to store user sessions.
* **PostgreSQL:** database utilized for storing information about registered users and event details.
* **EventPlanner:** The primary API serving as the core component. 
It orchestrates the functionality of the entire system.
## Setup Instructions
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
For a quickstart you can run the whole system by executing the command: 
```bash
docker compose up
```

### Running for development
If you want to run the API locally, make sure to set up the events PostgresSQL and the sessions Redis.
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

You can access the swagger documentation at ``http://localhost:8080``