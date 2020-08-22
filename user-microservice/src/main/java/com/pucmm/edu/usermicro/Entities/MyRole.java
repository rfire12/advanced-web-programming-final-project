package com.pucmm.edu.usermicro.Entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class MyRole implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true)
    private String name;

    public MyRole() {
    }

    public MyRole(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
