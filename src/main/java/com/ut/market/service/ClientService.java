package com.ut.market.service;

import com.ut.market.persistence.entity.users.Client;
import com.ut.market.persistence.repository.users.ClientRepository;
import com.ut.market.service.mapper.ClientMapper;
import com.ut.market.service.request.ClientRequest;
import com.ut.market.service.response.ClientResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService extends CrudService<Client, Long, ClientRequest, ClientResponse> {

    private final ClientMapper clientMapper;

    public ClientService(ClientRepository repository, ClientMapper clientMapper) {
        super(repository);
        this.clientMapper = clientMapper;
    }

    public List<ClientResponse> findByName(String name) {
        ClientRepository clientRepository = (ClientRepository) repository;
        return clientRepository.findByNameContaining(name)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    protected Client mapToEntity(ClientRequest request) {
        return clientMapper.mapToEntity(request);
    }

    @Override
    protected Client mapToEntity(ClientRequest request, Long id) {
        Client client = clientMapper.mapToEntity(request);
        client.setId(id);
        return client;
    }

    @Override
    protected ClientResponse mapToResponse(Client client) {
        return clientMapper.mapToResponse(client);
    }
}
