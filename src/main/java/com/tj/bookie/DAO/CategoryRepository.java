package com.tj.bookie.DAO;

import com.tj.bookie.utility.model.Admin;
import com.tj.bookie.utility.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

//    @Query(value = "select t_category.name from t_history,t_book_category,t_book " +
//            "where book_id=t_book.id and category_id=t_category.id and book_id = :id", nativeQuery = true)
    List<Category> findByBooks_Id(Integer bookId);

}
