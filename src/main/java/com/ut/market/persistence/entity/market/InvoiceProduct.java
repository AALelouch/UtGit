package com.ut.market.persistence.entity.market;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Product entity used in sales managment, has special fields to contains data about invoice products
 * Has a relation with Sale
 */
@Entity
@Table(name = "invoice_product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Category category;
    private Float totalPrice;
    private Float quantity;

    @ManyToOne
    @JoinColumn(name="id_sale", nullable=false)
    private Sale sale;


}
