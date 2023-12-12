package com.pehlivan.security.security.adapters;

import com.pehlivan.security.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class SecurityUser implements UserDetails {
    private final User user;
    private final List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    public SecurityUser(User user){
        this.user = user;
        initAuthorities();
    }
    @Override
    public String getPassword() {
        return user.getPassword();
    }
    @Override
    public String getUsername() {
        return user.getUserName();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;

    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !user.isBanned();
    }
    @Override
    public boolean isAccountNonLocked() {
        return !user.isLocked();
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    private void initAuthorities(){
        if (user == null) return;
        if (user.getRole() == null) return;
        if (user.getRole().getPermissions() == null) return;
        user.getRole()
                .getPermissions()
                .forEach(
                        authority -> authorities.add(
                                new SimpleGrantedAuthority(authority.getCode())
                        )
                );
    }
}
