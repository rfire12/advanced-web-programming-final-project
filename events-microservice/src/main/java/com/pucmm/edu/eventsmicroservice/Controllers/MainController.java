package com.pucmm.edu.eventsmicroservice.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.pucmm.edu.eventsmicroservice.DTO.InvoiceDTO;
import com.pucmm.edu.eventsmicroservice.DTO.ProductDTO;
import com.pucmm.edu.eventsmicroservice.Entities.Product;
import com.pucmm.edu.eventsmicroservice.Repositories.ProductsRepository;
import com.pucmm.edu.eventsmicroservice.Services.InvoiceServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<String> createInvoice(@RequestBody InvoiceDTO invoice) throws IOException {
        invoiceServices.createInvoice(invoice);
        invoiceServices.sendInvoiceEmail(invoice);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    @CrossOrigin
    @GetMapping("products")
    public ArrayList<ProductDTO> getProducts() {
        List<Product> products = productsRepository.findAll();
        ArrayList<ProductDTO> misProductos = new ArrayList<>();
        for (Product prod : products) {
            ProductDTO pResponse = new ProductDTO();
            pResponse.price = prod.getPrice();
            pResponse.name = prod.getName();
            misProductos.add(pResponse);
        }
        return misProductos;
    }

    @CrossOrigin
    @GetMapping("invoices")
    public List<InvoiceDTO> getInvoices() {
        return invoiceServices.getInvoices();
    }

    @CrossOrigin
    @GetMapping("invoices/client")
    public List<InvoiceDTO> getInvoicesByClient(@RequestParam String username) {
        return invoiceServices.getInvoicesByUsername(username);
    }
}