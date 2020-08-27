package com.pucmm.edu.eventsmicroservice.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pucmm.edu.eventsmicroservice.Entities.Product;
import com.pucmm.edu.eventsmicroservice.Repositories.InvoicesRepository;
import com.pucmm.edu.eventsmicroservice.Repositories.ProductsRepository;

@Service
public class ProductServices {
    @Autowired
    ProductsRepository productsRepository;

    @Autowired
    InvoicesRepository invoicesRepository;

    public void initialProducts() {
        if (productsRepository.findAll().size() == 0) {
            Product p1 = new Product();
            Product p2 = new Product();
            Product p3 = new Product();
            Product p4 = new Product();

            p1.setPrice(1000);
            p1.setName("Pre-Boda");

            p2.setPrice(5000);
            p2.setName("Boda");

            p3.setPrice(3000);
            p3.setName("Cumpleaños");

            p4.setPrice(4000);
            p4.setName("Vídeo de evento");

            productsRepository.save(p1);
            productsRepository.save(p2);
            productsRepository.save(p3);
            productsRepository.save(p4);
        }
    }

    public Product getProduct(Long id) {
        Product product = productsRepository.findById(id).get();
        return product;
    }
}