package com.vaporware.WebComplejidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(unique = true, length = 30)
    private String username;
    @Column(length = 128)
    private String password;
    @Column(name = "enabled")
    private boolean enabled = true;
    @Column(unique = false, length = 128)
    private String rol_u;
    @Column(unique = false, length = 128)
    private String complex_u;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getPassword() {
        return password;
    }

    public String getComplex_u() {
        return complex_u;
    }

    public void setPassword(String contraseña) {
        this.password = contraseña;
    }

    public void setRol_u(String rol) {
        this.rol_u = rol;
    }

    public void setComplex_u(String complex) {
        this.complex_u = complex;
    }

    public String getRol_u() {
        return rol_u;
    }

}
