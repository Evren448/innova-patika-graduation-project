package com.innova.graduationproject.repository;

import com.innova.graduationproject.entity.CreditScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditScoreRepository extends JpaRepository<CreditScore, Long> {
    CreditScore findCreditScoreById(Long id);
}
