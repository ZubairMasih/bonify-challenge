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

package com.example.urlfiltering;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class URLFilteringApplication {

	public static void main(String[] args) {
		SpringApplication.run(URLFilteringApplication.class, args);
	}

}


@Slf4j
@Component
class AppRunner implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		Set<String> urls = Stream.of("/users/esfand/info/birthday", "/users/majid/info/hello").collect(Collectors.toSet());

		processURLs(urls);
	}

	/*
		This method iterate the input URLs one by one and process each in parallel way
	 */
	public void processURLs (Set<String> urls ){
		urls.stream().parallel()
				.map(url -> url.substring(1).split("/"))
				.forEach(ProcessUrl::process);
	}
}

@Slf4j
@Component
class ProcessUrl {

	/*
		Processes the input segments coming as array with below structure
		part [0]: /users
		part [1]: *user_name*
		part [2]: /info
		part [3]: /*info*
	 */
	public static void process(String[] part) {
		log.warn("request is resolved for path " +"/users/"+ part[1] +"/info/" + part[3]);
	}
}