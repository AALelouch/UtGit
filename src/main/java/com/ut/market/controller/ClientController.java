package com.ut.market.controller;

import com.ut.market.persistence.entity.users.Client;
import com.ut.market.service.ClientService;
import com.ut.market.service.CrudService;
import com.ut.market.service.request.ClientRequest;
import com.ut.market.service.response.ClientResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Expose the endpoint regarding client logic and administration of clients
 */
@RestController
@RequestMapping("/api/v1/client")
public class ClientController {

    private final CrudService<Client, Long, ClientRequest, ClientResponse> crudService;

    public ClientController(ClientService crudService) {
        this.crudService = crudService;
    }

    @GetMapping
    public ResponseEntity<List<ClientResponse>> findAll(){
        return ResponseEntity.ok(crudService.findAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ClientResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(crudService.findById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<ClientResponse>> findByName(@PathVariable String name){
        ClientService clientService = (ClientService) crudService;
        return ResponseEntity.ok(clientService.findByName(name));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody ClientRequest clientRequest){
        crudService.save(clientRequest);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateById(@RequestBody ClientRequest clientRequest, @PathVariable Long id){
        crudService.update(clientRequest, id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id){
        crudService.delete(id);
    }

}
