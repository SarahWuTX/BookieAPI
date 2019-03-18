package com.tj.bookie.service;

import com.tj.bookie.DAO.*;
import com.tj.bookie.utility.model.History;
import com.tj.bookie.utility.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    public ResponseEntity<?> getAll(String wxId, Integer page) {
        int pageSize = 10;
        List<Order> orders = orderRepository.findAllByUserId_WxId
                (wxId, PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "orderTime"))).getContent();
        orders = this.fulfillOrders(orders);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }


    public ResponseEntity<?> getByStatus(String wxId, Integer status) {
        List<Order> orders = orderRepository.findAllByUserId_WxIdAndStatus
                (wxId, status, Sort.by(Sort.Direction.DESC, "orderTime"));
        orders = this.fulfillOrders(orders);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }


    /**
     * complete some properties of each order
     * @param orders order list
     * @return modified order list
     */
    public List<Order> fulfillOrders(List<Order> orders) {
        for (Order order:orders) {
            order.setOrderId(String.format("%06d", order.getId()));
            int totalCount = 0;
            for (History h: order.getOrderList()) {
                totalCount += h.getCount();
            }
            order.setTotalCount(totalCount);
        }
        return orders;
    }


}
