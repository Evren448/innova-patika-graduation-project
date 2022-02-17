package com.innova.graduationproject.service;

import com.innova.graduationproject.constant.CreditStatus;
import com.innova.graduationproject.dto.customer.CustomerRequestDto;
import com.innova.graduationproject.dto.customer.CustomerResponseDto;
import com.innova.graduationproject.entity.CreditApplication;
import com.innova.graduationproject.entity.Customer;
import com.innova.graduationproject.exception.CreditApplicationNotFoundException;
import com.innova.graduationproject.exception.EntityNotFoundException;
import com.innova.graduationproject.repository.CreditApplicationRepository;
import com.innova.graduationproject.repository.CreditScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CreditApplicationService {

    private final CreditApplicationRepository creditApplicationRepository;
    private final CustomerService customerService;
    private final CreditScoreService creditScoreService;

    public CreditApplication save(String identityNumber) {

        Customer customer = this.customerService.findCustomerByIdentityNumber(identityNumber);
        CreditApplication application = new CreditApplication();

        // TODO CreditScoreService hic kullanilmamis. Duzenle

        if (customer.getCreditScore().getScore() < 500) {
            application = CreditApplication.builder()
                    .creditStatus(CreditStatus.REJECTED)
                    .customer(customer)
                    .creditValue(BigDecimal.valueOf(0))
                    .build();
        } else if (customer.getCreditScore().getScore() >= 500 && customer.getCreditScore().getScore() < 1000) {
            if (customer.getIncome().compareTo(BigDecimal.valueOf(5000)) < 0) {
                application = CreditApplication.builder()
                        .creditStatus(CreditStatus.ACCEPTED)
                        .customer(customer)
                        .creditValue(BigDecimal.valueOf(10000))
                        .build();
            } else if (customer.getIncome().compareTo(BigDecimal.valueOf(5000)) >= 0) {
                application = CreditApplication.builder()
                        .creditStatus(CreditStatus.ACCEPTED)
                        .customer(customer)
                        .creditValue(BigDecimal.valueOf(20000))
                        .build();
            }
        } else if (customer.getCreditScore().getScore() >= 1000) {
            application = CreditApplication.builder()
                    .creditStatus(CreditStatus.ACCEPTED)
                    .customer(customer)
                    .creditValue(customer.getIncome().multiply(BigDecimal.valueOf(4)))
                    .build();
        }
        // TODO bir duzen bir dusunce.

        CreditApplication save = this.creditApplicationRepository.save(application);
        return save;


    }

    public List<CreditApplication> findCreditApplicationByIdentityNumber(String identityNumber) {
        List<CreditApplication> creditApplicationByCustomerIdentityNumber = this.creditApplicationRepository.findCreditApplicationByCustomerIdentityNumber(identityNumber);

        if(creditApplicationByCustomerIdentityNumber.isEmpty()){
            throw new CreditApplicationNotFoundException(identityNumber +  " Credit Application not found!");
        }

        return creditApplicationByCustomerIdentityNumber;
    }
}
