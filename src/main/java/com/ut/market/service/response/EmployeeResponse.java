package com.ut.market.service.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse implements Serializable {

    private Long id;
    private String name;
    private String address;
    private String phone;
    private LocalTime startTime;
    private LocalTime endTime;

}
