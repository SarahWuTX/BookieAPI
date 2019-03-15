package com.tj.bookie;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {
    //处理自定义的异常
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<?> customHandler(ConstraintViolationException e){
        e.printStackTrace();
        String message = "参数验证失败";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
    //其他未处理的异常
//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public ResponseEntity<?> exceptionHandler(Exception e){
//        e.printStackTrace();
//        return WebResult.buildResult().status(Config.FAIL).msg("系统错误");
//    }
}
