package com.ut.market.persistence.entity.users;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.time.LocalTime;

@Entity
@Table(name = "employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
public non-sealed class Employee extends People implements Serializable {

    private LocalTime startTime;
    private LocalTime endTime;

}
