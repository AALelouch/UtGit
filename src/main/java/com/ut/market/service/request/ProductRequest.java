package com.ut.market.service.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest implements Serializable {
    private String name;
    private String category;
    private Float price;
    private Integer stock;
}
