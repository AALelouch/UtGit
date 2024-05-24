package com.ut.market.service.request.sale;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleRequest implements Serializable {

    private Long idClient;
    private List<InvoiceProductRequest> products;

}
