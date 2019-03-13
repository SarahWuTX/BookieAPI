package com.tj.bookie.DAO;


import com.tj.bookie.model.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Integer> {

}
