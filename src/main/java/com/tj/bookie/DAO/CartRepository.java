package com.tj.bookie.DAO;

import com.tj.bookie.utility.model.Book;
import com.tj.bookie.utility.model.Cart;
import com.tj.bookie.utility.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CartRepository extends JpaRepository<Cart, Integer> {
    Optional<Cart> findByBookIdAndUserId(Book book, User user);
    Page<Cart> findAllByUserIdOrderByTimeDesc(User user, Pageable pageable);
    Integer deleteCartByBookIdAndUserId(Book book, User user);
}