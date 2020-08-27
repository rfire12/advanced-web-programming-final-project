package com.pucmm.edu.eventsmicroservice.DTO;

import java.util.Date;
import java.util.List;

public class InvoiceDTO {
    public Long id;
    public float total;
    public Date date;
    public String username;
    public List<Long> products;
    public List<String> productsNames;
}