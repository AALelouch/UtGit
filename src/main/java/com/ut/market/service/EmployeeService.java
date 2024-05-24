package com.ut.market.service;

import com.ut.market.persistence.entity.users.Employee;
import com.ut.market.persistence.repository.users.EmployeeRepository;
import com.ut.market.service.mapper.EmployeeMapper;
import com.ut.market.service.request.EmployeeRequest;
import com.ut.market.service.response.EmployeeResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService extends CrudService<Employee, Long, EmployeeRequest, EmployeeResponse>{

    private final EmployeeMapper employeeMapper;

    public List<EmployeeResponse> findByName(String name) {
        EmployeeRepository clientRepository = (EmployeeRepository) repository;
        return clientRepository.findByNameContaining(name)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public EmployeeService(EmployeeRepository repository, EmployeeMapper employeeMapper) {
        super(repository);
        this.employeeMapper = employeeMapper;
    }

    @Override
    protected Employee mapToEntity(EmployeeRequest employeeRequest) {
        return employeeMapper.mapToEntity(employeeRequest);
    }

    @Override
    protected Employee mapToEntity(EmployeeRequest employeeRequest, Long id) {
        Employee employee = employeeMapper.mapToEntity(employeeRequest);
        employee.setId(id);
        return  employee;
    }

    @Override
    protected EmployeeResponse mapToResponse(Employee employee) {
        return employeeMapper.mapToResponse(employee);
    }
}
