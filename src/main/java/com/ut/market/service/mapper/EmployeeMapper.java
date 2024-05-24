package com.ut.market.service.mapper;

import com.ut.market.persistence.entity.users.Client;
import com.ut.market.persistence.entity.users.Employee;
import com.ut.market.service.request.ClientRequest;
import com.ut.market.service.request.EmployeeRequest;
import com.ut.market.service.response.ClientResponse;
import com.ut.market.service.response.EmployeeResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    Employee mapToEntity(EmployeeRequest clientRequest);
    EmployeeResponse mapToResponse(Employee client);

}
