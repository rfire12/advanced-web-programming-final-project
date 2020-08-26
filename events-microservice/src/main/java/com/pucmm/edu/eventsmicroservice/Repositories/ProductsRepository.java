package com.pucmm.edu.eventsmicroservice.Repositories;

import com.pucmm.edu.eventsmicroservice.Entities.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);
    Product findByName(String name);
}