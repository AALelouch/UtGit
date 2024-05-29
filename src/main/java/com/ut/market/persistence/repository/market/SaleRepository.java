package com.ut.market.persistence.repository.market;

import com.ut.market.persistence.entity.market.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Sale repository used in sales managment, has special fields to contains data about products
 * Has special and custom method for apply business logic
 * */
@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    List<Sale> findSaleByDateTimeOfSaleAfter(LocalDateTime localDateTime);

}
