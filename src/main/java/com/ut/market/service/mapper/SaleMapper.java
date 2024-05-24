package com.ut.market.service.mapper;

import com.ut.market.persistence.entity.market.InvoiceProduct;
import com.ut.market.persistence.entity.market.Sale;
import com.ut.market.service.request.sale.SaleRequest;
import com.ut.market.service.response.sale.InvoiceProductResponse;
import com.ut.market.service.response.sale.SaleResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SaleMapper {

    Sale mapToEntity(SaleRequest productRequest);
    SaleResponse mapToResponse(Sale product);

    InvoiceProductResponse mapProductToResponse(InvoiceProduct invoiceProduct);


}
