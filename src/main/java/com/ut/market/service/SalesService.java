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
import com.ut.market.service.request.sale.ReportDateSaleRequest;
import com.ut.market.service.request.sale.SaleRequest;
import com.ut.market.service.response.sale.ReportDateSaleResponse;
import com.ut.market.service.response.sale.SaleResponse;
import com.ut.market.service.util.PdfGenerator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalesService extends CrudService<Sale, Long, SaleRequest, SaleResponse>{

    private final SaleMapper saleMapper;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final InvoiceProductRepository invoiceProductRepository;
    private final PdfGenerator<SaleResponse> pdfGeneratorInvoice;
    private final PdfGenerator<ReportDateSaleResponse> pdfGeneratorDailyReport;


    private final String INVOICE_CLIENT_TEMPLATE = "ClientInvoiceTemplate";
    private final String DAILY_REPORT_CLIENT_TEMPLATE = "InvoicePerDateTemplate";


    public SalesService(JpaRepository<Sale, Long> repository, SaleMapper saleMapper, ClientRepository clientRepository, ProductRepository productRepository, InvoiceProductRepository invoiceProductRepository, PdfGenerator<SaleResponse> pdfGeneratorInvoice, PdfGenerator<ReportDateSaleResponse> pdfGeneratorDailyReport) {
        super(repository);
        this.saleMapper = saleMapper;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.invoiceProductRepository = invoiceProductRepository;
        this.pdfGeneratorInvoice = pdfGeneratorInvoice;
        this.pdfGeneratorDailyReport = pdfGeneratorDailyReport;
    }

    public ReportDateSaleResponse findByAfterDate(ReportDateSaleRequest afterDate) {

        ReportDateSaleResponse response = new ReportDateSaleResponse();
        response.setSaleResponses(((SaleRepository)repository).findSaleByDateTimeOfSaleAfter(afterDate.getReportDate()).stream()
                .map(this::mapToResponse).collect(Collectors.toList()));
        response.setDateStart(afterDate.getReportDate());
        response.setTotalOfDate(0F);
        response.setReportName(afterDate.getReportName());

        response.getSaleResponses().forEach(saleResponse -> {
            response.setTotalOfDate(saleResponse.getTotalPrice() + response.getTotalOfDate());
        });


        try {
            String fileName = "Sale_Date_Report_" + afterDate.getReportDate().format(DateTimeFormatter.BASIC_ISO_DATE)
                    + "_" + afterDate.getReportName();
            pdfGeneratorDailyReport.generatePdf(DAILY_REPORT_CLIENT_TEMPLATE, response, fileName);

        }catch (IOException ioException){
            System.out.println("Error generating invoice");
            System.out.println(ioException);
            System.out.println(ioException.getMessage());
            System.out.println(ioException.getStackTrace());
        }
        return response;
    }

    public SaleResponse createSale(SaleRequest saleRequest){
        save(saleRequest);
        SaleResponse saleResponse = findById(saleRequest.getIdSale());

        try {
            String fileName = "Sale_" + saleResponse.getDateTimeOfSale().format(DateTimeFormatter.BASIC_ISO_DATE)
                    + "_" + saleResponse.getId();
            pdfGeneratorInvoice.generatePdf(INVOICE_CLIENT_TEMPLATE, saleResponse, fileName);

        }catch (IOException ioException){
            System.out.println("Error generating invoice");
            System.out.println(ioException);
            System.out.println(ioException.getMessage());
            System.out.println(ioException.getStackTrace());
        }

        return saleResponse;
    }

    @Override
    public void save(SaleRequest saleRequest) {

        Sale sale = mapToEntity(saleRequest);
        repository.save(sale);
        invoiceProductRepository.saveAll(sale.getInvoiceProducts());
        saleRequest.setIdSale(sale.getId());
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

        List<Product> affectedProducts = new ArrayList<>();

        sale.setClient(client);
        sale.setTotalPrice(0F);
        sale.setDateTimeOfSale(LocalDateTime.now());

        saleRequest.getProducts().forEach(
                requestedProduct -> {

                    Product product = productRepository.findById(requestedProduct.getId())
                            .orElseThrow(() -> new NotFoundException("Product not found"));

                    calculateStock(product, requestedProduct, affectedProducts);

                    InvoiceProduct invoiceProduct = buildInvoiceProduct(product, requestedProduct);
                    invoiceProduct.setSale(sale);
                    sale.getInvoiceProducts().add(invoiceProduct);
                    sale.setTotalPrice(sale.getTotalPrice() + invoiceProduct.getTotalPrice());

                }
        );

        productRepository.saveAll(affectedProducts);

        return sale;
    }

    private void calculateStock(Product product,
                                InvoiceProductRequest invoiceProductRequest,
                                List<Product> affectedProducts){
        product.setStock(product.getStock() - invoiceProductRequest.getQuantity());
        if (product.getStock() < 0) throw new BadRequestException("There aren't products in stock");
        affectedProducts.add(product);
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
