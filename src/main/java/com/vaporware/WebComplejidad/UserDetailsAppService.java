/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaporware.WebComplejidad;

/**
 *
 * @author Angel
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserDetailsAppService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        //Aqui se codifica o decodifica la contraseña para poder usarla en el sistema
        //user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new UserDetailsApp(user);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
       
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
