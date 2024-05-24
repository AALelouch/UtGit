package com.ut.market.service;

import com.ut.market.persistence.entity.market.InvoiceProduct;
import com.ut.market.persistence.entity.market.Product;
import com.ut.market.persistence.entity.market.Sale;
import com.ut.market.persistence.entity.users.Client;
import com.ut.market.persistence.repository.market.InvoiceProductRepository;
import com.ut.market.persistence.repository.market.ProductRepository;
import com.ut.market.persistence.repository.market.SaleRepository;
import com.ut.market.persistence.repository.users.ClientRepository;
import com.ut.market.service.exception.BadRequestException;
import com.ut.market.service.exception.NotFoundException;
import com.ut.market.service.mapper.SaleMapper;
import com.ut.market.service.request.sale.InvoiceProductRequest;
import com.ut.market.service.request.sale.SaleRequest;
import com.ut.market.service.response.sale.SaleResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalesService extends CrudService<Sale, Long, SaleRequest, SaleResponse>{

    private final SaleMapper saleMapper;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final InvoiceProductRepository invoiceProductRepository;

    public SalesService(SaleRepository repository, SaleMapper saleMapper, ClientRepository clientRepository, ProductRepository productRepository, InvoiceProductRepository invoiceProductRepository) {
        super(repository);
        this.saleMapper = saleMapper;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.invoiceProductRepository = invoiceProductRepository;
    }
//
//    private List<SaleResponse> findByAfterDate(LocalDateTime afterDate) {
//        return repository.findSaleByDateTimeOfSaleAfter(afterDate).stream()
//                .map(this::mapToResponse).collect(Collectors.toList());
//    }

    @Override
    public void save(SaleRequest saleRequest) {

        Sale sale = mapToEntity(saleRequest);
        repository.save(sale);
        invoiceProductRepository.saveAll(sale.getInvoiceProducts());

    }

    @Override
    protected Sale mapToEntity (SaleRequest saleRequest) {
        Sale sale = saleMapper.mapToEntity(saleRequest);

        return buildRequestEntity(saleRequest, sale);
    }

    @Override
    protected Sale mapToEntity(SaleRequest saleRequest , Long id) {
        Sale sale = saleMapper.mapToEntity(saleRequest);
        sale.setId(id);

        return buildRequestEntity(saleRequest, sale);
    }

    private Sale buildRequestEntity(SaleRequest saleRequest, Sale sale) {
        Client client = clientRepository.findById(saleRequest.getIdClient())
                .orElseThrow(() -> new NotFoundException("Client not found"));

        sale.setClient(client);
        sale.setTotalPrice(0F);
        sale.setDateTimeOfSale(LocalDateTime.now());

        saleRequest.getProducts().forEach(
                requestedProduct -> {

                    Product product = productRepository.findById(requestedProduct.getId())
                            .orElseThrow(() -> new NotFoundException("Product not found"));

                    calculateStock(product, requestedProduct);

                    InvoiceProduct invoiceProduct = buildInvoiceProduct(product, requestedProduct);
                    invoiceProduct.setSale(sale);

                    sale.getInvoiceProducts().add(invoiceProduct);
                    sale.setTotalPrice(sale.getTotalPrice() + invoiceProduct.getTotalPrice());

                }
        );

        return sale;
    }

    private void calculateStock(Product product, InvoiceProductRequest invoiceProductRequest){
        product.setStock(product.getStock() - invoiceProductRequest.getQuantity());
        if (product.getStock() < 0) throw new BadRequestException("There aren't products in stock");
        productRepository.save(product);
    }

    private InvoiceProduct buildInvoiceProduct(Product product, InvoiceProductRequest productRequest){

        return InvoiceProduct.builder()
                .name(product.getName())
                .category(product.getCategory())
                .quantity(productRequest.getQuantity())
                .totalPrice(product.getPrice() * productRequest.getQuantity())
                .build();

    }

    @Override
    protected SaleResponse mapToResponse(Sale sale) {
        return saleMapper.mapToResponse(sale);
    }
}
