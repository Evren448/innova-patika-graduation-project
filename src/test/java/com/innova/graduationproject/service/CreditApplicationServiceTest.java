package com.innova.graduationproject.service;

import com.innova.graduationproject.constant.CreditStatus;
import com.innova.graduationproject.dto.creditapplication.CreditApplicationRequestDto;
import com.innova.graduationproject.dto.creditapplication.CreditApplicationResponseDto;
import com.innova.graduationproject.dto.creditscore.CreditScoreResponseDto;
import com.innova.graduationproject.entity.CreditApplication;
import com.innova.graduationproject.entity.CreditScore;
import com.innova.graduationproject.entity.Customer;
import com.innova.graduationproject.exception.CreditApplicationNotFoundException;
import com.innova.graduationproject.exception.EntityNotFoundException;
import com.innova.graduationproject.repository.CreditApplicationRepository;
import com.innova.graduationproject.repository.CreditScoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class CreditApplicationServiceTest {
    @InjectMocks
    private CreditApplicationService creditApplicationService;
    @InjectMocks
    private CustomerService customerService;
    @InjectMocks
    private CreditScoreService creditScoreService;
    @InjectMocks
    private SMSService smsService;

    @Mock
    private CreditApplicationRepository creditApplicationRepository;
    @Mock
    private CreditScoreRepository creditScoreRepository;

    @Test
    public void given_validIdentityNumber_when_findCreditApplicationByIdentityNumber_then_returnCreditApplicationResponseDtoList() {

        String identityNumber = "12345678910";

        Customer customer = Customer.builder()
                .identityNumber(identityNumber)
                .creditScore(CreditScore.builder().score(500).build())
                .build();

        List<CreditApplication> creditApplicationList = new ArrayList() {{
            add(CreditApplication.builder().id(1L).creditValue(BigDecimal.valueOf(500)).creditStatus(CreditStatus.ACCEPTED).salary(BigDecimal.valueOf(500)).customer(customer).build());
        }};


        Mockito.when(creditApplicationRepository.findCreditApplicationByCustomerIdentityNumber(identityNumber)).thenReturn(creditApplicationList);

        List<CreditApplicationResponseDto> actual = creditApplicationService.findCreditApplicationByIdentityNumber(identityNumber);

        assertThat(actual.size()).isEqualTo(creditApplicationList.size());
        assertThat(actual.get(0).getCreditScore()).isEqualTo(creditApplicationList.get(0).getCustomer().getCreditScore().getScore());
        assertThat(actual.get(0).getCreditStatus()).isEqualTo(creditApplicationList.get(0).getCreditStatus());
        assertThat(actual.get(0).getCreditValue()).isEqualTo(creditApplicationList.get(0).getCreditValue());
        assertThat(actual.get(0).getSalary()).isEqualTo(creditApplicationList.get(0).getSalary());
        assertThat(actual.get(0).getCustomer()).isEqualTo(creditApplicationList.get(0).getCustomer());
        assertThat(actual.get(0).getId()).isEqualTo(creditApplicationList.get(0).getId());

    }

    @Test
    public void given_invalidIdentityNumber_when_findCreditApplicationByIdentityNumber_then_throwCreditApplicationNotFoundException() {
        String identityNumber = "12345678910";

        List<CreditApplication> emptyList = new ArrayList<>();

        Mockito.when(creditApplicationRepository.findCreditApplicationByCustomerIdentityNumber(identityNumber)).thenReturn(emptyList);

        Assertions.assertThrows(CreditApplicationNotFoundException.class, () -> creditApplicationService.findCreditApplicationByIdentityNumber(identityNumber));
    }


    @Test
    public void given_Level1_when_save_then_returnRejected() {
        //TODO

        //    private Long id;
//
//    private BigDecimal creditValue;
//
//    private CreditStatus creditStatus;
//
//    private Customer customer;
//
//    private BigDecimal salary;
//
//    private Integer creditScore;
//        String identityNumber = "12345678910";
//
//        Customer customer = Customer.builder()
//                .fullName("Test-fullname")
//                .id(1L)
//                .phoneNumber("Test-phone-number")
//                .income(BigDecimal.valueOf(5000))
//                .identityNumber(identityNumber)
//                .build();
//
//        CreditScoreResponseDto creditScoreResponseDto = CreditScoreResponseDto.builder()
//                .id(1L)
//                .score(400)
//                .customer(customer)
//                .build();
//
//        CreditApplication creditApplication = new CreditApplication();
//
//
//        Mockito.when(creditScoreService.findCreditScoreByCustomerIdentityNumber(identityNumber)).thenReturn(creditScoreResponseDto);
//
//        CreditApplicationResponseDto actual = creditApplicationService.save(identityNumber);
//
//        assertThat(actual.getCreditStatus()).isEqualTo(CreditStatus.REJECTED);

    }
}
