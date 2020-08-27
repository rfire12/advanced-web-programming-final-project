package com.pucmm.edu.eventsmicroservice.Services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.sendgrid.*;

import com.pucmm.edu.eventsmicroservice.DTO.InvoiceResponse;
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

    public void createInvoice(InvoiceResponse invoice) {
        Product product = productServices.getProduct(invoice.product);
        Invoice newInvoice = new Invoice(invoice.total, invoice.username, new Date(), product);
        invoicesRepository.save(newInvoice);
    }

    public List<InvoiceResponse> getInvoices() {
        List<Invoice> invoices = invoicesRepository.findAll();
        List<InvoiceResponse> response = new ArrayList<>();
        for (Invoice invoice : invoices) {
            InvoiceResponse irep = new InvoiceResponse();
            irep.total = invoice.getTotal();
            irep.product = invoice.getProduct().getName();
            irep.username = invoice.getUsername();
            response.add(irep);
        }
        return response;
    }

    public List<InvoiceResponse> getInvoicesByUsername(String username) {
        List<Invoice> invoices = invoicesRepository.findAllByUsername(username);
        List<InvoiceResponse> response = new ArrayList<>();
        for (Invoice invoice : invoices) {
            InvoiceResponse irep = new InvoiceResponse();
            irep.total = invoice.getTotal();
            irep.product = invoice.getProduct().getName();
            irep.username = invoice.getUsername();
            response.add(irep);
        }
        return response;
    }

    public void sendInvoiceEmail(InvoiceResponse invoiceResponse) throws IOException {
        Email from = new Email("20160290@ce.pucmm.edu.do");
        String subject = "You've bought a product";
        String apiKey = "HERE GOES THE API KEY";
        String url = "http://localhost:8080/users-microservice/api/search?username=" + invoiceResponse.username;

        UserDTO userDto = restTemplate.getForObject(url, UserDTO.class);

        Email to = new Email(userDto.email);

        String body = "Hello " + invoiceResponse.username + "\n\nYour package " + invoiceResponse.product
                + " has been paid successfully.";
        Content content = new Content("text/plain", body);
        Mail mail = new Mail(from, subject, to, content);
        SendGrid sendGrid = new SendGrid(apiKey);
        Request request = new Request();

        // Enviando correo a todos los empleados
        sendEmployeeEmail(from, invoiceResponse, sendGrid);

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            sendGrid.api(request);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void sendEmployeeEmail(Email from, InvoiceResponse invoiceResponse, SendGrid sendGrid) {
        String url = "http://localhost:8080/users-microservice/api/employees";
        UserDTO[] responses = restTemplate.getForObject(url, UserDTO[].class);
        assert responses != null;
        for (UserDTO employee : responses) {
            Email to = new Email(employee.email);
            String subject = "New Job";
            String body = "The user: " + invoiceResponse.username + "has bought the package " + invoiceResponse.product;
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