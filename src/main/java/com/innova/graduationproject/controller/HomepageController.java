package com.innova.graduationproject.controller;

import com.innova.graduationproject.dto.customer.CustomerRequestDto;
import com.innova.graduationproject.entity.Customer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Api(value = "HomepageController",tags = {"Homepage Controller"})
public class HomepageController {


    @ApiOperation(value = "Jump to index Home page",notes="index",httpMethod = "GET")
    @GetMapping
    public String viewHomepage(){
        return "index";
    }

    @ApiOperation(value = "Jump to register page",notes="register",httpMethod = "GET")
    @GetMapping("/create")
    public String createCustomer(Model model){
        model.addAttribute("customer", new CustomerRequestDto());
        return "register";
    }

    @ApiOperation(value = "Jump to check page",notes="check",httpMethod = "GET")
    @GetMapping("/check")
    public String checkCreditScore(Model model){
        model.addAttribute("customer", new Customer());
        return "check";
    }
}
