package com.innova.graduationproject.service;

import com.innova.graduationproject.constant.CreditStatus;
import com.innova.graduationproject.dto.customer.CustomerRequestDto;
import com.innova.graduationproject.dto.customer.CustomerResponseDto;
import com.innova.graduationproject.entity.CreditApplication;
import com.innova.graduationproject.entity.CreditScore;
import com.innova.graduationproject.entity.Customer;
import com.innova.graduationproject.exception.CustomerIsAlreadyExistException;
import com.innova.graduationproject.exception.EntityNotFoundException;
import com.innova.graduationproject.repository.CustomerRepository;
import com.innova.graduationproject.util.ConvertUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    public void given_validCustomerRequestDto_when_CreateCustomer_then_ReturnCustomerResponseDto(){
        CustomerRequestDto customerRequestDto = CustomerRequestDto.builder()
                .phoneNumber("5555555555")
                .income(BigDecimal.valueOf(500))
                .identityNumber("55555555555")
                .fullName("Test-fullname")
                .build();

        Customer expectedCustomer = ConvertUtil.convertCustomerRequestDtoToCustomer(customerRequestDto);
        expectedCustomer.setId(1L);


        when(customerRepository.save(ArgumentMatchers.any(Customer.class))).thenReturn(expectedCustomer);

        CustomerResponseDto responseDto = customerService.save(customerRequestDto);

        Assertions.assertEquals(expectedCustomer.getIncome(), responseDto.getIncome());
        Assertions.assertEquals(expectedCustomer.getFullName(), responseDto.getFullName());
        Assertions.assertEquals(expectedCustomer.getIdentityNumber(), responseDto.getIdentityNumber());
        Assertions.assertEquals(expectedCustomer.getPhoneNumber(), responseDto.getPhoneNumber());
        Assertions.assertEquals(expectedCustomer.getId(), responseDto.getId());
    }

    @Test
    public void given_InvalidIdentityNumber_then_throwEntityNotFoundException(){
        String identityNumber = "test-identitynumber";
        CustomerRequestDto customerRequestDto = CustomerRequestDto.builder().identityNumber(identityNumber).build();

        Customer customer = new Customer();

        when(customerRepository.findCustomerByIdentityNumber(identityNumber)).thenReturn(Optional.of(customer));

        Assertions.assertThrows(CustomerIsAlreadyExistException.class, () -> customerService.save(customerRequestDto));
        verify(customerRepository, times(1)).findCustomerByIdentityNumber(customerRequestDto.getIdentityNumber());
    }

    @Test
    public void given_invalidIdentityNumber_then_throwEntityNotFoundException(){

        String identityNumber = "12345678910";

        when(customerRepository.findCustomerByIdentityNumber(identityNumber)).thenReturn(Optional.ofNullable(null));

        Assertions.assertThrows(EntityNotFoundException.class, () -> customerService.deleteByIdentityNumber(identityNumber));
        verify(customerRepository, times(1)).findCustomerByIdentityNumber(identityNumber);
    }

    @Test
    public void given_validIdentityNumber_then_deleteCustomerByIdentityNumber(){

        String identityNumber = "12345678910";

        Customer customer = new Customer();

        when(customerRepository.findCustomerByIdentityNumber(identityNumber)).thenReturn(Optional.of(customer));

        customerService.deleteByIdentityNumber(identityNumber);
        verify(customerRepository, times(1)).findCustomerByIdentityNumber(identityNumber);
    }

    @Test
    public void given_invalidId_then_throwEntityNotFoundException(){
        Long id = 1L;

        when(customerRepository.findById(id)).thenReturn(Optional.ofNullable(null));

        Assertions.assertThrows(EntityNotFoundException.class, () -> customerService.findCustomerById(id));
    }

    @Test
    public void given_validId_then_returnCustomerRequestDto(){
        Long id = 1L;
        Customer customer = Customer.builder()
                .id(id)
                .fullName("Test-fullname")
                .identityNumber("Test-identity-number")
                .income(BigDecimal.valueOf(500))
                .phoneNumber("Test-phone-number")
                .build();

        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

        CustomerRequestDto actual = customerService.findCustomerById(id);

        Assertions.assertEquals(customer.getIncome(), actual.getIncome());
        Assertions.assertEquals(customer.getFullName(), actual.getFullName());
        Assertions.assertEquals(customer.getIdentityNumber(), actual.getIdentityNumber());
        Assertions.assertEquals(customer.getPhoneNumber(), actual.getPhoneNumber());
        Assertions.assertEquals(customer.getId(), actual.getId());
    }

    @Test
    public void given_invalidIdentityNumber_when_findCustomerByIdentityNumber_then_throwEntityNotFoundException(){
        String identityNumber = "12345678910";

        when(customerRepository.findCustomerByIdentityNumber(identityNumber)).thenReturn(Optional.ofNullable(null));

        Assertions.assertThrows(EntityNotFoundException.class, () -> customerService.findCustomerByIdentityNumber(identityNumber));
    }

    @Test
    public void given_validIdentityNumber_when_findCustomerByIdentityNumber_then_returnCustomer(){
        String identityNumber = "12345678910";
        Customer customer = Customer.builder()
                .fullName("Test-fullname")
                .id(1L)
                .phoneNumber("Test-phone-number")
                .income(BigDecimal.valueOf(5000))
                .identityNumber(identityNumber)
                .creditScore(CreditScore.builder().score(500).id(1L).build())
                .creditApplicationList(Arrays.asList(CreditApplication.builder().creditStatus(CreditStatus.ACCEPTED).creditValue(BigDecimal.valueOf(500)).salary(BigDecimal.valueOf(5000)).id(1L).build()))
                .build();

        when(customerRepository.findCustomerByIdentityNumber(identityNumber)).thenReturn(Optional.of(customer));

        Customer actual = customerService.findCustomerByIdentityNumber(identityNumber);

        Assertions.assertEquals(customer, actual);
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    public void given_validCustomerRequestDto_when_updateCustomer_then_returnCustomerResponseDto(){

        CustomerRequestDto customerRequestDto = CustomerRequestDto.builder()
                .phoneNumber("Test-phone-number")
                .income(BigDecimal.valueOf(500))
                .identityNumber("Test-identity-number")
                .fullName("Test-fullname")
                .build();
        Customer customer = ConvertUtil.convertCustomerRequestDtoToCustomer(customerRequestDto);
        customer.setId(1L);

        when(customerRepository.findById(customerRequestDto.getId())).thenReturn(Optional.of(customer));
        when(customerRepository.save(ArgumentMatchers.any(Customer.class))).thenReturn(customer);

        CustomerResponseDto actual = customerService.update(customerRequestDto);

        Assertions.assertEquals(customer.getIncome(), actual.getIncome());
        Assertions.assertEquals(customer.getFullName(), actual.getFullName());
        Assertions.assertEquals(customer.getIdentityNumber(), actual.getIdentityNumber());
        Assertions.assertEquals(customer.getPhoneNumber(), actual.getPhoneNumber());
        Assertions.assertEquals(customer.getId(), actual.getId());

        assertThat(customer).isNotNull();
        assertThat(customer.getId()).isNotNull();
    }

    @Test
    public void given_invalidCustomerRequestDto_when_updateCustomer_then_returnCustomerResponseDto(){
        CustomerRequestDto customerRequestDto = CustomerRequestDto.builder().id(1L).build();

        when(customerRepository.findById(customerRequestDto.getId())).thenReturn(Optional.ofNullable(null));

        Assertions.assertThrows(EntityNotFoundException.class, () -> customerService.update(customerRequestDto));
    }

    @Test
    public void pageableTest(){
        int page = 0;
        Pageable pageable = PageRequest.of(page,5, Sort.by("id").ascending());

        List<Customer> customerList = new ArrayList() {{
            add(Customer.builder().id(2L).identityNumber("Test-identity-number-1").build());
            add(Customer.builder().id(1L).identityNumber("Test-identity-number-2").build());
        }};

        Page<Customer> pageList = new PageImpl<>(customerList);

        when(customerRepository.findAll(pageable)).thenReturn(pageList);

        Page<Customer> actual = customerService.findCustomers(page);

        assertThat(pageList).isNotNull();
        assertThat(pageList.getTotalElements()).isEqualTo(actual.getTotalElements());
        assertThat(pageList.getTotalPages()).isEqualTo(actual.getTotalPages());
        assertThat(pageList.getSize()).isEqualTo(actual.getSize());
        assertThat(pageList.stream().findFirst().get().getIdentityNumber()).isEqualTo(actual.getContent().get(0).getIdentityNumber());
    }
}
