package com.pucmm.edu.usermicro.Entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class MyRole implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String role;

    public MyRole() {
    }

    public MyRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String name) {
        this.role = name;
    }
}
