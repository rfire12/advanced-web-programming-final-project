package com.pucmm.edu.eventsmicroservice.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.pucmm.edu.eventsmicroservice.DTO.InvoiceResponse;
import com.pucmm.edu.eventsmicroservice.DTO.ProductResponse;
import com.pucmm.edu.eventsmicroservice.Entities.Product;
import com.pucmm.edu.eventsmicroservice.Repositories.ProductsRepository;
import com.pucmm.edu.eventsmicroservice.Services.InvoiceServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class MainController {
    @Autowired
    ProductsRepository productsRepository;

    @Autowired
    InvoiceServices invoiceServices;

    @GetMapping("hello")
    public String app(HttpServletRequest request) {
        return "EVENTS, RUNNING ON: " + request.getLocalPort();
    }

    @CrossOrigin
    @PostMapping("invoice")
    public ResponseEntity<String> createInvoice(@RequestBody InvoiceResponse invoice) throws IOException {
        invoiceServices.createInvoice(invoice);
        invoiceServices.sendInvoiceEmail(invoice);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    @CrossOrigin
    @GetMapping("products")
    public ArrayList<ProductResponse> getProducts() {
        List<Product> products = productsRepository.findAll();
        ArrayList<ProductResponse> misProductos = new ArrayList<>();
        for (Product prod : products) {
            ProductResponse pResponse = new ProductResponse();
            pResponse.price = prod.getPrice();
            pResponse.name = prod.getName();
            misProductos.add(pResponse);
        }
        return misProductos;
    }

    @CrossOrigin
    @GetMapping("invoices")
    public List<InvoiceResponse> getInvoices() {
        return invoiceServices.getInvoices();
    }

    @CrossOrigin
    @GetMapping("invoices/client")
    public List<InvoiceResponse> getInvoicesByClient(@RequestParam String username) {
        return invoiceServices.getInvoicesByUsername(username);
    }
}