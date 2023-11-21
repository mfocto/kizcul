package com.kizcul.base.security;

import com.kizcul.base.security.jwt.CustomUser;
import com.kizcul.entity.user.UserEntity;
import com.kizcul.entity.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        UserEntity emp = repository.findByUserId(userName.trim());

        return emp == null ? null : new CustomUser(emp);
    }

}
