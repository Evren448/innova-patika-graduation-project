package com.innova.graduationproject.controller;

import com.innova.graduationproject.dto.creditapplication.CreditApplicationResponseDto;
import com.innova.graduationproject.entity.Customer;
import com.innova.graduationproject.service.CreditApplicationService;
import com.innova.graduationproject.service.CustomerService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Validated
public class CreditApplicationController {

    private final CreditApplicationService creditApplicationService;
    private final CustomerService customerService;

    @GetMapping("/creditApplication")
    @ApiOperation(value = "Jump to credit_page", notes = "credit_page", httpMethod = "GET")
    public String getCreditApplication(Model model){
        model.addAttribute("customer", new Customer());
        return "credit_app";
    }

    @PostMapping("/creditApplication")
    @ApiOperation(value = "Create Credit Application by customer identity number then return credit_app page", notes = "credit_app", httpMethod = "POST")
    @ApiImplicitParam(name = "identityNumber", value = "Identity Number", required = true, dataTypeClass = String.class)
    public String createCreditApplication(@RequestParam(name = "identityNumber") String identityNumber, Model model) {

        CreditApplicationResponseDto savedCredit = this.creditApplicationService.save(identityNumber);
        Customer customer = this.customerService.findCustomerByIdentityNumber(identityNumber);

        model.addAttribute("credit", savedCredit);
        model.addAttribute("customer", customer);
        return "credit_app";
    }


    @GetMapping("/checkCredit")
    @ApiOperation(value = "Check Credit Application by customer identity number then return check page", notes = "check", httpMethod = "GET")
    @ApiImplicitParam(name = "identityNumber", value = "Identity Number", required = true, dataTypeClass = String.class)
    public String checkCreditScoreByIdentityNumber(@RequestParam("identityNumber") String identityNumber, Model model) {

        List<CreditApplicationResponseDto> creditApplicationByIdentityNumber = this.creditApplicationService.findCreditApplicationByIdentityNumber(identityNumber);
        Customer customer = this.customerService.findCustomerByIdentityNumber(identityNumber);

        model.addAttribute("creditList", creditApplicationByIdentityNumber);

        model.addAttribute("customer", customer);

        return "check";
    }

}
