package org.myApps.billingService.service;

import org.myApps.billingService.dao.InvoiceRepository;
import org.myApps.billingService.dto.InvoiceRequestDTO;
import org.myApps.billingService.dto.InvoiceResponseDTO;
import org.myApps.billingService.entities.Customer;
import org.myApps.billingService.entities.Invoice;
import org.myApps.billingService.mappers.InvoiceMapper;
import org.myApps.billingService.restClient.CustomerRestClient;
import org.myApps.billingService.serviceExceptions.CustomerNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@Service
public class InvoiceServiceImpl implements InvoiceService {
    private InvoiceRepository invoiceRepository;
    private InvoiceMapper invoiceMapper;
    private CustomerRestClient customerRestClient;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper, CustomerRestClient customerRestClient) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
        this.customerRestClient = customerRestClient;
    }

    @Override
    public InvoiceResponseDTO save(InvoiceRequestDTO invoiceRequestDTO) {
        Customer customer;
        try {
            customer = customerRestClient
                    .getCustomer(invoiceRequestDTO.getCustomerId());
        }catch (Exception e){
            throw new CustomerNotFoundException("no customer with such id");
        }
        Invoice invoice = invoiceMapper.invoiceRequestDTOToInvoice(invoiceRequestDTO);
        invoice.setInvoiceId(UUID.randomUUID().toString());
        invoice.setDate(new Date());

        //TODO verification de l'intergrité réferentielle(verifier que Customer existe avant d'ajouter Invoice)
        Invoice savedInvoice = invoiceRepository.save(invoice);
        savedInvoice.setCustomer(customer);
        InvoiceResponseDTO invoiceResponseDTO = invoiceMapper.invoiceToInvoiceReponseDTO(savedInvoice);
        return invoiceResponseDTO;
    }

    @Override
    public InvoiceResponseDTO getInvoiceById(String invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId).get();
            if(invoice == null){
                throw new RuntimeException("no invoice with such id");
            }
            Customer customer = customerRestClient
                    .getCustomer(invoice.getCustomerId());
            /*c'est comme CustomerRepository mais dedans le microservice biling-service*/
            invoice.setCustomer(customer);

            InvoiceResponseDTO invoiceResponseDTO = invoiceMapper
                    .invoiceToInvoiceReponseDTO(invoice);
        return invoiceResponseDTO;
    }

    @Override
    public List<InvoiceResponseDTO> getAllInvoicesForCustomer(String customerId) {

        List<Invoice> invoices = invoiceRepository.findByCustomerId(customerId);
        Customer customer ;
        try {
            customer = customerRestClient
                    .getCustomer(customerId);
        }catch (Exception e){
            throw new CustomerNotFoundException("no customer with such id");
        }

        invoices.forEach(invoice -> {
            invoice.setCustomer(customer);
        });
        List<InvoiceResponseDTO> invoiceResponseDTOList = invoices
                .stream()
                .map(invoice -> invoiceMapper.invoiceToInvoiceReponseDTO(invoice))
                .collect(Collectors.toList());
        return invoiceResponseDTOList;
    }

    @Override
    public List<InvoiceResponseDTO> getAllInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        invoices.forEach(invoice -> {
            Customer customer = customerRestClient.getCustomer(invoice.getCustomerId());
            invoice.setCustomer(customer);
        });
        /*for(Invoice invoice: invoices){
            Customer customer = customerRestClient.getCustomer(invoice.getCustomerId());
            invoice.setCustomer(customer);
        }*/
        List<InvoiceResponseDTO> invoiceResponseDTOList = invoices
                .stream()
                .map(invoice -> invoiceMapper.invoiceToInvoiceReponseDTO(invoice))
                .collect(Collectors.toList());
        return invoiceResponseDTOList;
    }
}
