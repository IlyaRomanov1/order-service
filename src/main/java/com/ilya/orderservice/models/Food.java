package com.ilya.orderservice.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Foods")
@Getter
@Setter
@NoArgsConstructor
public class Food {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "Введите название")
    private String name;

    @Column(name = "price")
    @Positive(message = "Введите положительную сумму")
    private BigDecimal price;

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL)
    private List<Bag> bags;
}
