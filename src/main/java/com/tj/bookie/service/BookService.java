package com.tj.bookie.service;

import com.tj.bookie.DAO.BookRepository;
import com.tj.bookie.DAO.CategoryRepository;
import com.tj.bookie.DAO.HistoryRepository;
import com.tj.bookie.utility.Util;
import com.tj.bookie.utility.model.Book;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Transactional
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final HistoryRepository historyRepository;
    private final CategoryRepository categoryRepository;


    @Autowired
    public BookService(BookRepository bookRepository, HistoryRepository historyRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.historyRepository = historyRepository;
        this.categoryRepository = categoryRepository;
    }


    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(bookRepository.findAll(), HttpStatus.OK);
    }


    public ResponseEntity<?> getByPrice(Integer page) {
        int pageSize = 10;
        List<Book> books = bookRepository.findAll(PageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC, "price"))).getContent();
        for (Book book: books) {
            book.setCategory(Util.categoriesToString(categoryRepository.findByBooks_Id(book.getId())));
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }


    public ResponseEntity<?> getBySales(Integer page) {
        int pageSize = 10;
//        JSONArray result = new JSONArray();
//        List<Map<String, Object>> sales = historyRepository.findAllOrderBySalesDesc(PageRequest.of(page, pageSize)).getContent();
//        for (Map<String, Object> sales1: sales) {
//            JSONObject bookDetail = new JSONObject();
//            Optional<Book> book = bookRepository.findById(Integer.parseInt(sales1.get("book_id").toString()));
//            if (book.isPresent()) {
//                bookDetail = Util.parseBookToJSON(book.get());
//            }
//            bookDetail.put("category", Util.categoriesToString(categoryRepository.findByBooks_Id(Integer.parseInt(sales1.get("book_id").toString()))));
//            result.put(bookDetail);
//        }
        List<Book> books = bookRepository.findAll(PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "sales"))).getContent();
        for (Book book: books) {
            book.setCategory(Util.categoriesToString(categoryRepository.findByBooks_Id(book.getId())));
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }


    /**
     * get detail info of a book, including sales and category
     * @param bookId book id
     * @return json format book detail
     * @throws JSONException
     */
    public JSONObject getBookDetailById(Integer bookId) throws JSONException {
        Optional<Book> book = bookRepository.findById(bookId);
        JSONObject bookDetail = new JSONObject();
        if (book.isPresent()) {
            bookDetail = Util.parseBookToJSON(book.get());
        }
        bookDetail.put("category", Util.categoriesToString(categoryRepository.findByBooks_Id(bookId)));
        bookDetail.put("sales", historyRepository.findSalesByBookId(bookId));
        return bookDetail;
    }


}
