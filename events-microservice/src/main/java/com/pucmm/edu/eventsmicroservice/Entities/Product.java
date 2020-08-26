package com.pucmm.edu.eventsmicroservice.Entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private float price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Invoice> invoices;

    public Product(String name, float price) {
        this.name = name;
        this.price = price;
    }
}