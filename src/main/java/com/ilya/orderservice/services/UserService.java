package com.ilya.orderservice.services;

import com.ilya.orderservice.dtos.UserDto;
import com.ilya.orderservice.models.User;
import com.ilya.orderservice.repositories.UsersRepository;
import com.ilya.orderservice.security.MyUserDetails;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService{
    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public String finOne(String email){
        Optional<User> user = usersRepository.findByEmail(email);
        return user.map(User::getEmail).orElse(null);
    }

    public User findOne(Long id){
        Optional<User> user = usersRepository.findById(id);
        //TODO обработка исключения
        return user.get();
    }

    @Transactional
    public void register(UserDto userDto){
        userDto.setRole("ROLE_USER");
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        usersRepository.save(modelMapper.map(userDto, User.class));
    }

}
