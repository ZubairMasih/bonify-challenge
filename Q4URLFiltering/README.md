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

# Replace too many if-else statements using Rule Engine approach  

You have an input of the URLs: 
    
    “/users/Maria/info/location”, 
    “/users/Marcelo/info/birthday”

We are going to propose a design for an algorithm that given a set URL paths (as in example
above), can identify dynamic parts and resolve the masked URL to the path like this
“/users/*user_name*/info/*info_query*”

*   Algorithm should be as efficient as possible based on consideration about the CPU,
Memory and processing time.

*   *user_name* will represent the username values in the path. In our example it is
Maria or Marcelo.

*   *info_query* will represent the last part of the path. In our example location or
birthday.

*   The URLs can be dynamic, and can have multiple dynamic parts.

## Prerequisites
   * You must have Java 8 SDK installed in your machine
   * Maven installed and bin folder is added to %PATH environment variable
   
## How to run the application
 
 Using command line and Go to project folder and use either of ways to run it:
 * 1- run application    
   * run `mvn spring-boot:run `

 * 2- Build the jar file and run jar file    
   * run `mvn package -DskipTests `
   * `cd target`
   * `java -jar urlfiltering-1.0.jar dsl`
  