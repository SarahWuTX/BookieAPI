package com.tj.bookie.DAO;

import com.tj.bookie.utility.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Integer> {
    Page<Order> findAllByUserId_WxId(String wxId, Pageable pageable);
    List<Order> findAllByUserId_WxIdAndStatus(String wxId, Integer status, Sort sort);
}