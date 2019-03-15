package com.tj.bookie.DAO;

import com.tj.bookie.model.Admin;
import org.springframework.data.repository.CrudRepository;


public interface AdminRepository extends CrudRepository<Admin, Integer> {
    Admin findByName(String name);
}
