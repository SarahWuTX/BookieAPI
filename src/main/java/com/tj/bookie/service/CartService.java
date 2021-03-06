package com.tj.bookie.service;

import com.tj.bookie.DAO.BookRepository;
import com.tj.bookie.DAO.CartRepository;
import com.tj.bookie.DAO.HistoryRepository;
import com.tj.bookie.DAO.UserRepository;
import com.tj.bookie.utility.Util;
import com.tj.bookie.utility.model.Book;
import com.tj.bookie.utility.model.Cart;
import com.tj.bookie.utility.model.History;
import com.tj.bookie.utility.model.User;
import com.tj.bookie.utility.request.InputCart;
import org.hibernate.cache.spi.CacheTransactionSynchronization;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;


@Transactional
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    public CartService(CartRepository cartRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }


    public ResponseEntity<?> save(InputCart inputCart) {
        Optional<User> user = userRepository.findByWxId(inputCart.getWxId());
        Optional<Book> book = bookRepository.findById(inputCart.getBookId());
        if (!user.isPresent() || !book.isPresent()) {
            return new ResponseEntity<>("对象不存在", HttpStatus.NOT_FOUND);
        }
        Cart cart;
        Optional<Cart> optionalCart = cartRepository.findByBookIdAndUserId(book.get(), user.get());
        if (optionalCart.isPresent()) {
            cart = optionalCart.get();
        } else {
            cart = new Cart();
            cart.setUserId(user.get());
            cart.setBookId(book.get());
        }
        cart.setCount(inputCart.getCount());
        return new ResponseEntity<>(cartRepository.save(cart), HttpStatus.OK);
    }


    public ResponseEntity<?> get(String wxId, Integer page) throws JSONException {
        int pageSize = 10;
        Optional<User> user = userRepository.findByWxId(wxId);
        if (!user.isPresent()) {
            return new ResponseEntity<>("对象不存在", HttpStatus.OK);
        }
        List<Cart> carts = cartRepository.findAllByUserIdOrderByTimeDesc(user.get(), PageRequest.of(page, pageSize)).getContent();
        JSONArray result = new JSONArray();
        for (Cart cart: carts) {
            JSONObject jsonBook = Util.parseBookToJSONFull(cart.getBookId());
            jsonBook.put("count", cart.getCount());
            jsonBook.put("cartId", cart.getId());
            result.put(jsonBook);
        }
        return new ResponseEntity<>(result.toString(), HttpStatus.OK);
    }


    public ResponseEntity<?> delete(String wxId, Integer bookId) {
        Optional<User> user = userRepository.findByWxId(wxId);
        Optional<Book> book = bookRepository.findById(bookId);
        if (!user.isPresent() || !book.isPresent()) {
            return new ResponseEntity<>("对象不存在", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cartRepository.deleteCartByBookIdAndUserId(book.get(), user.get()), HttpStatus.OK);
    }


    public ResponseEntity<?> getCount(String wxId, Integer bookId) {
        Optional<Cart> cart = cartRepository.findByBookId_IdAndUserId_WxId(bookId, wxId);
        return cart.map(cart1 -> new ResponseEntity<>(cart1.getCount(), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(0, HttpStatus.OK));
    }


    public ResponseEntity<?> getById(Integer cartId) throws JSONException {
        Optional<Cart> cart = cartRepository.findById(cartId);
        return cart.<ResponseEntity<?>>map(cart1 -> new ResponseEntity<>(cart1, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("对象不存在", HttpStatus.NOT_FOUND));
//        JSONObject jsonBook = Util.parseBookToJSONFull(cart.get().getBookId());
//        jsonBook.put("count", cart.get().getCount());
//        jsonBook.put("cartId", cart.get().getId());

    }


}
