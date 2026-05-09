package com.ilya.orderservice.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "Введите имя")
    private String name;

    @Column(name = "email")
    @NotBlank(message = "Введите почту")
    @Email(message = "Введите корректную почту")
    private String email;

    @Column(name = "password")
    @NotBlank(message = "Введите пароль")
    private String password;

    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Bag> bags;
}
