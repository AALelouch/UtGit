package com.ut.market.persistence.entity.users;

import com.ut.market.persistence.entity.market.InvoiceProduct;
import com.ut.market.persistence.entity.market.Sale;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.List;

/**
 * Client entity used in client managment, has special fields to contains data about clients
 * */
@Entity
@Table(name = "client")
public non-sealed class Client extends People implements Serializable {

    @OneToMany(mappedBy = "client")
    private List<Sale> sales;

}
