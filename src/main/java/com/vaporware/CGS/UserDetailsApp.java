package com.vaporware.CGS;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Angel
 */
import java.util.ArrayList;
import java.util.Collection;

 
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
 
public class UserDetailsApp implements UserDetails {
 
    private User user;
     
    public UserDetailsApp(User user) {
        this.user = user;
    }
 
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> rolApp = new ArrayList<>();
        rolApp.add(new SimpleGrantedAuthority(getRol()));

        return rolApp;
    }
    
 
    @Override
    public String getPassword() {
        return user.getPassword();
    }
 
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public String getRol() {
        return user.getRol_u();
    }

    public String getComplex() {
        return user.getComplex_u();
    }
 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    @Override
    public boolean isEnabled() {
        return true;
    }
     
    public String getUser() {
        return user.getUsername() + " " + user.getRol_u();
    }
 
}