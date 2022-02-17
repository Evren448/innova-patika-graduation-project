package com.innova.graduationproject.service;

import com.innova.graduationproject.entity.CreditScore;
import com.innova.graduationproject.repository.CreditScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditScoreService {

    private final CreditScoreRepository creditScoreRepository;

    public CreditScore findCreditScoreByCustomerId(Long id){
        return this.creditScoreRepository.findCreditScoreById(id);
        // TODO Exception.
    }

}
