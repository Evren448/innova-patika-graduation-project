package com.innova.graduationproject.repository;

import com.innova.graduationproject.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findCustomerByIdentityNumber(String identityNumber);
    void deleteCustomerByIdentityNumber(String identityNumber);
    Customer findByPhoneNumber(String phoneNumber);

    Page<Customer> findAll(Pageable pageable);
}
