package com.ut.market.service.mapper;

import com.ut.market.persistence.entity.market.Product;
import com.ut.market.persistence.entity.users.Employee;
import com.ut.market.service.request.EmployeeRequest;
import com.ut.market.service.request.ProductRequest;
import com.ut.market.service.response.EmployeeResponse;
import com.ut.market.service.response.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "category", ignore = true)
    Product mapToEntity(ProductRequest productRequest);
    @Mapping(target = "category", ignore = true)
    ProductResponse mapToResponse(Product product);

}
