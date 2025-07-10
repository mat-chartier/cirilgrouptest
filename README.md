# cirilgrouptest

## To start the backend : 
```bash 
cd backend && ./mvnw spring-boot:run
```

## To start the frontend : 
```bash 
cd frontend && ng serve
```

## Start page: http://localhost:4200/

## Configuration
The configuration (height and width of the forest, propbability of the fire spreading to neighbour cells) is coded in the ForestFireConfiguration class. Shortcut decision to avoid dealing with loading .properties files.

## Architecture choices
- Backend and Frontend projects for clear separation and to avoid mixing responsibilities. 
- Backend based on Spring Boot for ease of API deployment (self contained application server mainly)
  - one parameters configuration file
  - one controller to serve the /api/next API to get the next forest state
  - one manager to implement business logic
  - one test class to cover key business logic (not complete coverage)
- Frontend based on Anuglar for ease of development (typescript, observable http requests, reactiv html... )
  - one service to call the backend API
  - one single app component for rendering the state of the poor forest

I chose to send the whole current forest state to the API simply to keep the stateless paradigm of REST APIs.
