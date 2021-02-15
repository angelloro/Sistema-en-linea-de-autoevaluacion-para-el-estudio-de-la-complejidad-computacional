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
import java.security.Principal;
import java.util.List;
import java.util.Random;
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
    Generador g;

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
        int metodos = 0, var = 0, varA = 0, bucles = 0;
        model.addAttribute("complex", complex);
        model.addAttribute("metodos", metodos);
        model.addAttribute("var", var);
        model.addAttribute("varA", varA);
        model.addAttribute("bucles", bucles);
        return "generador";
    }

    @PostMapping("/generador")
    public void upGenerador(String complex, HttpServletResponse response, int metodos, int var, int varA, int bucles) throws IOException {

        String path = "generador1/" + complex;
        path += "/" + metodos + "/" + var + "/" + varA + "/" + bucles;
        response.sendRedirect(path);

    }

    @GetMapping("/generador1/{complex}/{metodos}/{var}/{varA}/{bucles}")
    public String generador1(Model model,@PathVariable("complex") String complex, @PathVariable("metodos") String metodos, @PathVariable("var") String var,
                             @PathVariable("varA") String varA, @PathVariable("bucles") String bucles) {
        
        g = new Generador(Integer.parseInt(metodos), Integer.parseInt(var), Integer.parseInt(varA), Integer.parseInt(bucles), complex);
        model.addAttribute("generador", g);
        return "generador1";
    }

    @PostMapping("/generador1")
    public void upGenerador1( String complex) {


    }
    
    
    @PostMapping("/generadorAlumno")
    public void generadorAlumno(HttpServletResponse response, Principal principal) throws IOException {

        User user = userRepository.findByUserName(principal.getName());
        String path = "generador1/" + user.getComplex_u();
        Random r = new Random();
        path += "/" + r.nextInt(5) + "/" + r.nextInt(20) + "/" + r.nextInt(20) + "/" + r.nextInt(5);
        response.sendRedirect(path);

    }

}
