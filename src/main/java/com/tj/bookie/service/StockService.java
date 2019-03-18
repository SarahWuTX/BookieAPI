package com.tj.bookie.service;

import com.tj.bookie.DAO.BookRepository;
import com.tj.bookie.utility.Util;
import com.tj.bookie.utility.model.Book;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StockService {
    private final BookRepository bookRepository;

    @Autowired
    public StockService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public ResponseEntity<?> getAll(Integer page, Integer pageSize) throws JSONException {
        List<Book> books = bookRepository.findAll(PageRequest.of(page, pageSize)).getContent();
        JSONArray result = new JSONArray();
        for (Book book: books) {
            result.put(Util.parseBookToJSON(book));
        }
        return new ResponseEntity<>(result.toString(), HttpStatus.OK);
    }


    public ResponseEntity<?> getByIsbn(String isbn) throws JSONException {
        Optional<Book> book = bookRepository.findByIsbn(isbn);
        if (!book.isPresent()) {
            return new ResponseEntity<>("对象不存在", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(Util.parseBookToJSON(book.get()).toString(), HttpStatus.OK);
    }


    public ResponseEntity<?> notification(Integer page, Integer limit, Integer stock) throws JSONException {
        List<Book> books = bookRepository.findByStockIsLessThan
                (stock, PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "stock"))).getContent();
        JSONArray result = new JSONArray();
        for (Book book: books) {
            result.put(Util.parseBookToJSON(book));
        }
        return new ResponseEntity<>(result.toString(), HttpStatus.OK);
    }



}
