package com.pucmm.edu.eventsmicroservice.DTO;

import java.util.List;

public class InvoiceDTO {
    public Long id;
    public float total;
    public String username;
    public List<Long> products;
    public List<String> productsNames;
}