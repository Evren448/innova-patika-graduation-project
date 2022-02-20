package com.innova.graduationproject.service;

import com.innova.graduationproject.constant.ExceptionMessage;
import com.innova.graduationproject.dto.customer.CustomerRequestDto;
import com.innova.graduationproject.dto.customer.CustomerResponseDto;
import com.innova.graduationproject.entity.Customer;
import com.innova.graduationproject.exception.CustomerIsAlreadyExistException;
import com.innova.graduationproject.exception.EntityNotFoundException;
import com.innova.graduationproject.repository.CreditScoreRepository;
import com.innova.graduationproject.repository.CustomerRepository;
import com.innova.graduationproject.util.ConvertUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CreditScoreRepository creditScoreRepository;

    public CustomerResponseDto save(CustomerRequestDto customerRequestDto) {

        System.out.println(customerRequestDto.getIdentityNumber());

        Optional<Customer> customerByIdentityNumber = this.customerRepository.findCustomerByIdentityNumber(customerRequestDto.getIdentityNumber());
        if(customerByIdentityNumber.isPresent()){
            throw new CustomerIsAlreadyExistException(ExceptionMessage.CUSTOMER_IS_ALREADY_EXIST.getMessage());
        }

        Customer customer = ConvertUtil.convertCustomerRequestDtoToCustomer(customerRequestDto);

        customer.setCreditScore(ConvertUtil.generateCustomerCreditScore(customer));

        Customer savedCustomer = this.customerRepository.save(customer);

        return ConvertUtil.convertCustomerToCustomerResponseDto(savedCustomer);
    }

    public CustomerResponseDto update(CustomerRequestDto customerRequestDto) {

        Customer customer = this.customerRepository.findById(customerRequestDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.CUSTOMER_NOT_FOUND.getMessage()));

        Customer updatedCustomer = ConvertUtil.convertCustomerRequestDtoToCustomerUpdate(customer, customerRequestDto);

        Customer saved = this.customerRepository.save(updatedCustomer);

        return ConvertUtil.convertCustomerToCustomerResponseDto(saved);
    }

    public void deleteByIdentityNumber(String identityNumber) {

        Customer customerByIdentityNumber = this.customerRepository.findCustomerByIdentityNumber(identityNumber)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.CUSTOMER_NOT_FOUND.getMessage()));

        this.customerRepository.deleteById(customerByIdentityNumber.getId());
    }

    public Page<Customer> findCustomers(int page) {

        Pageable pageable = PageRequest.of(page,5, Sort.by("id").ascending());
        if(pageable.isUnpaged()){
            System.out.println("hata"); //TODO bakalim.
        }

        return this.customerRepository.findAll(pageable);
    }

    public CustomerRequestDto findCustomerById(Long id){

        Customer customer = this.customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.CUSTOMER_NOT_FOUND.getMessage()));

        return ConvertUtil.convertCustomerToCustomerRequestDto(customer);

    }


    public Customer findCustomerByIdentityNumber(String identityNumber){
        Customer customer = this.customerRepository.findCustomerByIdentityNumber(identityNumber)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.CUSTOMER_NOT_FOUND.getMessage()));

        return customer;
    }
}
