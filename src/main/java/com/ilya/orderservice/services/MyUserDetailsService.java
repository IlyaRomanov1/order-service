package com.ilya.orderservice.services;

import com.ilya.orderservice.models.User;
import com.ilya.orderservice.repositories.UsersRepository;
import com.ilya.orderservice.security.MyUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = usersRepository.findByEmail(username);
        if(user.isEmpty())
            throw new UsernameNotFoundException("Пользователь с таким email не найден");
        return new MyUserDetails(user.get());
    }
}
