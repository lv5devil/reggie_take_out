package com.American.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@ResponseBody
@ControllerAdvice(annotations = {RestController.class, Controller.class})
public class GlobalExceptionHandler {
    @ExceptionHandler({SQLIntegrityConstraintViolationException.class})
    public R<String>exceptionHandler(SQLIntegrityConstraintViolationException ex){

        String message = ex.getMessage();
        if(message.contains("Duplicate entry")){
            String msg=message.split(" ")[2]+"已存在";
            return R.error(msg);
        }
        return R.error("未知错误");
    }
    @ExceptionHandler({CustomException.class})
    public R<String>exceptionHandler(CustomException ex){

        String message = ex.getMessage();

        return R.error(message);
    }
}
