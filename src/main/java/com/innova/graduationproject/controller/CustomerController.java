package com.innova.graduationproject.controller;

import com.innova.graduationproject.dto.customer.CustomerRequestDto;
import com.innova.graduationproject.dto.customer.CustomerResponseDto;
import com.innova.graduationproject.entity.CreditApplication;
import com.innova.graduationproject.entity.Customer;
import com.innova.graduationproject.service.CreditApplicationService;
import com.innova.graduationproject.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final CreditApplicationService creditApplicationService;

//    @GetMapping("/create")
//    public String getCreateCustomer(Model model){
//        model.addAttribute("customer", new CustomerRequestDto());
//        return "create_customer_form";
//    }

//    @GetMapping("/update")
//    public String getUpdateCustomer(Model model){
//        model.addAttribute("customer", new CustomerRequestDto());
//        return "update_customer_form";
//    }



    @PostMapping("/create")
    public String createCustomer(@Valid @ModelAttribute("customer") CustomerRequestDto customerRequestDto, BindingResult bindingResult, HttpSession session){

        // TODO burda bi gariplik var bakalim.
        if(bindingResult.hasErrors()){
            System.out.println("hata");
            return "register";
        }
        CustomerResponseDto savedCustomer = this.customerService.save(customerRequestDto);

        return "index";

//        try {
//            CustomerResponseDto savedCustomer = this.customerService.save(customerRequestDto);
//            return "index";
//        } catch (CustomerExistException ex){
//            session.setAttribute("msg",ex.getMessage());
//            return "create_customer_form";
//        }
    }

//    @PostMapping("/update")
//    public String updateCustomer(@Valid @ModelAttribute("customer") CustomerRequestDto customerRequestDto, BindingResult bindingResult, HttpSession session){
//
//        if(bindingResult.hasErrors()){
//            System.out.println("hata");
//            return "update_customer_form";
//        }
//        CustomerResponseDto updatedCustomer = this.customerService.update(customerRequestDto);
//        return "index";
////
////        try {
////            CustomerResponseDto updatedCustomer = this.customerService.update(customerRequestDto);
////            return "index";
////        } catch (EntityNotFoundException ex){
////            session.setAttribute("msg",ex.getMessage());
////            return "update_customer_form";
////        }
//    }

    @GetMapping("/deleteCustomer/{identityNumber}")
    public String getDeleteCustomer(@PathVariable("identityNumber") String identityNumber){

        this.customerService.delete(identityNumber);
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
    public String updateCustomer(@PathVariable("id") Long id, Model m) {
        // TODO CustomerService'e tasinacak
        Customer customer = this.customerService.findCustomerById(id);
        if(customer != null){
            Customer updatedCustomer = customer;
            m.addAttribute("customer", customer);
            return "edit_customer";
        }
        return "redirect:/viewCustomers/0";
    }

    @PostMapping("/updateCustomer")
    public String updateCustomer(@ModelAttribute("customer") Customer customer, HttpSession session){


        Customer updateCustomer = this.customerService.findCustomerById(customer.getId());
        updateCustomer.setPhoneNumber(customer.getPhoneNumber());
        updateCustomer.setIncome(customer.getIncome());
        updateCustomer.setFullName(customer.getFullName());
        updateCustomer.setIdentityNumber(customer.getIdentityNumber());
        updateCustomer.setId(customer.getId());

        Customer updatedCustomer = this.customerService.saveCustomer(updateCustomer);
        if(updatedCustomer != null){
            session.setAttribute("msg", "Notes updated.");
        } else {
            session.setAttribute("msg", "Something wrong on server.");

        }

        return "redirect:customer/viewCustomers/0";
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
