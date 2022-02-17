package com.innova.graduationproject.service;

import com.innova.graduationproject.dto.customer.CustomerRequestDto;
import com.innova.graduationproject.dto.customer.CustomerResponseDto;
import com.innova.graduationproject.entity.Customer;
import com.innova.graduationproject.exception.CustomerIsAlreadyExistException;
import com.innova.graduationproject.exception.EntityNotFoundException;
import com.innova.graduationproject.repository.CreditScoreRepository;
import com.innova.graduationproject.repository.CustomerRepository;
import com.innova.graduationproject.util.ConvertUtil;
import lombok.RequiredArgsConstructor;
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

        Optional<Customer> customerByIdentityNumber = this.customerRepository.findCustomerByIdentityNumber(customerRequestDto.getIdentityNumber());
        if(customerByIdentityNumber.isPresent()){
            throw new CustomerIsAlreadyExistException("Customer is already exist!");
        }

        Customer customer = ConvertUtil.convertCustomerRequestDtoToCustomer(customerRequestDto);
        customer.setCreditScore(ConvertUtil.generateCustomerCreditScore(customer));

        Customer savedCustomer = this.customerRepository.save(customer);

        return ConvertUtil.convertCustomerToCustomerResponseDto(savedCustomer);
    }

    public CustomerResponseDto update(CustomerRequestDto customerRequestDto) {

        Optional<Customer> customerByIdentityNumber = this.customerRepository.findCustomerByIdentityNumber(customerRequestDto.getIdentityNumber());
        if(!customerByIdentityNumber.isPresent()){
            throw new EntityNotFoundException("Customer not found!");
        }

        Customer updatedCustomer = ConvertUtil.convertCustomerRequestDtoToCustomerUpdate(customerByIdentityNumber.get(), customerRequestDto);
        Customer save = this.customerRepository.save(updatedCustomer);

        return ConvertUtil.convertCustomerToCustomerResponseDto(save);
    }

    public void delete(String identityNumber) {

        Optional<Customer> customerByIdentityNumber = this.customerRepository.findCustomerByIdentityNumber(identityNumber);
        if(!customerByIdentityNumber.isPresent()){
            throw new EntityNotFoundException("Customer not found!");
        }

        this.customerRepository.deleteCustomerByIdentityNumber(identityNumber);
    }

    public Page<Customer> findCustomers(int page) {

        Pageable pageable = PageRequest.of(page,5, Sort.by("id").ascending());

        return this.customerRepository.findAll(pageable);
    }

    public Customer findCustomerById(Long id){
        return this.customerRepository.findById(id).get();
        // TODO

        // TODO incelensin bi eksik fazla ayarla.
    }

    public Customer saveCustomer(Customer customer){
        return this.customerRepository.save(customer);
        // TODO
    }

    public Customer findCustomerByIdentityNumber(String identityNumber){
        Customer customer = this.customerRepository.findCustomerByIdentityNumber(identityNumber).orElseThrow(() -> new EntityNotFoundException("Customer not found!"));

        return customer;
        // TODO
    }
}
