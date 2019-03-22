package com.tj.bookie;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.TransactionSystemException;
import java.text.ParseException;
import java.sql.SQLException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;


@ControllerAdvice(basePackages = "com.tj.bookie" )
public class GlobalExceptionHandler {
    //处理自定义的异常
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> constraintViolationExceptionHandler(ConstraintViolationException e){
        e.printStackTrace();
        return new ResponseEntity<>("参数验证失败", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> dataIntegrityViolationExceptionHandler(DataIntegrityViolationException e){
        e.printStackTrace();
        return new ResponseEntity<>("违反数据库约束", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<?> transactionSystemExceptionHandler(TransactionSystemException e){
        e.printStackTrace();
        return new ResponseEntity<>("错误的数据库操作", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
    public ResponseEntity<?> InvalidDataAccessResourceUsageExceptionHandler(InvalidDataAccessResourceUsageException e){
        e.printStackTrace();
        return new ResponseEntity<>("玄学错误，我搞不懂", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ParseException.class)
    public ResponseEntity<?> ParseExceptionHandler(ParseException e){
        e.printStackTrace();
        return new ResponseEntity<>("格式解析出错（日期）", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JSONException.class)
    public ResponseEntity<?> JSONExceptionHandler(JSONException e){
        e.printStackTrace();
        return new ResponseEntity<>("转化JSON出错", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    //其他未处理的异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<?> exceptionHandler(Exception e){
        e.printStackTrace();
        return new ResponseEntity<>("未处理的异常", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
