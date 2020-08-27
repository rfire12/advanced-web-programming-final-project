package com.pucmm.edu.eventsmicroservice.Repositories;

import java.util.List;

import com.pucmm.edu.eventsmicroservice.Entities.Invoice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoicesRepository extends JpaRepository<Invoice, Long> {
    public List<Invoice> findAllByUsername(String username);
}