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

package com.bonify.challenge.gateway.microservices.bookstore.controller;

import com.bonify.challenge.gateway.microservices.bookstore.domain.Book;
import com.bonify.challenge.gateway.microservices.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping ("/books")
public class BookStoreController {

    @Autowired
    BookService bookService;

    @GetMapping("")
    public ResponseEntity<List<Book>> getAllBooks(){
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable String isbn){
        Optional<Book> optBook = bookService.getBookByIsbn(isbn);
        if(optBook.isPresent())
            return ResponseEntity.ok(optBook.get());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Book());
    }

    @PostMapping ("/add")
    public void setBookName (@RequestParam String title, @RequestParam String isbn) {
        bookService.addBook(title, isbn);
    }
}
