package com.ut.market.service.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponse implements Serializable {
    private Long id;
    private String name;
    private String address;
    private String phone;
}
