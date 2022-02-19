package com.innova.graduationproject.Advice;

import com.innova.graduationproject.exception.CreditApplicationNotFoundException;
import com.innova.graduationproject.exception.CustomerIsAlreadyExistException;
import com.innova.graduationproject.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.HttpServletBean;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
@Slf4j
public class CreditSystemControllerAdvice {

    public static final String DEFAULT_ERROR_VIEW = "generic-error";
    public static final String DEFAULT_ERROR_ATTRIBUTE_NAME = "errorMessage";

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ModelAndView handleEntityNotFoundException(HttpServletRequest req, EntityNotFoundException ex) {
        ModelAndView model = new ModelAndView(DEFAULT_ERROR_VIEW);
        model.addObject(DEFAULT_ERROR_ATTRIBUTE_NAME, ex.getMessage());
        return model;
    }

    @ExceptionHandler(value = CreditApplicationNotFoundException.class)
    public ModelAndView handleEntityCreditApplicationNotFoundException(HttpServletRequest req, CreditApplicationNotFoundException ex) {
        ModelAndView model = new ModelAndView(DEFAULT_ERROR_VIEW);
        model.addObject(DEFAULT_ERROR_ATTRIBUTE_NAME, ex.getMessage());
        System.out.println(req.getServletPath());

        // TODO bu request e bi bak.
        return model;
    }

    @ExceptionHandler(value = CustomerIsAlreadyExistException.class)
    public ModelAndView handleEntityCustomerIsAlreadyExistException(HttpServletRequest req, CreditApplicationNotFoundException ex) {
        ModelAndView model = new ModelAndView(DEFAULT_ERROR_VIEW);
        model.addObject(DEFAULT_ERROR_ATTRIBUTE_NAME, ex.getMessage());
        return model;
    }

    @ExceptionHandler(value = Exception.class)
    public ModelAndView handleException(Exception ex) {
        ModelAndView model = new ModelAndView(DEFAULT_ERROR_VIEW);
        model.addObject(DEFAULT_ERROR_ATTRIBUTE_NAME, ex.getMessage());
        model.addObject("statusCode", HttpStatus.NOT_FOUND.value());

        return model;
    }



}
