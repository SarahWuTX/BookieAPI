package com.tj.bookie.DAO;

import com.tj.bookie.model.Order;
import org.springframework.data.repository.CrudRepository;


public interface OrderRepository extends CrudRepository<Order, Integer> {

}