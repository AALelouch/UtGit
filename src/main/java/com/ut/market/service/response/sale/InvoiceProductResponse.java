package com.ut.market.service.response.sale;

import com.ut.market.persistence.entity.market.Category;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceProductResponse {

    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Category category;
    private Float totalPrice;
    private Integer quantity;

}
