/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaporware.CGS;

/**
 *
 * @author Angel
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserDetailsAppService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new UserDetailsApp(user);
    }

    public void delete(String username) {
        userRepository.deleteUser(username);
    }

    public void update(String username, User user) {
        User userUp = userRepository.findByUserName(username);
        userUp.setUsername(user.getUsername());
        userUp.setPassword(user.getPassword());
        userUp.setComplex_u(user.getComplex_u());
        userUp.setRol_u(user.getRol_u());

        userRepository.save(userUp);

    }

}
