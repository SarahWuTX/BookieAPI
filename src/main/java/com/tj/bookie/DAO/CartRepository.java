package com.tj.bookie.DAO;

import com.tj.bookie.model.Cart;
import org.springframework.data.repository.CrudRepository;


public interface CartRepository extends CrudRepository<Cart, Integer> {

}