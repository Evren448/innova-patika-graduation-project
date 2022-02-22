package com.innova.graduationproject.controller;

import com.innova.graduationproject.dto.CreditDto;
import com.innova.graduationproject.dto.CustomerRestDto;
import com.innova.graduationproject.dto.creditapplication.CreditApplicationResponseDto;
import com.innova.graduationproject.dto.creditscore.CreditScoreResponseDto;
import com.innova.graduationproject.dto.customer.CustomerResponseDto;
import com.innova.graduationproject.entity.CreditApplication;
import com.innova.graduationproject.entity.Customer;
import com.innova.graduationproject.service.CreditApplicationService;
import com.innova.graduationproject.service.CreditScoreService;
import com.innova.graduationproject.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CreditApplicationController {

    private final CreditApplicationService creditApplicationService;
    private final CustomerService customerService;

    @GetMapping("/creditApplication")
    public String getCreditApplication(Model model){
        model.addAttribute("customer", new Customer());
        return "credit_app";
    }

    @PostMapping("/creditApplication")
    public String createCreditApplication(@RequestParam(name = "identityNumber") String identityNumber, @ModelAttribute("customer") Customer customer, Model model) {

        CreditApplicationResponseDto savedCredit = this.creditApplicationService.save(identityNumber);

        model.addAttribute("credit", savedCredit);
        return "credit_app";
    }


    @GetMapping("/checkCredit")
    public String checkCreditScoreByIdentityNumber(@RequestParam("identityNumber") String identityNumber, Model model) {

        List<CreditApplicationResponseDto> creditApplicationByIdentityNumber = this.creditApplicationService.findCreditApplicationByIdentityNumber(identityNumber);
        Customer customer = this.customerService.findCustomerByIdentityNumber(identityNumber);

        model.addAttribute("creditList", creditApplicationByIdentityNumber);

        model.addAttribute("customer", customer);

        return "check";
    }

}
