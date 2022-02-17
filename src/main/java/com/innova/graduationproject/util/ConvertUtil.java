package com.innova.graduationproject.util;

import com.innova.graduationproject.dto.customer.CustomerRequestDto;
import com.innova.graduationproject.dto.customer.CustomerResponseDto;
import com.innova.graduationproject.entity.CreditScore;
import com.innova.graduationproject.entity.Customer;
import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class ConvertUtil {

    // TODO convertleri bi ayarla.

    public static CustomerRequestDto convertCustomerToCustomerRequestDto(Customer customer){
        return CustomerRequestDto.builder()
                .fullName(customer.getFullName())
                .identityNumber(customer.getIdentityNumber())
                .income(customer.getIncome())
                .phoneNumber(customer.getPhoneNumber())
                .build();
    }

    public static Customer convertCustomerRequestDtoToCustomer(CustomerRequestDto customerRequestDto){
        return Customer.builder()
                .fullName(customerRequestDto.getFullName())
                .identityNumber(customerRequestDto.getIdentityNumber())
                .income(customerRequestDto.getIncome())
                .phoneNumber(customerRequestDto.getPhoneNumber())
                .build();
    }

    public static CustomerResponseDto convertCustomerToCustomerResponseDto(Customer customer){
        return CustomerResponseDto.builder()
                .fullName(customer.getFullName())
                .identityNumber(customer.getIdentityNumber())
                .income(customer.getIncome())
                .phoneNumber(customer.getPhoneNumber())
                .id(customer.getId())
                .build();
    }

    public static Customer convertCustomerResponseDtoToCustomer(CustomerResponseDto customerResponseDto){
        return Customer.builder()
                .fullName(customerResponseDto.getFullName())
                .identityNumber(customerResponseDto.getIdentityNumber())
                .income(customerResponseDto.getIncome())
                .phoneNumber(customerResponseDto.getPhoneNumber())
                .id(customerResponseDto.getId())
                .build();
    }

    public static Customer convertCustomerRequestDtoToCustomerUpdate(Customer customer, CustomerRequestDto dto){
        customer.setIdentityNumber(dto.getIdentityNumber());
        customer.setFullName(dto.getFullName());
        customer.setIncome(dto.getIncome());
        customer.setPhoneNumber(dto.getPhoneNumber());
        return customer;
    }

    public static CreditScore generateCustomerCreditScore(Customer customer){

        // TODO bu random duzgun calismiyor olabilir.

        return CreditScore.builder()
                .score(new Random().nextInt(2000))
                .customer(customer)
                .build();
    }

}
