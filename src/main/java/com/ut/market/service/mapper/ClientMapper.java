package com.ut.market.service.mapper;

import com.ut.market.persistence.entity.users.Client;
import com.ut.market.service.request.ClientRequest;
import com.ut.market.service.response.ClientResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client mapToEntity(ClientRequest clientRequest);
    ClientResponse mapToResponse(Client client);

}
