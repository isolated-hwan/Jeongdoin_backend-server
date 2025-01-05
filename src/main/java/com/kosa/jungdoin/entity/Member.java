package com.kosa.jungdoin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "members")
public class Member extends BaseMember {
    @Column(name = "body_weight", precision = 4, scale = 1)
    private BigDecimal bodyWeight;
    @Column(name = "tall", precision = 4, scale = 1)
    private BigDecimal tall;
    @Column(name = "address")
    private String address;
}
