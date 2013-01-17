package org.encuestame.mvc.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public @ResponseBody String handleBusinessException(Exception ex) {
        System.out.print(ex);
        ex.printStackTrace();
        return "Handled BusinessException";
    }

}
