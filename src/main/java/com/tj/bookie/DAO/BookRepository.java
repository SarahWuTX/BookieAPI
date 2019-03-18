package com.tj.bookie.DAO;

import com.tj.bookie.utility.model.Book;

import com.tj.bookie.utility.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Transactional
public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query(value = "select * from t_book", nativeQuery = true)
    Page<Book> findAll(Pageable pageable);

    Optional<Book> findByIsbn(String isbn);

    Page<Book> findByStockIsLessThan(Integer stock, Pageable pageable);

}
