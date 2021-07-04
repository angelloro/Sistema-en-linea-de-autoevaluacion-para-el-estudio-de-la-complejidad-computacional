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
import com.vaporware.GeneradorComplejidad.Generador;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Random;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppController {

    Generador generador;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserDetailsAppService userDetailsService;
    
     @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/")
    public String home() {
        return "generador";
    }

    @GetMapping("/info")
    public String hello() {
        return "info";
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
    public void processRegister(@ModelAttribute("user") User user, HttpServletResponse response) throws IOException {
        //userRepository.save(user);
        userDetailsService.registerUser(user);
        String path = "/users";
        response.sendRedirect(path);
    }

    @GetMapping("/deleteuser/{username}")
    public void deleteSuccessful(@PathVariable(value = "username") String username, HttpServletResponse response) throws IOException {
        userRepository.delete(userRepository.findByUserName(username));
        String path = "/users";
        response.sendRedirect(path);
    }

    @GetMapping("/updateuser/{username}")
    public String updateUser(Model model, @PathVariable(value = "username") String username) {
        User user = userRepository.findByUserName(username);
        model.addAttribute("user", user);
        return "updateuser";
    }

    @PostMapping("/updateuser/{username}")
    public void updateSuccessful(@ModelAttribute("user") User user, @PathVariable(value = "username") String username, HttpServletResponse response) throws IOException {
        User userUp = userRepository.findByUserName(username);
        userUp.setUsername(user.getUsername());
        
        if(!userUp.getPassword().equals(user.getPassword()) && !user.getPassword().equals("")){
            userUp.setPassword(passwordEncoder.encode(user.getPassword()));
        }  
        userUp.setComplex_u(user.getComplex_u());
        userUp.setRol_u(user.getRol_u());
        //userDetailsService.registerUser(userUp);
        userRepository.save(userUp);
        String path = "/users";
        response.sendRedirect(path);
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
    public void upGenerador(String complex, HttpServletResponse response, int var, int varA, int bucles, int buclesMin) throws IOException {
        String path = "generador1/" + complex;
        path += "/" + var + "/" + varA + "/" + bucles + "/" + buclesMin;
        response.sendRedirect(path);
    }

    @GetMapping("/generador1/{complex}/{var}/{varA}/{bucles}/{buclesMin}")
    public String generador1(Model model, @PathVariable("complex") String complex, @PathVariable("var") String var,
            @PathVariable("varA") String varA, @PathVariable("bucles") String bucles, @PathVariable("buclesMin") String buclesMin) {
        generador = new Generador(1, Integer.parseInt(var), Integer.parseInt(varA), Integer.parseInt(bucles), Integer.parseInt(buclesMin), complex);
        model.addAttribute("generador", generador.getMethodCollection().get(0));
        return "generador1";
    }

    @PostMapping("/generador1")
    public void upGenerador1(String complex) {

    }

    @GetMapping("/generadorAlumno")
    public void generadorAlumno(HttpServletResponse response, Principal principal) throws IOException {
        User user = userRepository.findByUserName(principal.getName());
        String path = "generador1/" + user.getComplex_u();
        Random r = new Random();
        int variables = r.nextInt(5) + 1;
        path += "/" + String.valueOf(r.nextInt(20) + 1) + "/" + String.valueOf(r.nextInt(20) + 1) + "/" + String.valueOf(variables) + "/" + String.valueOf(r.nextInt(variables) + 1);
        if (user.getComplex_u().equals("ninguna")) {
            response.sendRedirect("ninguna/");
        } else {
            response.sendRedirect(path);
        }
    }

    @GetMapping("/ninguna")
    public String ningunOrden(Model model, Principal principal) {
        String nombre = principal.getName(); //get logged in username
        model.addAttribute("nombre", nombre);
        return "ninguna";
    }

}
