package com.globalSolution.api.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.html.parser.Entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.globalSolution.api.models.TipoClima;
import com.globalSolution.api.repository.TipoClimaRepository;

@RestController
@RequestMapping("/api/clima")
public class TipoClimaController {
    Logger log = LoggerFactory.getLogger(TipoClimaController.class);

    List<TipoClima> clima = new ArrayList<>();

    @Autowired
    TipoClimaRepository repository;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<TipoClima>>> index (@RequestParam(required = false) String TipoClima, @PageableDefault(size = 5) Pageable pageable){

        List<EntityModel<TipoClima>> TipoClimasModel = new ArrayList<>();
        
        if(TipoClima == null){
            List<TipoClima> tipoClimas = repository.findAll(pageable).getContent();
            for(TipoClima tipoClima : tipoClimas){
                TipoClimasModel.add(getTipoClimaModel(TipoClima));
            }
        } else {
            List<TipoClima> TipoClimas = repository.findByNameContaining(TipoClima, pageable).getContent();
            for (TipoClima tipoClima : TipoClimas){
                TipoClimasModel.add(getTipoClimaModel(TipoClima));
            }
        }

        CollectionModel<EntityModel<TipoClima>> collectionModel = CollectionModel.of(TipoClimasModel);
        collectionModel.add(getSelfLink());
        return ResponseEntity.ok(collectionModel);

    }

    @PostMapping
    public ResponseEntity<EntityModel<TipoClima>> create(@RequestBody @valid TipoClima TipoClima){
        log.info("Cadastrando grão: " + TipoClima);
        TipoClima postObj = repository.save(TipoClima);
        EntityModel<TipoClima> TipoClimaModel = getTipoClimaModel(postObj);
        TipoClimaModel.add(getSelfLink());
        TipoClimaModel.add(getupdateLink(postObj.getId()));
        TipoClimaModel.add(getDeleteLink(postObj.getId()));
        return ResponseEntity.created(TipoClimaModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(TipoClimaModel);
    }

    @GetMapping("{id}")
    public ResponseEntity<EntityModel<TipoClima>> show(@PathVariable Long id){
        log.info("Buscando TipoClima com id " + id);
        TipoClima TipoClima = getTipoClima(id);
        EntityModel<TipoClima> TipoClimaModel = getTipoClimaModel(TipoClima);
        TipoClimaModel.add(getSelfLink());
        TipoClimaModel.add(getUpdateLink(id));
        TipoClimaModel.add(getDeleteLink(id));
        return ResponseEntity.ok(TipoClimaModel);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<TipoClima> destroy(@PathVariable Long id){
        log.info("Apagando TipoClima com id " + id);
        repository.delete(getTipoClima(id));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<EntityModel<TipoClima>> update(@PathVariable Long id, @RequestBody @Valid TipoClima TipoClima){
        log.info("Alterando TipoClima com id " + id);
        getTipoClima(id);
        TipoClima.setId(id);
        TipoClima putObj = repository.save(TipoClima);
        EntityModel<TipoClima> TipoClimaModel = getTipoClimaModel(putObj);
        TipoClimaModel.add(getSelfLink());
        TipoClimaModel.add(getDeleteLink(putObj.getId()));
        return ResponseEntity.ok(TipoClima);
    }

    private TipoClima getTipoClima(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Grão não existe"));
    }
    
}
