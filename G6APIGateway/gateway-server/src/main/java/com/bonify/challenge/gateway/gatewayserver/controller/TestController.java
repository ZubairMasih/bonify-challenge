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

package com.bonify.challenge.gateway.gatewayserver.controller;

import com.bonify.challenge.gateway.gatewayserver.clients.MovieClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@RestController
@RequestMapping ("/tests")
public class TestController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    MovieClient movieClient;

    @GetMapping ("/load")
    public void testLoadBalancing () {
        System.out.println("======= Load Testing  =================");
        IntStream.range(1, 100).forEach(i -> {
            new Thread(() -> {testMovieServiceLoadBalancing();}).start();
        });
    }

    public void testMovieServiceLoadBalancing() {
        String result= movieClient.getServiceName();
        System.out.println("Total movies: " + result);
        try {
            Thread.sleep(3000 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @GetMapping ("/fallback")
    @HystrixCommand(fallbackMethod = "fallback")
    public String getMovieServiceName() {
        if(ThreadLocalRandom.current().nextInt() > 0.5)
            throw new RuntimeException("Oh noooooo! Something bad happened");

        return movieClient.getServiceName();
    }

    public String fallback () {
        return "Sorry, movie service is not available at the moment. please come back shortly!";
    }
}
