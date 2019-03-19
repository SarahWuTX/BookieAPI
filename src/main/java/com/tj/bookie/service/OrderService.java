package com.tj.bookie.service;

import com.tj.bookie.DAO.*;
import com.tj.bookie.utility.model.*;
import com.tj.bookie.utility.request.InputOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Transactional
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final HistoryRepository historyRepository;


    @Autowired
    public OrderService(OrderRepository orderRepository, DeliveryRepository deliveryRepository,
                        UserRepository userRepository, CartRepository cartRepository, HistoryRepository historyRepository) {
        this.orderRepository = orderRepository;
        this.deliveryRepository = deliveryRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.historyRepository = historyRepository;
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


    public ResponseEntity<?> create(InputOrder inputOrder) {
        // check user
        Optional<User> user = userRepository.findByWxId(inputOrder.getWxId());
        if (!user.isPresent()) {
            return new ResponseEntity<>("对象不存在", HttpStatus.NOT_FOUND);
        }
        // check carts
        List<Cart> carts = new ArrayList<>();
        for (Integer cartId: inputOrder.getCartList()) {
            Optional<Cart> cart = cartRepository.findById(cartId);
            if (!cart.isPresent()) {
                return new ResponseEntity<>("对象不存在", HttpStatus.NOT_FOUND);
            } else {
                carts.add(cart.get());
            }
        }

        // generate new order
        Order order = new Order();
        order.setUserId(user.get());
        order.setReceiverName(inputOrder.getReceiverName());
        order.setReceiverPhone(inputOrder.getReceiverPhone());
        order.setStatus(0);
        // delivery fee
        Optional<Delivery> delivery = deliveryRepository.findByProvince(inputOrder.getReceiverAddress().get(0));
        if (delivery.isPresent()) {
            order.setDeliveryFee(delivery.get().getDeliveryFee());
        } else {
            order.setDeliveryFee(0f);
        }
        // address
        StringBuilder address = new StringBuilder();
        inputOrder.getReceiverAddress().forEach(address::append);
        order.setReceiverAddress(address.toString());
        // total price
        float totalPrice = order.getDeliveryFee();
        for (Cart cart: carts) {
            totalPrice += cart.getBookId().getPrice()*cart.getBookId().getDiscount();
        }
        order.setTotalPrice(totalPrice);
        // save
        order = orderRepository.save(order);

        // generate history
        for (Cart cart: carts) {
            History history = new History(order, cart.getUserId().getId(), cart.getBookId(), cart.getCount());
            historyRepository.save(history);
        }

        return new ResponseEntity<>(order, HttpStatus.OK);
    }


    public ResponseEntity<?> pay (Integer orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (!order.isPresent()) {
            return new ResponseEntity<>("对象不存在", HttpStatus.NOT_FOUND);
        }
        order.get().setStatus(1);
        return new ResponseEntity<>(orderRepository.save(order.get()), HttpStatus.OK);
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
