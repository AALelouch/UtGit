package com.ut.market.controller;

import com.ut.market.persistence.entity.market.Sale;
import com.ut.market.service.CrudService;
import com.ut.market.service.SalesService;
import com.ut.market.service.request.sale.SaleRequest;
import com.ut.market.service.response.sale.SaleResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sales")
public class SalesController {

    private final CrudService<Sale, Long, SaleRequest, SaleResponse> crudService;

    public SalesController(SalesService crudService) {
        this.crudService = crudService;
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
    public void create(@RequestBody SaleRequest saleRequest){
        crudService.save(saleRequest);
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

}
