package com.ut.market.controller;

import com.ut.market.persistence.entity.market.Product;
import com.ut.market.service.CrudService;
import com.ut.market.service.ProductService;
import com.ut.market.service.request.ProductRequest;
import com.ut.market.service.response.ProductResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final CrudService<Product, Long, ProductRequest, ProductResponse> crudService;

    public ProductController(ProductService crudService) {
        this.crudService = crudService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll(){
        return ResponseEntity.ok(crudService.findAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(crudService.findById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<ProductResponse>> findByName(@PathVariable String name){
        ProductService productService = (ProductService) crudService;
        return ResponseEntity.ok(productService.findByName(name));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductResponse>> findByCategory(@PathVariable String category){
        ProductService productService = (ProductService) crudService;
        return ResponseEntity.ok(productService.findByCategory(category));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody ProductRequest productRequest){
        crudService.save(productRequest);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateById(@RequestBody ProductRequest productRequest, @PathVariable Long id){
        crudService.update(productRequest, id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id){
        crudService.delete(id);
    }

}
