package com.innova.graduationproject.advice;

import com.innova.graduationproject.exception.CreditApplicationNotFoundException;
import com.innova.graduationproject.exception.CustomerIsAlreadyExistException;
import com.innova.graduationproject.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@ControllerAdvice
@Slf4j
public class CreditSystemControllerAdvice {

    public static final String DEFAULT_ERROR_VIEW = "generic-error";
    public static final String DEFAULT_ERROR_ATTRIBUTE_NAME = "errorMessage";

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ModelAndView handleEntityNotFoundException(HttpServletRequest req, EntityNotFoundException ex) {
        log.error(req.getServletPath() + " get error message: "+ ex.getMessage() + " at " + new Date());

        ModelAndView model = new ModelAndView(DEFAULT_ERROR_VIEW);
        model.addObject(DEFAULT_ERROR_ATTRIBUTE_NAME, ex.getMessage());
        model.addObject("statusCode", HttpStatus.NOT_FOUND.value());

        return model;
    }

    @ExceptionHandler(value = CreditApplicationNotFoundException.class)
    public ModelAndView handleCreditApplicationNotFoundException(HttpServletRequest req, CreditApplicationNotFoundException ex) {
        log.error(req.getServletPath() + " get error message: "+ ex.getMessage() + " at " + new Date());

        ModelAndView model = new ModelAndView(DEFAULT_ERROR_VIEW);
        model.addObject(DEFAULT_ERROR_ATTRIBUTE_NAME, ex.getMessage());
        model.addObject("statusCode", HttpStatus.NOT_FOUND.value());

        return model;
    }

    @ExceptionHandler(value = CustomerIsAlreadyExistException.class)
    public ModelAndView handleCustomerIsAlreadyExistException(HttpServletRequest req, CustomerIsAlreadyExistException ex) {
        log.error(req.getServletPath() + " get error message: "+ ex.getMessage() + " at " + new Date());

        ModelAndView model = new ModelAndView(DEFAULT_ERROR_VIEW);
        model.addObject(DEFAULT_ERROR_ATTRIBUTE_NAME, ex.getMessage());
        model.addObject("statusCode", HttpStatus.CONFLICT.value());
        System.out.println(req.getServletPath());

        return model;
    }

    @ExceptionHandler(value = Exception.class)
    public ModelAndView handleException(HttpServletRequest req, Exception ex) {

        log.error(req.getServletPath() + " get error message: "+ ex.getMessage() + " at " + new Date());

        ModelAndView model = new ModelAndView(DEFAULT_ERROR_VIEW);
        model.addObject(DEFAULT_ERROR_ATTRIBUTE_NAME, ex.getMessage());
        model.addObject("statusCode", HttpStatus.NOT_FOUND.value());

        return model;
    }



}
