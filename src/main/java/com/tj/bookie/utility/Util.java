package com.tj.bookie.utility;

import com.tj.bookie.utility.model.Book;
import com.tj.bookie.utility.model.Category;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Util {

    /**
     * change list into string
     * @param categories categories of book
     * @return the result string
     */
    public static String categoriesToString(List<Category> categories) {
        StringBuilder categoryList = new StringBuilder();
        for (Category category: categories) {
            categoryList.append(category.getName());
            categoryList.append(" ");
        }
        return categoryList.toString();
    }

    /**
     * parse a book object to json object
     * @param book the book object
     * @return the json object
     */
    public static JSONObject parseBookToJSONFull(Book book) throws JSONException{
        JSONObject jsonBook = new JSONObject();
        jsonBook.put("id", book.getId());
        jsonBook.put("isbn", book.getIsbn());
        jsonBook.put("name", book.getName());
        jsonBook.put("coverUrl", book.getCoverUrl());
        jsonBook.put("author", book.getAuthor());
        jsonBook.put("publisher", book.getPublisher());
        jsonBook.put("price", book.getPrice());
        jsonBook.put("discount", book.getDiscount());
        jsonBook.put("description", book.getDescription());
        jsonBook.put("stock", book.getStock());
        jsonBook.put("sales", book.getSales());
        return jsonBook;
    }


    /**
     * parse a book object to json object
     * @param book the book to parse
     * @return the result json object
     * @throws JSONException
     */
    public static JSONObject parseBookToJSON(Book book) throws JSONException{
        JSONObject jsonBook = new JSONObject();
        jsonBook.put("isbn", book.getIsbn());
        jsonBook.put("title", book.getName());
        jsonBook.put("stock", book.getStock());
        jsonBook.put("sales", book.getSales());
        return jsonBook;
    }


    /**
     * change hour,minute,seconds to 0
     * @return date after cleaning
     */
    public static Calendar clearDate(Calendar day) {
        day.clear(Calendar.MILLISECOND);
        day.clear(Calendar.SECOND);
        day.clear(Calendar.MINUTE);
        day.set(Calendar.HOUR_OF_DAY, 0);
        return day;
    }
}
