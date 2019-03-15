package com.tj.bookie.controller;

import com.tj.bookie.DAO.BookRepository;
import com.tj.bookie.model.Book;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
@Validated
public class BookController {
    private final BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository) { this.bookRepository = bookRepository; }

    @ApiOperation(value="获取书列表", notes="notes here")
    @GetMapping(path="/getAll")
    public Iterable<Book> getAll() {
        return bookRepository.findAll();
    }

}
