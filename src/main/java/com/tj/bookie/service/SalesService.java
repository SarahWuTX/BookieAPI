package com.tj.bookie.service;

import com.tj.bookie.DAO.BookRepository;
import com.tj.bookie.DAO.CategoryRepository;
import com.tj.bookie.DAO.HistoryRepository;
import com.tj.bookie.utility.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;


@Service
@Transactional
public class SalesService {
    private final CategoryRepository categoryRepository;
    private final HistoryRepository historyRepository;

    @Autowired
    public SalesService(CategoryRepository categoryRepository, HistoryRepository historyRepository) {
        this.categoryRepository = categoryRepository;
        this.historyRepository = historyRepository;
    }


    public ResponseEntity<?> getTrendByWeek() {
        // init
        List<Map<String, Integer>> data = new ArrayList<>();
        List<String> timeline = new ArrayList<>();
        // deal with date
        Calendar day = Util.clearDate(Calendar.getInstance());
        Date begin;
        Date end;
        int week = day.get(Calendar.DAY_OF_WEEK);
        day.add(Calendar.DATE, -week-5);
        end = day.getTime();
        // add data of each time duration
        for (int i = 0; i < 7; i++) {
            begin = end;
            day.add(Calendar.DATE, 1);
            end = day.getTime();
            String title = new SimpleDateFormat("EE dd").format(begin);
            timeline.add(title);
            data.add(this.parseResultListToMap(historyRepository.findSalesBetween(begin, end)));
        }
        // generate results
        Map<String,List> results = this.formatResults(data);
        results.put("timeline", timeline);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }


    public ResponseEntity<?> getTrendByMonth() {
        // init
        List<Map<String, Integer>> data = new ArrayList<>();
        List<String> timeline = new ArrayList<>();
        // deal with date
        Calendar day = Util.clearDate(Calendar.getInstance());
        day.set(Calendar.DAY_OF_MONTH, 1);
        Date begin;
        Date end;
        day.add(Calendar.MONTH, -4);
        end = day.getTime();
        // add data of each time duration
        for (int i = 0; i < 4; i++) {
            begin = end;
            day.add(Calendar.MONTH, 1);
            end = day.getTime();
            String title = new SimpleDateFormat("yyyy.MM").format(begin);
            timeline.add(title);
            data.add(this.parseResultListToMap(historyRepository.findSalesBetween(begin, end)));
        }
        // generate results
        Map<String,List> results = this.formatResults(data);
        results.put("timeline", timeline);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }


    public ResponseEntity<?> getTrendBySeason() {
        // init
        List<Map<String, Integer>> data = new ArrayList<>();
        List<String> timeline = new ArrayList<>();
        // deal with date
        Calendar day = Util.clearDate(Calendar.getInstance());
        day.set(Calendar.DAY_OF_MONTH, 1);
        Date begin;
        Date end;
        int excludedMonth = day.get(Calendar.MONTH)%3;
        day.add(Calendar.MONTH, -excludedMonth-9);
        end = day.getTime();
        // add data of each time duration
        for (int i = 0; i < 3; i++) {
            begin = end;
            day.add(Calendar.MONTH, 2);
            String title1 = new SimpleDateFormat("yyyy.MM").format(begin);
            String title2 = new SimpleDateFormat("yyyy.MM").format(day.getTime());
            timeline.add(title1+"~"+title2);
            day.add(Calendar.MONTH, 1);
            end = day.getTime();
            data.add(this.parseResultListToMap(historyRepository.findSalesBetween(begin, end)));
        }
        // generate results
        Map<String,List> results = this.formatResults(data);
        results.put("timeline", timeline);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }


    public ResponseEntity<?> getDistributionBySeason(Integer nSeasonAgo){
        Calendar day = Util.clearDate(Calendar.getInstance());
        day.set(Calendar.DAY_OF_MONTH, 1);
        Date begin;
        Date end;
        int excludedMonth = day.get(Calendar.MONTH)%3;
        day.add(Calendar.MONTH, -excludedMonth-nSeasonAgo*3);
        begin = day.getTime();
        day.add(Calendar.MONTH, 3);
        end = day.getTime();
        Map<String, Integer> map = this.parseResultListToMap(historyRepository.findSalesBetween(begin, end));
        List<String> categories = categoryRepository.findName();
        categories.forEach(key -> {
            if (!map.containsKey(key)) {
                map.put(key, 0);
            }
        });
        return new ResponseEntity<>(map, HttpStatus.OK);
    }



    public ResponseEntity<?> getDistributionByCount() {
        List<Object[]> result = historyRepository.findUserByCount();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    /**
     * parse the result set from mysql into a map
     * @param result list
     * @return map
     */
    public Map<String, Integer> parseResultListToMap(List<Object[]> result){
        Map<String, Integer> map = new HashMap<>();
        for (Object[] cate: result) {
            map.put(cate[0].toString(), Integer.parseInt(cate[1].toString()));
        }
        return map;
    }


    /**
     * change data into what front-end wants
     * @param data list
     * @return map
     */
    public Map<String, List> formatResults(List<Map<String, Integer>> data) {
        Map<String, List> result = new HashMap<>();
        List<String> categories = categoryRepository.findName();
        for (Map<String, Integer> map: data) {
            categories.addAll(map.keySet());
        }
        categories.forEach(key -> {
            List<Integer> list = new ArrayList<>();
            for (Map<String, Integer> map: data) {
                list.add(map.getOrDefault(key, 0));
            }
            result.put(key, list);
        });

        return result;
    }




}
