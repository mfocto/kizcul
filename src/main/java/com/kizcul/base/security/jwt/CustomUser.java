package com.kizcul.base.security.jwt;

import com.kizcul.entity.user.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serial;
import java.util.Collection;
import java.util.Collections;

public class CustomUser extends User{
    @Serial
    private static final long serialVersionUID = 1L;
    private UserEntity user;

    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public CustomUser(UserEntity user) {
        super(user.getUserId(), user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(user.getUserAuth())));
        this.user = user;
    }

    public CustomUser(UserEntity user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUserId(), user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(user.getUserAuth())));
        this.user = user;
    }

    public String getUserIdx() {
        return user.getUserIdx();
    }

    public String getUserId() {
        return user.getUserId();
    }

    public String getRole() {
        return user.getRole();
    }
}
