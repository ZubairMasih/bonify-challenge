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

package com.example.Q3ByEnum;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class Q3ByEnumApplication {

	public static void main(String[] args) {
		SpringApplication.run(Q3ByEnumApplication.class, args);
	}

}


@Component
class AppRunner implements CommandLineRunner {

	@Override
	public void run(String... args){
		try{
			processContract(args[0]);

		}catch (IllegalArgumentException e){
			System.out.println("type not found!");
		}
	}

	public void processContract(String type) throws IllegalArgumentException {
		Operation.valueOf(type).process();
	}
}

/*
	We need to keep updated the below Enum entries when a new type is introduced
 */

enum Operation {
	Eletricity {
		@Override
		void process() {
			System.out.println("electricity processed!");
		}
	},
	DSL{
		@Override
		void process() {
			System.out.printf("dsl processed!");
		}
	},
	Apartment  {
		@Override
		void process() {
			System.out.println("apartment processed!");
		}
	};

	abstract void process ();
}