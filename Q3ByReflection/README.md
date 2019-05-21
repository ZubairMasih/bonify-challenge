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

# Replace too many if-else statements using Java Reflection   

Assume we have below function in our application:

    public void processContract(String type) {
        if ("electricity".equals(type)) {
            System.out.println("Processed electricity");
        } else if ("dsl".equals(type)) {
            System.out.println("Processed dsl");
        } else if ("appartment_rent".equals(type)) {
            System.out.println("Processed appartment");
        }
    }

We need to add new if statement any time a new type introduced which ultimately ends up
with a lengthly messy implementation.  

This sample application demonstrate how you can replace the old fasion if-else statements with the 
help of Java reflections so that you will not to touch the implementation of this method any time later.


## Prerequisites
   * You must have Java 8 SDK installed in your machine
   * Maven installed and bin folder is added to %PATH environment variable
   
## How to run the application
 
 Using command line and Go to project folder and use either of ways to run it:
* 1- run application and pass input 'dsl' as an example
    * run `mvn spring-boot:run -Dspring-boot.run.arguments="dsl" `   
 
 * 2- Build the jar file and run jar file with given input   
   * run `mvn package -DskipTests `
   * `cd target`
   * `java -jar Reflection-1.0.jar dsl`
  