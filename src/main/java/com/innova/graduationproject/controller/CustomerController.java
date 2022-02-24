package com.innova.graduationproject.controller;

import com.innova.graduationproject.dto.customer.CustomerRequestDto;
import com.innova.graduationproject.dto.customer.CustomerResponseDto;
//import com.innova.graduationproject.entity.CreditApplication;
import com.innova.graduationproject.entity.Customer;
//import com.innova.graduationproject.service.CreditApplicationService;
import com.innova.graduationproject.service.CustomerService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@Controller
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
//    private final CreditApplicationService creditApplicationService;

    @PostMapping("/create")
    @ApiOperation(value = "Register user, if its success show homepage", notes = "register", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "identityNumber", value = "Identity Number", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "fullName", value = "Full name", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "phoneNumber", value = "Phone Number", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "income", value = "Income", required = true, dataTypeClass = BigDecimal.class)})
    public String createCustomer(@Valid @ModelAttribute("customer") CustomerRequestDto customerRequestDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "register";
        }

        this.customerService.save(customerRequestDto);

        return "redirect:viewCustomers/0";

    }

    @GetMapping("/deleteCustomer/{identityNumber}")
    @ApiOperation(value = "Delete customer by identity number then return index page", notes = "index", httpMethod = "GET")
    @ApiImplicitParam(name = "identityNumber", value = "Identity Number", required = true, dataTypeClass = String.class)
    public String getDeleteCustomerByIdentityNumber(@PathVariable("identityNumber") String identityNumber) {

        this.customerService.deleteByIdentityNumber(identityNumber);

        return "index";
    }

    @ApiOperation(value = "Show all customers with page number then return view_customers page", notes = "index", httpMethod = "GET")
    @ApiImplicitParam(name = "page", value = "Page", required = true, dataTypeClass = int.class)
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
    @ApiOperation(value = "Find which customer will update with id then return edit_customer page", notes = "edit_customer", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "Customer Id", defaultValue = "0", required = true, dataTypeClass = Long.class)
    public String updateCustomer(@PathVariable("id") Long id, Model model) {

        CustomerRequestDto customer = this.customerService.findCustomerById(id);

        model.addAttribute("customer", customer);

        return "edit_customer";
    }

    @PostMapping("/updateCustomer")
    @ApiOperation(value = "Update customer, if its success show index page", notes = "index", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "id", value = "Id", required = true, dataTypeClass = Long.class),
            @ApiImplicitParam(name = "identityNumber", value = "Identity Number", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "fullName", value = "Full name", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "phoneNumber", value = "Phone Number", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "income", value = "Income", required = true , dataTypeClass = BigDecimal.class)})
    public String updateCustomer(@Valid @ModelAttribute("customer") CustomerRequestDto customerRequestDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "edit_customer";
        }
        //TODO update ederken ayni tel no ya patliyor.

        CustomerResponseDto updatedCustomer = this.customerService.update(customerRequestDto);

        return "index";
    }
}
