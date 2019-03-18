package com.tj.bookie.service;

import com.tj.bookie.DAO.BookRepository;
import com.tj.bookie.DAO.HistoryRepository;
import com.tj.bookie.utility.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@Transactional
public class SalesService {
    private final BookRepository bookRepository;
    private final HistoryRepository historyRepository;

    @Autowired
    public SalesService(BookRepository bookRepository, HistoryRepository historyRepository) {
        this.bookRepository = bookRepository;
        this.historyRepository = historyRepository;
    }


    public ResponseEntity<?> getTrendByWeek() {
        Map<Integer, List<Map<String, Integer>>> results = new HashMap<>();
        Calendar day = Util.clearDate(Calendar.getInstance());
        Date begin;
        Date end;
        int week = day.get(Calendar.DAY_OF_WEEK);
        day.add(Calendar.DATE, -week-5);
        end = day.getTime();
        for (int i = 0; i < 7; i++) {
            begin = end;
            day.add(Calendar.DATE, 1);
            end = day.getTime();
            List<Map<String, Integer>> result = historyRepository.findSalesBetween(begin, end);
            results.put(i+1, result);
        }
        return new ResponseEntity<>(results, HttpStatus.OK);
    }


    public ResponseEntity<?> getTrendByMonth() {
        Map<Integer, List<Map<String, Integer>>> results = new HashMap<>();
        Calendar day = Util.clearDate(Calendar.getInstance());
        day.set(Calendar.DAY_OF_MONTH, 1);
        Date begin;
        Date end;
        day.add(Calendar.MONTH, -4);
        end = day.getTime();
        for (int i = 0; i < 4; i++) {
            begin = end;
            day.add(Calendar.MONTH, 1);
            end = day.getTime();
            List<Map<String, Integer>> result = historyRepository.findSalesBetween(begin, end);
            results.put(i+1, result);
        }
        return new ResponseEntity<>(results, HttpStatus.OK);
    }


    public ResponseEntity<?> getTrendBySeason() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
