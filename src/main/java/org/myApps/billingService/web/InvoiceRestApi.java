package org.myApps.billingService.web;

import org.myApps.billingService.dto.InvoiceRequestDTO;
import org.myApps.billingService.dto.InvoiceResponseDTO;
import org.myApps.billingService.service.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api")
public class InvoiceRestApi {
    private InvoiceService invoiceService;

    public InvoiceRestApi(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping(path = "/invoices/{id}")
    public InvoiceResponseDTO getInvoice(@PathVariable(name = "id") String invoiceId) {
        return invoiceService.getInvoiceById(invoiceId);
    }

    @GetMapping(path = "/invoices/perCustomer/{customerId}")
    public List<InvoiceResponseDTO> getAllInvoicesForCustomer(@PathVariable(name = "customerId") String customerId) {
        return invoiceService.getAllInvoicesForCustomer(customerId);
    }

    @PostMapping(path = "/invoices")
    public InvoiceResponseDTO saveInvoice(@RequestBody InvoiceRequestDTO invoiceRequestDTO) {
        return invoiceService.save(invoiceRequestDTO);
    }

    @GetMapping(path = "/invoices")
    public List<InvoiceResponseDTO> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
