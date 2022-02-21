package com.innova.graduationproject.service;

import com.innova.graduationproject.constant.ExceptionMessage;
import com.innova.graduationproject.dto.creditscore.CreditScoreResponseDto;
import com.innova.graduationproject.entity.CreditScore;
import com.innova.graduationproject.exception.CreditScoreNotFoundException;
import com.innova.graduationproject.repository.CreditScoreRepository;
import com.innova.graduationproject.util.ConvertUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditScoreService {

    private final CreditScoreRepository creditScoreRepository;

    public CreditScoreResponseDto findCreditScoreByCustomerIdentityNumber(String identityNumber){

        CreditScore creditScore = this.creditScoreRepository.findCreditScoreByCustomerIdentityNumber(identityNumber)
                .orElseThrow(() -> new CreditScoreNotFoundException(ExceptionMessage.CREDIT_SCORE_NOT_FOUND.getMessage()));

        return ConvertUtil.convertCreditToCreditScoreResponseDto(creditScore);
    }
}
