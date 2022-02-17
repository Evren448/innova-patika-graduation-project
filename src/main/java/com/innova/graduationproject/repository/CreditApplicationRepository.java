package com.innova.graduationproject.repository;

import com.innova.graduationproject.entity.CreditApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditApplicationRepository extends JpaRepository<CreditApplication, Long> {
    List<CreditApplication> findCreditApplicationByCustomerIdentityNumber(String identityNumber);
}
