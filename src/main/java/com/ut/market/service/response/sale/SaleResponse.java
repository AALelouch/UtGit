package com.ut.market.service.response.sale;

import com.ut.market.persistence.entity.users.Client;
import com.ut.market.service.response.ClientResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleResponse implements Serializable {

    private Long id;
    private Float totalPrice;
    private LocalDateTime dateTimeOfSale;
    private List<InvoiceProductResponse> invoiceProducts;
    private ClientResponse client;


}
