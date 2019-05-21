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

package com.example.Q3ByFactory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Q3ByFactoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(Q3ByFactoryApplication.class, args);
    }

}

@Slf4j
@Component
class AppRunner implements CommandLineRunner {

    @Override
    public void run(String... args){
        try{
            processContract(args[0]);

        }catch (IllegalArgumentException e){
            log.error("type not found!");
        }
    }

    /*
        processes any input type
     */
    public void processContract(String type) throws IllegalArgumentException{
        FactoryOperations.getOperation(type.toLowerCase()).process();
    }
}

/*
    impose certain operation for all types
 */
interface IOperation {
    void process ();
}

/*
    Maintains a map of all existing types and return corresponding operation based on given type

    we need to keep type definition map updated once a new type introduced
 */
class FactoryOperations {

    static Map<String, IOperation> types = new HashMap<>();

    static {
        types.put("eletricity", () -> System.out.printf("electricity processed!"));
        types.put("dsl", () -> System.out.printf("dsl processed!"));
        types.put("apartment", () -> System.out.printf("apartment processed!"));
    }

    public static IOperation getOperation(String type) throws IllegalArgumentException {
        return types.get(type);
    }
}
