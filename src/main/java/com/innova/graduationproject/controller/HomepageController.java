package com.innova.graduationproject.controller;

import com.innova.graduationproject.dto.customer.CustomerRequestDto;
import com.innova.graduationproject.entity.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomepageController {

    @GetMapping
    public String viewHomepage(){
        return "index";
    }

    @GetMapping("/create")
    public String createCustomer(Model model){
        model.addAttribute("customer", new CustomerRequestDto());
        return "register";
    }

//    @GetMapping("/check")
//    public String checkCreditScore(Model model){
//        model.addAttribute("customer", new Customer());
//        return "check";
//    }
}
