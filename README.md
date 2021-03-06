# Project Overview

A sample microservice built with Spring Boot and Gradle. There are APIs built
using REST and the resource is bicycle. All CRUD operations are supported 
through Rest API. 


## How to run

After downloading the project, run following command:  
**gradlew**

This will download all the dependencies for this app.

In order to start the server, run the BicyclemanagementApplication class. 
The application is now running on port 8081 as mentioned in application.properties.

Now you can call the APIs using Postman or cURL.

## How to run tests

In order to run tests, go to BicycleControllerTest. All tests were developed using 
Mockito and Junit.

##  How to verify
CRUD apis:  

GET /bicycles/  
GET /bicycles/{id}  
POST /bicycles  
PUT /bicycles/{id}  
DELETE /bicycles/{id}  

Sample payload for post request:

{  
"id": "900",  
"vendor": "Bianchi",  
"name": "Infinito",  
"price": 4000.00  
}