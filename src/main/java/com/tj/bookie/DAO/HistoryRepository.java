package com.tj.bookie.DAO;

import com.tj.bookie.utility.model.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Transactional
public interface HistoryRepository extends JpaRepository<History, Integer>, JpaSpecificationExecutor<History> {
    @Query(value = "select book_id, sum(count) sales " +
            "from t_history group by book_id order by sales desc", nativeQuery = true)
    Page<Map<String, Object>> findAllOrderBySalesDesc(Pageable pageable);

    @Query(value = "select sum(count) sales from t_history where book_id=:bookId", nativeQuery = true)
    Integer findSalesByBookId(@Param("bookId") Integer bookId);

    @Query(value = "select tc.name category, sum(th.count) sales " +
            "from t_category tc join t_book_category tbc on tc.id = tbc.category_id " +
            "join t_book tb on tbc.book_id = tb.id join t_history th on tb.id = th.book_id " +
            "join t_order t on th.order_id = t.id " +
            "where t.order_time <= :endD and t.order_time >= :beginD " +
            "group by tc.name", nativeQuery = true)
    List<Object[]> findSalesBetween(@Param("beginD") Date startDate, @Param("endD") Date endDate);

    @Query(value = "select c,count(u) from (select user_id u,sum(count) c from t_history group by user_id) tb " +
            "group by c order by c asc", nativeQuery = true)
    List<Object[]> findUserByCount();

}
