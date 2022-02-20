package com.innova.graduationproject.controller;

import com.innova.graduationproject.dto.customer.CustomerRequestDto;
import com.innova.graduationproject.dto.customer.CustomerResponseDto;
//import com.innova.graduationproject.entity.CreditApplication;
import com.innova.graduationproject.entity.Customer;
//import com.innova.graduationproject.service.CreditApplicationService;
import com.innova.graduationproject.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
//    private final CreditApplicationService creditApplicationService;

    @PostMapping("/create")
    public String createCustomer(@Valid @ModelAttribute("customer") CustomerRequestDto customerRequestDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){ return "register"; }

        this.customerService.save(customerRequestDto);

        return "redirect:viewCustomers/0";

    }

    @GetMapping("/deleteCustomer/{identityNumber}")
    public String getDeleteCustomerByIdentityNumber(@PathVariable("identityNumber") String identityNumber){

        this.customerService.deleteByIdentityNumber(identityNumber);

        return "index";
    }

    @GetMapping("/viewCustomers/{page}")
    public String viewCustomers(@PathVariable("page") int page, Model m) {

        Page<Customer> customerList = this.customerService.findCustomers(page);

        m.addAttribute("pageNo", page);
        m.addAttribute("totalPage", customerList.getTotalPages());
        m.addAttribute("customers", customerList);
        m.addAttribute("totalElement", customerList.getTotalElements());

        return "view_customers";
    }

    @GetMapping("/updateCustomer/{id}")
    public String updateCustomer(@PathVariable("id") Long id, Model model) {

        CustomerRequestDto customer = this.customerService.findCustomerById(id);

        model.addAttribute("customer", customer);

        return "edit_customer";
    }

    @PostMapping("/updateCustomer")
    public String updateCustomer(@Valid @ModelAttribute("customer") CustomerRequestDto customerRequestDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "edit_customer";
        }
        //TODO update ederken ayni tel no ya patliyor.

        CustomerResponseDto updatedCustomer = this.customerService.update(customerRequestDto);

        return "index";
    }

//    @GetMapping("/check")
//    @ResponseBody
//    public void checkCreditScore(@ModelAttribute Customer customer){
//        Customer cstm = this.customerService.findCustomerByIdentityNumber(customer.getIdentityNumber());
//        System.out.println("customer " + customer.getIdentityNumber());
//        System.out.println("customer " + customer.getFullName());
//        System.out.println("cstm " + cstm.getIdentityNumber());
//        System.out.println("cstm " + cstm.getCreditScore().getScore());
//
//        //return cstm.toString();
//
//        //return "check";
//    }

//    @GetMapping("/check")
//    public String checkCreditScoreByIdentityNumber(@RequestParam("identityNumber") String identityNumber, Model model){
//
//        List<CreditApplication> creditApplication = this.creditApplicationService.findCreditApplicationByIdentityNumber(identityNumber);
//        Customer customer = this.customerService.findCustomerByIdentityNumber(identityNumber);
//
//        model.addAttribute("creditListSize", creditApplication);
//        model.addAttribute("customer", customer);
//
//        return "check";
//    }


}
