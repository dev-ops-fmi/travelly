
# Travelly Project

## _The Best Travel App, Ever..._

Welcome to the Travelly project!
This repository contains a Java Spring Maven project
for a travel management application.

## Link to front end: https://github.com/panayot-marinov/travelly-frontend

## Features

### Trip Planning Interface

- Enable users to input trip details, such as destination, travel dates,
and preferences (e.g., budget, interests)
- Offer an interactive map that allows users to create and visualize itineraries
by adding activities, accommodations, and transportation options
- Provide a list of recommended activities, accommodations, and transportation 
options based on user preferences, budget, and travel dates

### Itinerary Builder and Timeline View

- Allow users to create and edit itineraries by adding, removing,
or rearranging activities, accommodations, and transportation options
- Offer a timeline view to visualize the trip itinerary and
ensure there are no scheduling conflicts

### Packing List Generator

- Provide a customizable packing list generator that takes into account 
the user's destination, travel dates, and planned activities
- Offer the ability to add, remove, or edit items on the packing list
- Enable users to mark items as packed and track their packing progress

## Table of Contents

- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
  - [Clone the Repository](#clone-the-repository)
  - [Configure the Database](#configure-the-database)
  - [Build the Project](#build-the-project)
  - [Run the Application](#run-the-application)
- [Contributing](#contributing)
- [License](#license)

## Prerequisites

Before you begin, ensure you have the following installed:

- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [PostgreSQL Server](https://www.postgresql.org/download/)
- [Git](https://git-scm.com/downloads)

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/tuechki/travelly.git
cd travelly
```

### Configure the Database

Create a PostgreSQL database named `travelly_db`.
Update the database configuration in `src/main/resources/application.properties`:

```sh
spring.datasource.url=jdbc:postgresql://localhost:5432/travelly_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Build the Project

Navigate to the project root directory and build the application using Maven:

```sh
mvn clean package
```

### Run the Application

Once the project is built, you can run the application:

```sh
java -jar target/travelly-*.jar
```

###### The application should now be accessible at http://localhost:8080.

### Open Swagger UI

```sh
Click here: http://localhost:8080/swagger-ui-travelly.html
```

### Exploring the API

On the Swagger UI page, you'll see a list of available APIs and their endpoints.
Click on an endpoint to view detailed information about the request and response
(including request parameters, response schemas, and example requests)

### API Endpoints

### User controller

```bash
GET
/users/{userId}/trips

POST
/users/{userId}/trips

POST
/users/register

POST
/users/login

PATCH
/users

GET
/users/{userId}

DELETE
/users/{userId}
```

### Trip controller

```bash
GET
/trips/{tripId}/itineraries

POST
/trips/{tripId}/itineraries

DELETE
/trips/{tripId}/itineraries

GET
/trips/{tripId}/items

POST
/trips/{tripId}/items

DELETE
/trips/{tripId}/items

PATCH
/trips

GET
/trips/{tripId}

DELETE
/trips/{tripId}

GET
/trips/{tripId}/transportationOptions/recommend

GET
/trips/{tripId}/activities/recommend

GET
/trips/{tripId}/accommodations/recommend
```

### Itinerary controller

```bash
GET
/itineraries/{itineraryId}/transportationOptions

POST
/itineraries/{itineraryId}/transportationOptions

DELETE
/itineraries/{itineraryId}/transportationOptions

GET
/itineraries/{itineraryId}/activities

POST
/itineraries/{itineraryId}/activities

DELETE
/itineraries/{itineraryId}/activities

GET
/itineraries/{itineraryId}/accommodations

POST
/itineraries/{itineraryId}/accommodations

DELETE
/itineraries/{itineraryId}/accommodations

GET
/itineraries/{itineraryId}

DELETE
/itineraries/{itineraryId}

PATCH
/itineraries/{itineraryId}

GET
/itineraries/{itineraryId}/transportationOptions/map

GET
/itineraries/{itineraryId}/activities/map

GET
/itineraries/{itineraryId}/accommodations/map
```

### Transportation option controller

```bash
DELETE
/transportationOption/{transportationOptionId}

PATCH
/transportationOption/{transportationOptionId}
```

### Item controller

```bash
DELETE
/items/{itemId}

PATCH
/items/{itemId}
```

### Activity controller

```bash
DELETE
/activities/{activityId}

PATCH
/activities/{activityId}
```

### Accommodation controller

```bash
DELETE
/accommodation/{accommodationId}

PATCH
/accommodation/{accommodationId}
```

### Making API Requests

To test an API endpoint, click the "Try it out" button.
Fill in any required parameters, headers, and request body (if applicable).
Click the "Execute" button to see the response.

### Authentication (If Applicable)

If the APIs require authentication, provide the necessary authentication
details using the Swagger UI interface before making requests.

## Contributing

Contributions are welcome! If you find any issues or have improvements to suggest,
please open an issue or create a pull request.

## License

This project is licensed under the [MIT License](https://opensource.org/license/mit/).
