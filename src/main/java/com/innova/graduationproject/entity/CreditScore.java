package com.innova.graduationproject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "credit_scores")
public class CreditScore extends Audit implements Serializable {

    private static final long serialVersionUID = 1L;

    // TODO User id ne abi

    @Id
    @Column(name = "user_id")
    private Long id;

    @Column(name = "score")
    private Integer score;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "user_id")
    private Customer customer;

}
