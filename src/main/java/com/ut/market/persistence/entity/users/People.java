package com.ut.market.persistence.entity.users;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * People class used in People managment, has special fields to contains data about people
 * It's used to mapping employees and clients
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public sealed class People permits Employee, Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String name;
    protected String address;
    protected String phone;

}
