package com.innova.graduationproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.innova.graduationproject.constant.CreditStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "credit_application")
public class CreditApplication extends Audit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "credit_value", nullable = false)
    private BigDecimal creditValue;

    @Column(name = "credit_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CreditStatus creditStatus;

    @Column(name = "salary", nullable = false)
    private BigDecimal salary;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;
}
