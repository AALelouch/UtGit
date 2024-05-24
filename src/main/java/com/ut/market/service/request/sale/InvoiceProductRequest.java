package com.ut.market.service.request.sale;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceProductRequest {

    private Long id;
    private Float quantity;

}
