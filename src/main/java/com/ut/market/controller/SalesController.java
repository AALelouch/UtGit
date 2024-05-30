package com.ut.market.controller;

import com.ut.market.persistence.entity.market.Sale;
import com.ut.market.service.CrudService;
import com.ut.market.service.SalesService;
import com.ut.market.service.request.sale.ReportDateSaleRequest;
import com.ut.market.service.request.sale.SaleRequest;
import com.ut.market.service.response.sale.ReportDateSaleResponse;
import com.ut.market.service.response.sale.SaleResponse;
import com.ut.market.service.util.PdfFinder;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * Expose the endpoint regarding sale logic and administration of sales also report generation
 */
@RestController
@RequestMapping("/api/v1/sales")
public class SalesController {

    private final CrudService<Sale, Long, SaleRequest, SaleResponse> crudService;
    private final PdfFinder finder;

    public SalesController(CrudService<Sale, Long, SaleRequest, SaleResponse> crudService, PdfFinder finder) {
        this.crudService = crudService;
        this.finder = finder;
    }

    @GetMapping
    public ResponseEntity<List<SaleResponse>> findAll(){
        return ResponseEntity.ok(crudService.findAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<SaleResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(crudService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SaleResponse create(@RequestBody SaleRequest saleRequest){
        return ((SalesService) crudService).createSale(saleRequest);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateById(@RequestBody SaleRequest saleRequest, @PathVariable Long id){
        crudService.update(saleRequest, id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id){
        crudService.delete(id);
    }

    @GetMapping(path = "/download/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    @ApiResponse(content = @Content(schema = @Schema(type = "string", format = "binary")), responseCode = "200")
    public ResponseEntity<Resource> download(@PathVariable String id) throws IOException {

        ByteArrayResource resource = finder.findPdfUsingIdAsLastDigits(id);

        return ResponseEntity.ok()
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_PDF)
                .header("Content-Disposition", "attachment; filename=" + id + ".pdf")
                .body(resource);
    }

    @PostMapping("/dateReport/")
    public ResponseEntity<ReportDateSaleResponse> generateReport(@RequestBody ReportDateSaleRequest request){
        return ResponseEntity.ok(((SalesService) crudService).findByAfterDate(request));
    }

}
