package com.innova.graduationproject.validator;

import com.innova.graduationproject.entity.Customer;
import com.innova.graduationproject.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UniquePhoneNumberValidator implements ConstraintValidator<UniquePhoneNumber, String> {

    private final CustomerRepository customerRepository;

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        Customer customer = customerRepository.findByPhoneNumber(phoneNumber);
        if (customer != null) {
            return false;
        }
        return true;
    }


}