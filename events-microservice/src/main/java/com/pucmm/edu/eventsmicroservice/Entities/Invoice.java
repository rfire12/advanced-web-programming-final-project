package com.pucmm.edu.eventsmicroservice.Entities;

import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private float total;
    private String username;
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    public Invoice(float total, String username, Date date, Product product) {
        this.total = total;
        this.username = username;
        this.date = date;
        this.product = product;
    }
}