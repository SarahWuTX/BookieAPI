package com.tj.bookie.DAO;

import com.tj.bookie.utility.model.Book;

import com.tj.bookie.utility.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Transactional
public interface BookRepository extends JpaRepository<Book, Integer> {
//    @Query(value = "select * from t_book", nativeQuery = true)
    Page<Book> findAll(Pageable pageable);

    @Query(value = "select * from t_book order by price*discount asc", nativeQuery = true)
    Page<Book> findAllOrderByPriceAsc(Pageable pageable);

    Optional<Book> findByIsbn(String isbn);

    Page<Book> findByStockIsLessThan(Integer stock, Pageable pageable);

    @Query(value = "select distinct(tb.id),isbn,tb.name,price,cover_url,discount,author,publisher,description,tb.stock,tb.sales " +
            "from t_book tb,t_book_category tbc,t_category tc " +
            "where tb.id=tbc.book_id and tc.id=tbc.category_id " +
            "and tc.id in (select distinct category_id " +
            "from t_book_category tbc join t_history th on tbc.book_id=th.book_id " +
            "where th.user_id = :uid)", nativeQuery = true)
    Page<Book> findBooksByUserPreference(@Param(value = "uid") Integer userId, Pageable pageable);

}
