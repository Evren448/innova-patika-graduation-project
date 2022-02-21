package com.innova.graduationproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
@Slf4j
public class SMSService {

    public void SendSms(String phoneNumber, Date sendDate, String fullName, BigDecimal value, String status){
        log.info("Dear " + fullName + "! Thanks for application. Your application has been " +
                status + " and your credit value is " + value +"$ at " + sendDate
        );
    }

}
