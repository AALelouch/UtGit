package com.ut.market.service;

import com.ut.market.service.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public abstract class CrudService<
        ENTITY extends Serializable,
        ID extends Serializable,
        REQUEST extends Serializable,
        RESPONSE extends Serializable> {

    protected JpaRepository<ENTITY, ID> repository;

    public CrudService() {
    }

    public CrudService(JpaRepository<ENTITY, ID> repository) {
        this.repository = repository;
    }

    public void save(REQUEST request){
        repository.save(mapToEntity(request));
    }

    public List<RESPONSE> findAll(){
        return repository.findAll().stream().map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void update(REQUEST request, ID id){
        repository.findById(id).orElseThrow(()-> new NotFoundException("Entity not found: " + id));
        repository.save(mapToEntity(request, id));
    }

    public void delete(ID id){
        repository.deleteById(id);
    }

    public RESPONSE findById(ID id){
        return repository.findById(id).map(this::mapToResponse)
                .orElseThrow(() -> new NotFoundException("Entity not found: " + id));
    }

    protected abstract ENTITY mapToEntity(REQUEST request);
    protected abstract ENTITY mapToEntity(REQUEST request, ID id);
    protected abstract RESPONSE mapToResponse(ENTITY client);}
