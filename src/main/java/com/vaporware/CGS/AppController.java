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
import com.vaporware.GeneradorComplejidad.Generador;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/login")
    public String Login() {
        return "login";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> listUsers = userRepository.findAll();
        model.addAttribute("listUsers", listUsers);

        return "users";
    }

   
    @PostMapping("/users")
    public void newUser(HttpServletResponse response) throws IOException {
        String path = "/newuser";
        response.sendRedirect(path);
    }

    @GetMapping("/newuser")
    public String User(Model model, User u) {

        model.addAttribute("user", u);
        return "newuser";
    }

    @PostMapping("/newuser")
    public String processRegister(@ModelAttribute("user") User user) {

        userRepository.save(user);

        return "success";
    }

    @GetMapping("/deleteuser/{username}")
    public String deleteSuccessful(@PathVariable(value = "username") String username) {

        userRepository.delete(userRepository.findByUserName(username));

        return "success";
    }
    @GetMapping("/updateuser/{username}")
    public String updateUser(Model model, @PathVariable(value = "username") String username) {
        User user = userRepository.findByUserName(username);
        model.addAttribute("user", user);

        return "updateuser";
    }

    @PostMapping("/updateuser/{username}")
    public String updateSuccessful(@ModelAttribute("user") User user, @PathVariable(value = "username") String username) {
        User userUp = userRepository.findByUserName(username);
        userUp.setUsername(user.getUsername());
        userUp.setPassword(user.getPassword());
        userUp.setComplex_u(user.getComplex_u());
        userUp.setRol_u(user.getRol_u());

        userRepository.save(userUp);

        return "success";
    }

    @RequestMapping("/generador")
    public String generador(Model model) {
        String complex = null;
        model.addAttribute("complex", complex);
        return "generador";
    }

    @PostMapping("/generador")
    public void upGenerador(Model model, String complex,HttpServletResponse response) throws IOException {

        System.out.println(complex);
        String path = "generador1/?complex=" + complex;
        response.sendRedirect(path);

    }

}
