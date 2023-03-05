package org.myApps.billingService;

import org.myApps.billingService.dto.InvoiceRequestDTO;
import org.myApps.billingService.service.InvoiceService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
@EnableFeignClients /* obligatoire pour activer opernFeign*/
public class BillingApplication {
    public static void main(String[] args) {
        SpringApplication.run(BillingApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(InvoiceService invoiceService){
        return args -> {
            invoiceService.save(new InvoiceRequestDTO(BigDecimal.valueOf(87741874),"c01"));
            invoiceService.save(new InvoiceRequestDTO(BigDecimal.valueOf(548454),"c01"));
            invoiceService.save(new InvoiceRequestDTO(BigDecimal.valueOf(65465454),"c02"));
        };
    }
}
