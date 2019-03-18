package com.tj.bookie.DAO;

import com.tj.bookie.utility.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {
    Optional<Delivery> findByProvince(String province);
}
