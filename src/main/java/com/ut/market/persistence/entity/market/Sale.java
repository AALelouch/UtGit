package com.ut.market.persistence.entity.market;

import com.ut.market.persistence.entity.users.Client;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *  Sale entity used in sales managment, has special fields to contains data about sales
 *  Has multiple invoice product
 *  Has relation many to one with client
 */
@Entity
@Table(name = "sale")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sale implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float totalPrice;
    private LocalDateTime dateTimeOfSale;

    @OneToMany(mappedBy = "sale")
    private List<InvoiceProduct> invoiceProducts = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="id_client", nullable=false)
    private Client client;



}
