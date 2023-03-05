package org.myApps.billingService.mappers;

import org.mapstruct.Mapper;
import org.myApps.billingService.dto.InvoiceRequestDTO;
import org.myApps.billingService.dto.InvoiceResponseDTO;
import org.myApps.billingService.entities.Invoice;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {
    Invoice invoiceRequestDTOToInvoice(InvoiceRequestDTO invoiceRequestDTO);
    InvoiceResponseDTO invoiceToInvoiceReponseDTO(Invoice invoice);
}
