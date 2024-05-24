package com.ut.market.service.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequest implements Serializable {
    private String name;
    private String address;
    private String phone;
}
