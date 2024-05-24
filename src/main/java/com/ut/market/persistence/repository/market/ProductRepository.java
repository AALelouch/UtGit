package com.ut.market.persistence.repository.market;

import com.ut.market.persistence.entity.market.Category;
import com.ut.market.persistence.entity.market.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContaining(String name);
    List<Product> findByCategory(Category category);
}
