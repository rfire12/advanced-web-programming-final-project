package notificationmicro.Services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import notificationmicro.Entities.Invoice;
import notificationmicro.Repositories.InvoiceRepository;

@Service
public class InvoiceService {
    @Autowired
    InvoiceRepository invoiceRepository;

    public List<Invoice> getInvoicesByUsername(String username) {
        return invoiceRepository.findAllByUsername(username);
    }

    @Transactional
    public void createInvoice(Invoice invoice) {
        invoiceRepository.save(invoice);
    }
}