package com.buylify.buylifyapp.security;


import com.buylify.buylifyapp.authentication.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class SecurityUser implements UserDetails {

    private User user;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var userType = user.getUserType();
        List<GrantedAuthority> userTypes = new ArrayList<>();
        userTypes.add(new SimpleGrantedAuthority(userType.getName()));
        return userTypes;
    }

    public Long getId(){
        return user.getId();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public String getName(){
        return user.getName();
    }

    public String getSurname(){
        return user.getSurname();
    }

    public String getPhoneNumber(){
        return user.getPhoneNumber();
    }

    public String getEmail(){
        return user.getEmail();
    }

    public LocalDateTime getRegistrationDate(){
        return user.getRegistrationDate();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
