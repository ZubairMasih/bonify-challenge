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

package com.bonify.challenge.gateway.microservices.bookstore.service;

import com.bonify.challenge.gateway.microservices.bookstore.domain.Author;
import com.bonify.challenge.gateway.microservices.bookstore.domain.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private List<Book> books;

    public BookService() {
        this.books = new ArrayList<>();
        books.add(new Book("Becoming", "9781524763138", Arrays.asList(new Author("Michelle Obama"))));
        books.add(new Book("Liar Liar", "9780316418249",  Arrays.asList(new Author("James Patterson"), new Author("Candice Fox"))));
        books.add(new Book("The Chef", "9780316453301", Arrays.asList(new Author("Max DiLallo "), new Author("James Patterson"))));
        books.add(new Book("Stranger Things - Suspicious Minds", "9781984800886", Arrays.asList(new Author("Gwenda Bond"))));
    }

    public List<Book> getAllBooks(){
        return this.books;
    }

    public Optional<Book> getBookByIsbn(String isbn){
        return books.stream().filter(b -> b.getIsbn().equals(isbn)).findFirst();
    }

    public void addBook(String title, String isbn)
    {
        books.add(new Book(title, isbn, Arrays.asList(new Author("Unknown!!"))));
    }
}
