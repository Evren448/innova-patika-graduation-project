package com.innova.graduationproject.controller;

import com.innova.graduationproject.entity.CreditApplication;
import com.innova.graduationproject.entity.Customer;
import com.innova.graduationproject.service.CreditApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class CreditApplicationController {

    private final CreditApplicationService creditApplicationService;

    @GetMapping("/creditApplication")
    public String getCreditApplication(Model model){
        model.addAttribute("customer", new Customer());
        return "credit_app";
    }

    @PostMapping("/creditApplication")
    public String createCreditApplication(@RequestParam(name = "identityNumber") String identityNumber, @ModelAttribute("customer") Customer customer, Model model){

//        if(bindingResult.hasErrors()){
//            System.out.println("hata");
//            return "hata";
//        }
        CreditApplication savedCredit = this.creditApplicationService.save(identityNumber);

        model.addAttribute("credit", savedCredit);
        return "credit_app";

//        try {
//            CustomerResponseDto savedCustomer = this.customerService.save(customerRequestDto);
//            return "index";
//        } catch (CustomerExistException ex){
//            session.setAttribute("msg",ex.getMessage());
//            return "create_customer_form";
//        }
    }

}
