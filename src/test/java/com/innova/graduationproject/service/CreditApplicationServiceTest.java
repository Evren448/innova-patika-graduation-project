package com.innova.graduationproject.service;

import com.innova.graduationproject.constant.CreditLimitMultiplier;
import com.innova.graduationproject.constant.CreditStatus;
import com.innova.graduationproject.dto.creditapplication.CreditApplicationResponseDto;
import com.innova.graduationproject.dto.creditscore.CreditScoreResponseDto;
import com.innova.graduationproject.entity.CreditApplication;
import com.innova.graduationproject.entity.CreditScore;
import com.innova.graduationproject.entity.Customer;
import com.innova.graduationproject.exception.CreditApplicationNotFoundException;
import com.innova.graduationproject.repository.CreditApplicationRepository;
import com.innova.graduationproject.repository.CreditScoreRepository;
import com.innova.graduationproject.util.ConvertUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreditApplicationServiceTest {
    @InjectMocks
    private CreditApplicationService creditApplicationService;

    @Mock
    private CustomerService customerService;

    @Mock
    private CreditScoreService creditScoreService;

    @Mock
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


        when(creditApplicationRepository.findCreditApplicationByCustomerIdentityNumber(identityNumber)).thenReturn(creditApplicationList);

        List<CreditApplicationResponseDto> actual = creditApplicationService.findCreditApplicationByIdentityNumber(identityNumber);

        assertThat(actual.size()).isEqualTo(creditApplicationList.size());
        assertThat(actual.size()).isGreaterThanOrEqualTo(1);
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

        when(creditApplicationRepository.findCreditApplicationByCustomerIdentityNumber(identityNumber)).thenReturn(emptyList);

        assertThat(emptyList.size()).isGreaterThanOrEqualTo(0);
        Assertions.assertThrows(CreditApplicationNotFoundException.class, () -> creditApplicationService.findCreditApplicationByIdentityNumber(identityNumber));
    }


    @Test
    public void given_validIdentityNumber_whenSaveCreditApplicationWithLine1_then_returnRejected() {

        String identityNumber = "12345678910";
        Integer Line1 = 499;

        CreditScore creditScore = CreditScore.builder().score(Line1).id(1L).build();
        Customer customer = Customer.builder().id(1L).fullName("Test-fullname").phoneNumber("test-phonenumber").identityNumber(identityNumber).creditScore(creditScore).income(BigDecimal.valueOf(5000)).build();

        CreditScoreResponseDto responseDto = ConvertUtil.convertCreditToCreditScoreResponseDto(creditScore);

        CreditApplication value = CreditApplication.builder().creditStatus(CreditStatus.REJECTED).creditValue(BigDecimal.ZERO).id(1L).salary(BigDecimal.valueOf(5000)).customer(customer).build();
        CreditApplicationResponseDto expected = ConvertUtil.convertCreditApplicationToCreditApplicationResponseDto(value);

        when(creditScoreService.findCreditScoreByCustomerIdentityNumber(ArgumentMatchers.any(String.class))).thenReturn(responseDto);
        when(customerService.findCustomerByIdentityNumber(ArgumentMatchers.any(String.class))).thenReturn(customer);
        when(creditApplicationRepository.save(ArgumentMatchers.any(CreditApplication.class))).thenReturn(value);

        CreditApplicationResponseDto actual = creditApplicationService.save(identityNumber);

        assertThat(actual.getCreditStatus().getStatus()).isEqualTo(CreditStatus.REJECTED.getStatus()).isEqualTo(expected.getCreditStatus().getStatus());
        assertThat(actual.getCreditValue()).isEqualTo(expected.getCreditValue());
        assertThat(actual.getCustomer()).isEqualTo(expected.getCustomer());
        assertThat(actual.getCustomer().getId()).isNotNull();
        assertThat(actual.getId()).isNotNull().isEqualTo(expected.getId());
        assertThat(actual.getCustomer().getIncome()).isNotNull().isEqualTo(expected.getSalary());
        assertThat(actual.getCreditScore()).isEqualTo(expected.getCustomer().getCreditScore().getScore());

    }

    @Test
    public void given_validIdentityNumber_whenSaveCreditApplicationWithLine2_then_returnAcceptedWithCreditValue10000() {

        String identityNumber = "12345678910";
        Integer Line2 = 700;
        BigDecimal incomeLessThan5000 = BigDecimal.valueOf(4999);

        CreditScore creditScore = CreditScore.builder().score(Line2).id(1L).build();
        Customer customer = Customer.builder().id(1L).fullName("Test-fullname").phoneNumber("test-phonenumber").identityNumber(identityNumber).creditScore(creditScore) .income(incomeLessThan5000).build();

        CreditScoreResponseDto responseDto = ConvertUtil.convertCreditToCreditScoreResponseDto(creditScore);
        CreditApplication value = CreditApplication.builder().creditStatus(CreditStatus.ACCEPTED).creditValue(BigDecimal.valueOf(10000)).id(1L).salary(incomeLessThan5000).customer(customer).build();
        CreditApplicationResponseDto expected = ConvertUtil.convertCreditApplicationToCreditApplicationResponseDto(value);

        when(creditScoreService.findCreditScoreByCustomerIdentityNumber(ArgumentMatchers.any(String.class))).thenReturn(responseDto);
        when(customerService.findCustomerByIdentityNumber(ArgumentMatchers.any(String.class))).thenReturn(customer);
        when(creditApplicationRepository.save(ArgumentMatchers.any(CreditApplication.class))).thenReturn(value);

        CreditApplicationResponseDto actual = creditApplicationService.save(identityNumber);

        assertThat(actual.getCreditStatus().getStatus()).isEqualTo(CreditStatus.ACCEPTED.getStatus()).isEqualTo(expected.getCreditStatus().getStatus());
        assertThat(actual.getCreditValue()).isEqualTo(expected.getCreditValue());
        assertThat(actual.getCustomer()).isEqualTo(expected.getCustomer());
        assertThat(actual.getCustomer().getId()).isNotNull();
        assertThat(actual.getId()).isNotNull().isEqualTo(expected.getId());
        assertThat(actual.getCustomer().getIncome()).isNotNull().isEqualTo(expected.getSalary());
        assertThat(actual.getCreditScore()).isEqualTo(expected.getCustomer().getCreditScore().getScore());
    }

    @Test
    public void given_validIdentityNumber_whenSaveCreditApplicationWithLine3_then_returnAcceptedWithCreditValue10000() {

        String identityNumber = "12345678910";
        Integer Line3 = 700;
        BigDecimal incomeBetween5000and10000 = BigDecimal.valueOf(5001);

        CreditScore creditScore = CreditScore.builder().score(Line3).id(1L).build();
        Customer customer = Customer.builder().id(1L).fullName("Test-fullname").phoneNumber("test-phonenumber").identityNumber(identityNumber).creditScore(creditScore) .income(incomeBetween5000and10000).build();

        CreditScoreResponseDto responseDto = ConvertUtil.convertCreditToCreditScoreResponseDto(creditScore);
        CreditApplication value = CreditApplication.builder().creditStatus(CreditStatus.ACCEPTED).creditValue(BigDecimal.valueOf(20000)).id(1L).salary(incomeBetween5000and10000).customer(customer).build();
        CreditApplicationResponseDto expected = ConvertUtil.convertCreditApplicationToCreditApplicationResponseDto(value);

        when(creditScoreService.findCreditScoreByCustomerIdentityNumber(ArgumentMatchers.any(String.class))).thenReturn(responseDto);
        when(customerService.findCustomerByIdentityNumber(ArgumentMatchers.any(String.class))).thenReturn(customer);
        when(creditApplicationRepository.save(ArgumentMatchers.any(CreditApplication.class))).thenReturn(value);

        CreditApplicationResponseDto actual = creditApplicationService.save(identityNumber);

        assertThat(actual.getCreditStatus().getStatus()).isEqualTo(CreditStatus.ACCEPTED.getStatus()).isEqualTo(expected.getCreditStatus().getStatus());
        assertThat(actual.getCreditValue()).isEqualTo(expected.getCreditValue());
        assertThat(actual.getCustomer()).isEqualTo(expected.getCustomer());
        assertThat(actual.getCustomer().getId()).isNotNull();
        assertThat(actual.getId()).isNotNull().isEqualTo(expected.getId());
        assertThat(actual.getCustomer().getIncome()).isNotNull().isEqualTo(expected.getSalary());
        assertThat(actual.getCreditScore()).isEqualTo(expected.getCustomer().getCreditScore().getScore());
    }

    @Test
    public void given_validIdentityNumber_whenSaveCreditApplicationWithLine4_then_returnAcceptedWithCreditValue10000() {

        String identityNumber = "12345678910";
        Integer Line3 = 1100;
        BigDecimal income = BigDecimal.valueOf(4000);

        CreditScore creditScore = CreditScore.builder().score(Line3).id(1L).build();
        CreditScoreResponseDto responseDto = ConvertUtil.convertCreditToCreditScoreResponseDto(creditScore);

        Customer customer = Customer.builder()
                .id(1L)
                .fullName("Test-fullname")
                .phoneNumber("test-phonenumber")
                .identityNumber(identityNumber)
                .creditScore(creditScore)
                .income(income)
                .build();


        CreditApplication value = CreditApplication.builder()
                .creditStatus(CreditStatus.ACCEPTED)
                .creditValue(income.multiply(BigDecimal.valueOf(CreditLimitMultiplier.MULTIPLIER_BY.getValue())))
                .id(1L)
                .salary(income)
                .customer(customer)
                .build();

        CreditApplicationResponseDto expected = ConvertUtil.convertCreditApplicationToCreditApplicationResponseDto(value);

        when(creditScoreService.findCreditScoreByCustomerIdentityNumber(ArgumentMatchers.any(String.class))).thenReturn(responseDto);
        when(customerService.findCustomerByIdentityNumber(ArgumentMatchers.any(String.class))).thenReturn(customer);
        when(creditApplicationRepository.save(ArgumentMatchers.any(CreditApplication.class))).thenReturn(value);

        CreditApplicationResponseDto actual = creditApplicationService.save(identityNumber);

        assertThat(actual.getCreditStatus().getStatus()).isEqualTo(CreditStatus.ACCEPTED.getStatus()).isEqualTo(expected.getCreditStatus().getStatus());
        assertThat(actual.getCreditValue()).isEqualTo(expected.getCreditValue());
        assertThat(actual.getCustomer()).isEqualTo(expected.getCustomer());
        assertThat(actual.getCustomer().getId()).isNotNull();
        assertThat(actual.getId()).isNotNull().isEqualTo(expected.getId());
        assertThat(actual.getCustomer().getIncome()).isNotNull().isEqualTo(expected.getSalary());
        assertThat(actual.getCreditScore()).isEqualTo(expected.getCustomer().getCreditScore().getScore());
    }
}
