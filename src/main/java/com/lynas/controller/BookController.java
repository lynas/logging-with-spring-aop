package com.lynas.controller;

import com.lynas.LogInputOutput;
import com.lynas.domain.Book;
import org.springframework.web.bind.annotation.*;

@RestController
class BookController {

    @LogInputOutput
    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable Long id){
        return new Book(id, "bookName " + id, "author" + id);
    }

    @LogInputOutput
    @PostMapping("/books/create")
    public Book createNewBook(@RequestBody Book book) {
        return book;
    }
}
