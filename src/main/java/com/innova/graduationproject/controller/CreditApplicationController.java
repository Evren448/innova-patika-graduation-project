package com.innova.graduationproject.controller;

import com.innova.graduationproject.dto.CreditDto;
import com.innova.graduationproject.dto.CustomerRestDto;
import com.innova.graduationproject.entity.CreditApplication;
import com.innova.graduationproject.entity.Customer;
import com.innova.graduationproject.service.CreditApplicationService;
import com.innova.graduationproject.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    public String createCreditApplication(@NotNull @RequestParam(name = "identityNumber") String identityNumber, @ModelAttribute("customer") Customer customer, Model model){

        Customer customerByIdentityNumber = this.customerService.findCustomerByIdentityNumber(identityNumber);

//        //Bu calisan kod
//        CustomerRestDto dto = CustomerRestDto.builder()
//                .creditScore(customerByIdentityNumber.getCreditScore().getScore())
//                .fullName(customerByIdentityNumber.getFullName())
//                .identityNumber(customerByIdentityNumber.getIdentityNumber())
//                .income(customerByIdentityNumber.getIncome())
//                .phoneNumber(customerByIdentityNumber.getPhoneNumber())
//                .build();
//
//        String URL = "http://localhost:8082/creditService/save";
//        RestTemplate restTemplate=new RestTemplate();
//        CreditDto savedCredit=restTemplate.postForObject(URL,dto,CreditDto.class);
//        //Bu calisan kod

        CustomerRestDto dto = CustomerRestDto.builder()
                .creditScore(customerByIdentityNumber.getCreditScore().getScore())
                .fullName(customerByIdentityNumber.getFullName())
                .identityNumber(customerByIdentityNumber.getIdentityNumber())
                .income(customerByIdentityNumber.getIncome())
                .phoneNumber(customerByIdentityNumber.getPhoneNumber())
                .build();

//// Prepare request
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
//        HttpEntity<CustomerRestDto> request = new HttpEntity<>(dto, headers);
//
//// Send prepared request
//        ResponseEntity<CreditDto> response = restTemplate.postForEntity("http://localhost:8082/creditService/save", request, CreditDto.class);
//        CreditDto savedCredit = response.getBody();

        String URL = "http://localhost:8082/creditService/save";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpEntity<CustomerRestDto> request = new HttpEntity<>(dto, headers);

        CreditDto savedCredit = restTemplate.exchange(URL, HttpMethod.POST, request, CreditDto.class).getBody();



//        CreditApplication savedCredit = this.creditApplicationService.save(identityNumber);

//        model.addAttribute("credit", savedCredit);
//        return "credit_app";


//        if(bindingResult.hasErrors()){
//            System.out.println("hata");
//            return "hata";
//        }
//        CreditApplication savedCredit = this.creditApplicationService.save(identityNumber);

//        String URL = "http://localhost:8082/creditService/save";
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<CreditApplication> postCredit = restTemplate.exchange(URL, HttpMethod.POST, HttpEntity.EMPTY, CreditApplication.class); //object
//        CreditApplication savedCredit = postCredit.getBody();

//        ResponseEntity<List<CreditApplication>> savedCredit = restTemplate.exchange(URL, HttpMethod.POST, null, new ParameterizedTypeReference<List<CreditApplication>>() {});
//        List<Example> exampleList = actualExample.getBody();

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

    @GetMapping("/check")
    public String checkCreditScore(Model model){
        model.addAttribute("customer", new Customer());
        return "check";
    }

    @GetMapping("/check1")
    public String checkCreditScoreByIdentityNumber(@RequestParam("identityNumber") String identityNumber, Model model){

        String URL = "http://localhost:8082/creditService/get/" + identityNumber;
        RestTemplate restTemplate = new RestTemplate();
        List<CreditDto> creditApplication = restTemplate.exchange(URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<CreditDto>>() {
        }).getBody();

        Customer customer = this.customerService.findCustomerByIdentityNumber(identityNumber);
        model.addAttribute("creditList", creditApplication);

        model.addAttribute("customer", customer);

        return "check";
    }

}
