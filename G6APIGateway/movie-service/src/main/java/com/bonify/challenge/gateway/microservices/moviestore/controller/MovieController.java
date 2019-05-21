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

package com.bonify.challenge.gateway.microservices.moviestore.controller;

import com.bonify.challenge.gateway.microservices.moviestore.domain.Movie;
import com.bonify.challenge.gateway.microservices.moviestore.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/movies")
public class MovieController {

    @Autowired
    MovieService moviesService;

    @Autowired
    Environment environment;


    @GetMapping("")
    public ResponseEntity<List<Movie>> getAllMovies(){
        return ResponseEntity.ok(moviesService.getAllMoviers());
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<List<Movie>> getMovieByYear(@PathVariable String year){
        return ResponseEntity.ok(moviesService.getMovieByYear(year));
    }

    @GetMapping ("/name")
    @ResponseBody
    public String getServiceName () {
        return "calling movie-service on port:" + environment.getProperty("server.port");
    }
}
