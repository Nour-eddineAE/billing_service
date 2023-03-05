package org.myApps.billingService.service;

import org.myApps.billingService.dto.InvoiceRequestDTO;
import org.myApps.billingService.dto.InvoiceResponseDTO;
import org.myApps.billingService.serviceExceptions.CustomerNotFoundException;

import java.util.List;

public interface InvoiceService
{
    InvoiceResponseDTO save(InvoiceRequestDTO invoiceRequestDTO);
    InvoiceResponseDTO getInvoiceById(String invoiceId);
    List<InvoiceResponseDTO> getAllInvoicesForCustomer(String customerId);
    List<InvoiceResponseDTO> getAllInvoices();
}
