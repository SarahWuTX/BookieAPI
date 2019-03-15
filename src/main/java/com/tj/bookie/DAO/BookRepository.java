package com.tj.bookie.DAO;

import com.tj.bookie.model.Book;
import org.springframework.data.repository.CrudRepository;


public interface BookRepository extends CrudRepository<Book, Integer> {

}
