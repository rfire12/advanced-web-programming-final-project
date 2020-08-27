package com.pucmm.edu.eventsmicroservice.Services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sendgrid.*;

import com.pucmm.edu.eventsmicroservice.DTO.InvoiceDTO;
import com.pucmm.edu.eventsmicroservice.DTO.UserDTO;
import com.pucmm.edu.eventsmicroservice.Entities.Invoice;
import com.pucmm.edu.eventsmicroservice.Entities.Product;
import com.pucmm.edu.eventsmicroservice.Repositories.InvoicesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InvoiceServices {
    @Autowired
    InvoicesRepository invoicesRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ProductServices productServices;

    public void createInvoice(InvoiceDTO request) {
        Set<Product> products = new HashSet<>();

        for (Long id : request.products) {
            products.add(productServices.getProduct(id));
        }
        Invoice newInvoice = new Invoice(request.total, request.username, new Date(), products);
        invoicesRepository.save(newInvoice);
    }

    public List<InvoiceDTO> getInvoices() {
        List<Invoice> invoices = invoicesRepository.findAll();
        List<InvoiceDTO> response = new ArrayList<>();
        for (Invoice invoice : invoices) {
            InvoiceDTO irep = new InvoiceDTO();
            irep.total = invoice.getTotal();

            Set<Product> products = invoice.getProducts();
            List<String> productNames = new ArrayList<>();

            for (Product product : products) {
                productNames.add(product.getName());
            }

            irep.id = invoice.getId();
            irep.productsNames = productNames;
            irep.username = invoice.getUsername();
            irep.date = invoice.getDate();
            response.add(irep);
        }
        return response;
    }

    public List<InvoiceDTO> getInvoicesByUsername(String username) {
        List<Invoice> invoices = invoicesRepository.findAllByUsername(username);
        List<InvoiceDTO> response = new ArrayList<>();
        for (Invoice invoice : invoices) {
            InvoiceDTO irep = new InvoiceDTO();
            irep.total = invoice.getTotal();

            Set<Product> products = invoice.getProducts();
            List<String> productNames = new ArrayList<>();

            for (Product product : products) {
                productNames.add(product.getName());
            }

            irep.id = invoice.getId();
            irep.productsNames = productNames;
            irep.date = invoice.getDate();
            irep.username = invoice.getUsername();
            response.add(irep);
        }
        return response;
    }

    public void sendInvoiceEmail(InvoiceDTO invoice) throws IOException {
        Email from = new Email("20160290@ce.pucmm.edu.do");
        String subject = "You've bought a product";
        String apiKey = "HERE GOES THE API KEY";
        String url = "http://localhost:8080/users-microservice/api/search?username=" + invoice.username;

        UserDTO userDto = restTemplate.getForObject(url, UserDTO.class);

        Email to = new Email(userDto.email);

        String body = "Hello " + invoice.username + "\n\nYour packages: " + String.join(", ", invoice.productsNames)
                + " has been paid successfully.";
        Content content = new Content("text/plain", body);
        Mail mail = new Mail(from, subject, to, content);
        SendGrid sendGrid = new SendGrid(apiKey);
        Request request = new Request();

        sendEmployeeEmail(from, invoice, sendGrid);

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            sendGrid.api(request);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void sendEmployeeEmail(Email from, InvoiceDTO invoice, SendGrid sendGrid) {
        String url = "http://localhost:8080/users-microservice/api/employees";
        UserDTO[] employees = restTemplate.getForObject(url, UserDTO[].class);

        for (UserDTO employee : employees) {
            Email to = new Email(employee.email);
            String subject = "New Job";
            String body = "The user: " + invoice.username + "has bought the following packages "
                    + String.join(", ", invoice.productsNames);
            Content content = new Content("text/plain", body);
            Mail mail = new Mail(from, subject, to, content);
            Request request = new Request();
            try {
                request.setMethod(Method.POST);
                request.setEndpoint("mail/send");
                request.setBody(mail.build());
                sendGrid.api(request);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }
}