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

# High performance CSV file loader and Database Persistence using Spring boot 2.0 

This sample application uses Spring Framework 5.0, Java 8 Streams, Java NIO and Spring Cache facilities to load big bulk
of data in CSV format and store them in Database (PostgreSQL).

Importing a large data from file system and save the respected data into database could be one of the tasks happens very often when your developing large applications. 
There are various ways to accomplish this but the one I used in this application has the aim of getting best execution speed
and minimum memory usage.

To obtain these features, I have used Spring framework 5.0 as my overall architecture and leverage below techniques:
   * Java 8 streams for streaming the data using Lazy mode
   * Java 8 Functional programming to increase the efficient of loops and minimize the code lines  
   * Spring Batch insert to insert data in batch instead of one by one
   * Internal Spring Cache system for lookup method
   * Spring Event mechanism to trigger an event to persist data into database when the Bucket is full
     with data. This prevent loading all data into memory (OutOfMemory error for large multi Gb files) perior
       to persistence. The method which works efficiently with small data set is already implemented and commented
      
   
## Prerequisites
   * You must have Java 8 SDK installed in your machine
   * PostgreSQL database installed and configured properly
   * Maven installed and bin folder is added to %PATH environment variable
    

## How to run the application

1. Open the project in your favorite IDE and open application.properties file
   * `change the value for data.file property`
   * `change spring.datasource properties to your desired one`
   * `spring.jpa.show-sql=true` will display the SQL queries on console
   * `spring.jpa.properties.hibernate.jdbc.batch_size=100` change this property value to your desired batch size
   * `spring.jpa.properties.hibernate.generate_statistics=true` will display the hibernate statistics on console to 
        observe how caching and batch insertion works. 
   * open `DataImporterApplication.java` and change the default values for static variables (startRange, endRange) if 
        your want bigger sample data file. 


2. Using command line   
   * run `mvn package` from project folder and wait for the build to be completed
   * `cd dist`
   * `java -jar DataImporter-1.0.jar`
   
3. expected output
   * The program will generate 10000 sample data (as default) and will store them into sample.csv file 
   * Then, it will lookup few instances based on Identifier value to make sure data stored successfully and
        the underlying caching mechanism works properly 

## Further improvement
   * You can change the default Spring Cache module and try different Caching providers such as:
       * `EhCache`     
       * `Hazelcast`    
       * `Infinispan`    
       * `Couchbase`    
       * `Redis`    
       * `Caffeine`
       
       Thanks to Spring boot, you can achieve above features with simply changing few properties within the project.
   
     
   * To improve SQL insertion in Big Data scenario, you might try below two approaches in order:
       * `Disabling the primary key indexes for those tables before insertion will give you another 10%-15% performance accelaration`    
       * `If need more insertion speed, try to use remove JPA part and use JDBC directly for another maybe 5% boosting but not too much`
       * `If need drastic changes to your insertion speed when working with large GB files, you should go for Database specific features  
            provided by each database engine such as MySQL 'Load Data' and PostgreSQL 'COPY'`
           
     
   * Feel free to clone the repository and use it for your own purpose. Good Luck!
    
