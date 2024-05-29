package com.ut.market.persistence.repository.users;

import com.ut.market.persistence.entity.users.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Product repository used in people managment, has special fields to contains data about clients
 * Has special and custom method for apply business logic
 * */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findByNameContaining(String name);

}
