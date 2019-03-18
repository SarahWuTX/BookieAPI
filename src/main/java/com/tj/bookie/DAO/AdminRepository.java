package com.tj.bookie.DAO;

import com.tj.bookie.utility.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findByName(String name);
}
