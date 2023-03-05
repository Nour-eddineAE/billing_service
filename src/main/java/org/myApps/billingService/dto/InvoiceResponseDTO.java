package org.myApps.billingService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.myApps.billingService.entities.Customer;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceResponseDTO {
    private  String invoiceId;
    private Date date;
    private BigDecimal amount;
    private Customer customer;
}
