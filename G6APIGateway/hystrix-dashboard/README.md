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

# Histrix Dashboard Server    

This application runs Netflix Hystrix dashboard server

## Prerequisites
   * You must have Java 8 SDK installed in your machine
   * Maven installed and bin folder is added to %PATH environment variable
   
## How to run the application
 
 Using command line and Go to project folder:
* 1- 
    * cd `hystrix-dashboard` folder 
    * run `mvn spring-boot:run `   
 
  The application will be running on port 8877
  
  open browser with below url to watch the run time information on service invocations:
  * `http://localhost:8877/hystrix`
  
  enter this url in provided interface:
  * `http://localhost:8887/actuator/hystrix.stream`
     
     
    