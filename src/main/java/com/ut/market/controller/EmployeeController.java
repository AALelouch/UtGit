package com.ut.market.controller;

import com.ut.market.persistence.entity.users.Employee;
import com.ut.market.service.CrudService;
import com.ut.market.service.EmployeeService;
import com.ut.market.service.request.EmployeeRequest;
import com.ut.market.service.response.EmployeeResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final CrudService<Employee, Long, EmployeeRequest, EmployeeResponse> crudService;

    public EmployeeController(EmployeeService crudService) {
        this.crudService = crudService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> findAll(){
        return ResponseEntity.ok(crudService.findAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<EmployeeResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(crudService.findById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<EmployeeResponse>> findByName(@PathVariable String name){
        EmployeeService clientService = (EmployeeService) crudService;
        return ResponseEntity.ok(clientService.findByName(name));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody EmployeeRequest clientRequest){
        crudService.save(clientRequest);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateById(@RequestBody EmployeeRequest employeeRequest, @PathVariable Long id){
        crudService.update(employeeRequest, id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id){
        crudService.delete(id);
    }

}
