package com.ilya.orderservice.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "order_vw")
@Getter
@Setter
@NoArgsConstructor
public class OrderView {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "total_price")
    private BigDecimal totalPrice;
}
