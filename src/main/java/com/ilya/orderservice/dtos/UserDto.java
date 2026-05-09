package com.ilya.orderservice.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long id;

    @NotBlank(message = "Введите имя")
    private String name;

    @NotBlank(message = "Введите почту")
    @Email(message = "Введите корректную почту")
    private String email;

    @NotBlank(message = "Введите пароль")
    private String password;

    @Column(name = "role")
    private String role;
}
