package com.innova.graduationproject.service;

import com.innova.graduationproject.constant.CreditLimitMultiplier;
import com.innova.graduationproject.constant.CreditStatus;
import com.innova.graduationproject.constant.ExceptionMessage;
import com.innova.graduationproject.dto.creditapplication.CreditApplicationRequestDto;
import com.innova.graduationproject.dto.creditapplication.CreditApplicationResponseDto;
import com.innova.graduationproject.dto.creditscore.CreditScoreResponseDto;
import com.innova.graduationproject.entity.CreditApplication;
import com.innova.graduationproject.entity.Customer;
import com.innova.graduationproject.exception.CreditApplicationNotFoundException;
import com.innova.graduationproject.repository.CreditApplicationRepository;
import com.innova.graduationproject.util.ConvertUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CreditApplicationService {

    private final CreditApplicationRepository creditApplicationRepository;
    private final CustomerService customerService;
    private final CreditScoreService creditScoreService;
    private final SMSService smsService;

    public CreditApplicationResponseDto save(String identityNumber) {

        CreditScoreResponseDto creditScoreByCustomerIdentityNumber = this.creditScoreService.findCreditScoreByCustomerIdentityNumber(identityNumber);

        Customer customer = this.customerService.findCustomerByIdentityNumber(identityNumber);

        CreditApplicationRequestDto application = new CreditApplicationRequestDto();


        if (customer.getCreditScore().getScore() < 500) {

            application = this.calculateApplication(CreditStatus.REJECTED, customer, BigDecimal.valueOf(0));

        } else if (customer.getCreditScore().getScore() >= 500 && customer.getCreditScore().getScore() < 1000) {
            if (customer.getIncome().compareTo(BigDecimal.valueOf(5000)) < 0) {

                application = this.calculateApplication(CreditStatus.ACCEPTED, customer, BigDecimal.valueOf(10000));

            } else if (customer.getIncome().compareTo(BigDecimal.valueOf(5000)) >= 0) {

                application = this.calculateApplication(CreditStatus.ACCEPTED, customer, BigDecimal.valueOf(20000));

            }
        } else if (customer.getCreditScore().getScore() >= 1000) {

            application =
                    this.calculateApplication(
                            CreditStatus.ACCEPTED,
                            customer,
                            creditScoreByCustomerIdentityNumber.getCustomer().getIncome().multiply(BigDecimal.valueOf(CreditLimitMultiplier.MULTIPLIER_BY.getMultiplierValue())));

        }

        CreditApplication creditApplication = ConvertUtil.convertCreditApplicationRequestDtoToCreditApplication(application);

        CreditApplication savedCredit = this.creditApplicationRepository.save(creditApplication);
        this.smsService.SendSms(savedCredit.getCustomer().getPhoneNumber(), savedCredit.getCreatedDate(), savedCredit.getCustomer().getFullName(), savedCredit.getCreditValue(), savedCredit.getCreditStatus().getStatus());

        return ConvertUtil.convertCreditApplicationToCreditApplicationResponseDto(savedCredit);


    }

    public List<CreditApplicationResponseDto> findCreditApplicationByIdentityNumber(String identityNumber) {
        List<CreditApplication> creditApplicationByCustomerIdentityNumber = this.creditApplicationRepository.findCreditApplicationByCustomerIdentityNumber(identityNumber);

        if(creditApplicationByCustomerIdentityNumber.isEmpty()){
            throw new CreditApplicationNotFoundException(ExceptionMessage.CREDIT_APPLICATION_NOT_FOUND.getMessage());
        }

        List<CreditApplicationResponseDto> responseList = new ArrayList<>();

        for (CreditApplication application : creditApplicationByCustomerIdentityNumber) {
            responseList.add(ConvertUtil.convertCreditApplicationToCreditApplicationResponseDto(application));
        }
        return responseList;
    }

    private CreditApplicationRequestDto calculateApplication(CreditStatus status, Customer customer, BigDecimal creditValue){
        return CreditApplicationRequestDto.builder()
                .creditStatus(status)
                .creditValue(creditValue)
                .customer(customer)
                .build();
    }

}
