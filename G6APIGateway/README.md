/**
 * Copyright 2019 Esfandiyar Talebi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

# API Gateway with Spring Boot 2.0    

This sample application demonstrate basic AGI Gateway implementation based on below technologies:
*   Netflix Eureka
*   Netflix Zuul Proxy
*   Netflix Hystrix
*   Netflix Ribbon
*   Spring Boot 2.0 

## Prerequisites
   * You must have Java 8 SDK installed in your machine
   * Maven installed and bin folder is added to %PATH environment variable
   
## How to run the application
 
 Using command line and Go to project folder:
* 1- Service Registry application
    * cd `service-registry` folder 
    * run `mvn spring-boot:run `   
 
  The application will be running on port 8888  
* 2- Movie Store service application with two instances each on different port for load balancing purpose   
   * cd `movie-service` folder
   * run `mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8070`   
   * cd `movie-service` folder
   * run `mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8071`
  
* 3- Book Store service application with two instances each on different port for load balancing purpose
   * cd `books-service` folder
   * run `mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8090`   
   * cd `books-service` folder
   * run `mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8091`

* 4- Run Hystrix Dashboard server
   * cd `hystrix-dashboard` folder
   * run `mvn spring-boot:run`
   The gateway will be running on port 8877 by default
           
* 5- Run Gateway server
   * cd `gateway-server` folder
   * run `mvn spring-boot:run`
   The gateway will be running on port 8887 by default and you will see list of instances for all registered services will be printed in console
              
* 6- open Eureka Dashboard in browser open port 8888 to see list of registered services and other runtime information
      
* 7- Test Api Gateway by invoking below methods using PostMan: 
   * http://localhost:8887/mapi/movies   
   displays the list of movies taken from Movie store service
   * http://localhost:8887/mapi/movies/name  
   displays the name of movie service running on taken port
   * http://localhost:8887/bapi/books  
   displays the list of books taken from Book store service
          
* 7- Test Api Gateway load balancing feature by invoking below method using PostMan: 
   * http://localhost:8887/tests/load   
      It displays the movie service invocations taken from difference instances            

* 8- Test Api Gateway failure handling feature by invoking below method using PostMan: 
   * http://localhost:8887/tests/fallback   
      It simulates the call to movie store service which throws exception but it return a descriptive message to user instead on error messages by calling fallback method            
            
                    