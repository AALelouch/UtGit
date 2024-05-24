package com.ut.market.service;

import com.ut.market.persistence.entity.market.Category;
import com.ut.market.persistence.entity.market.Product;
import com.ut.market.persistence.repository.market.ProductRepository;
import com.ut.market.service.mapper.ProductMapper;
import com.ut.market.service.request.ProductRequest;
import com.ut.market.service.response.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService extends CrudService<Product, Long, ProductRequest, ProductResponse>{

    private final HashMap<String, Category> categoryAsValue = new HashMap<>(Map.of(
            "Papeleria", Category.PAPER,
            "Comestibles", Category.FOOD,
            "Aseo", Category.CLEANING
    ));

    private final HashMap<Category, String> categoryAsKey = new HashMap<>(Map.of(
            Category.PAPER, "Papeleria",
            Category.FOOD, "Comestibles",
            Category.CLEANING, "Aseo"
    ));

    private final ProductMapper productMapper;

    public ProductService(ProductRepository repository, ProductMapper productMapper) {
        super(repository);
        this.productMapper = productMapper;
    }

    public List<ProductResponse> findByName(String name) {
        ProductRepository productRepository = (ProductRepository) repository;
        return productRepository.findByNameContaining(name)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<ProductResponse> findByCategory(String category) {
        ProductRepository productRepository = (ProductRepository) repository;
        return productRepository.findByCategory(categoryAsValue.get(category))
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    protected Product mapToEntity(ProductRequest productRequest) {
        Product product = productMapper.mapToEntity(productRequest);
        product.setCategory(categoryAsValue.get(productRequest.getCategory()));
        return product;
    }

    @Override
    protected Product mapToEntity(ProductRequest productRequest, Long id) {
        Product product = productMapper.mapToEntity(productRequest);
        product.setCategory(categoryAsValue.get(productRequest.getCategory()));
        product.setId(id);
        return product;
    }

    @Override
    protected ProductResponse mapToResponse(Product product) {
        ProductResponse productResponse = productMapper.mapToResponse(product);
        productResponse.setCategory(product.getCategory().getName());
        return productResponse;
    }
}
