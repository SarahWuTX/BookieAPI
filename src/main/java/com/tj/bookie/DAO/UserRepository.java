package com.tj.bookie.DAO;


import com.tj.bookie.utility.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByWxId(String wxId);
}
