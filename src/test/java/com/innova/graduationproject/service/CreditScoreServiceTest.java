package com.innova.graduationproject.service;

import com.innova.graduationproject.dto.creditscore.CreditScoreResponseDto;
import com.innova.graduationproject.dto.customer.CustomerRequestDto;
import com.innova.graduationproject.dto.customer.CustomerResponseDto;
import com.innova.graduationproject.entity.CreditApplication;
import com.innova.graduationproject.entity.CreditScore;
import com.innova.graduationproject.entity.Customer;
import com.innova.graduationproject.exception.CreditScoreNotFoundException;
import com.innova.graduationproject.exception.EntityNotFoundException;
import com.innova.graduationproject.repository.CreditScoreRepository;
import com.innova.graduationproject.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CreditScoreServiceTest {
    @InjectMocks
    private CreditScoreService creditScoreService;

    @Mock
    private CreditScoreRepository creditScoreRepository;

    @Test
    public void given_validIdentityNumber_when_findCreditScoreByCustomerIdentityNumber_then_ReturnCreditApplicationResponseDto(){
        String identityNumber = "12345678910";

        CreditScore creditScore = CreditScore.builder().id(1L).score(500).build();

        when(creditScoreRepository.findCreditScoreByCustomerIdentityNumber(identityNumber)).thenReturn(Optional.of(creditScore));

        CreditScoreResponseDto actual = creditScoreService.findCreditScoreByCustomerIdentityNumber(identityNumber);

        assertThat(actual).isNotNull();
        assertThat(actual.getScore()).isEqualTo(creditScore.getScore());
        assertThat(actual.getId()).isNotNull().isEqualTo(creditScore.getId());


    }

    @Test
    public void given_invalidIdentityNumber_when_findCreditScoreByCustomerIdentityNumber_then_ReturnCreditApplicationResponseDto(){
        String identityNumber = "12345678910";
        when(creditScoreRepository.findCreditScoreByCustomerIdentityNumber(identityNumber)).thenReturn(Optional.ofNullable(null));
        Assertions.assertThrows(CreditScoreNotFoundException.class, () -> creditScoreService.findCreditScoreByCustomerIdentityNumber(identityNumber));
        verify(creditScoreRepository, times(1)).findCreditScoreByCustomerIdentityNumber(identityNumber);
    }
}
