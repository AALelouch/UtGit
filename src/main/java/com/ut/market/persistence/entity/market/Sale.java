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
