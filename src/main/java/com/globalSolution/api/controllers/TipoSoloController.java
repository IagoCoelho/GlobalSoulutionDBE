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

import com.globalSolution.api.models.TipoSolo;
import com.globalSolution.api.repository.TipoSoloRepository;
@RestController
@RequestMapping("/api/solo")
public class TipoSoloController {
    Logger log = LoggerFactory.getLogger(TipoSoloController.class);

    List<TipoSolo> solo = new ArrayList<>();

    @Autowired
    TipoSoloRepository repository;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<TipoSolo>>> index (@RequestParam(required = false) String TipoSolo, @PageableDefault(size = 5) Pageable pageable){

        List<EntityModel<TipoSolo>> TipoSolosModel = new ArrayList<>();
        
        if(TipoSolo == null){
            List<TipoSolo> TipoSolos = repository.findAll(pageable).getContent();
            for(TipoSolo tipoSolo : TipoSolos){
                TipoSolosModel.add(getTipoSoloModel(TipoSolo));
            }
        } else {
            List<TipoSolo> TipoSolos = repository.findByNameContaining(TipoSolo, pageable).getContent();
            for (TipoSolo tipoSolo : TipoSolos){
                TipoSolosModel.add(getTipoSoloModel(TipoSolo));
            }
        }

        CollectionModel<EntityModel<TipoSolo>> collectionModel = CollectionModel.of(TipoSolosModel);
        collectionModel.add(getSelfLink());
        return ResponseEntity.ok(collectionModel);

    }

    @PostMapping
    public ResponseEntity<EntityModel<TipoSolo>> create(@RequestBody @valid TipoSolo TipoSolo){
        log.info("Cadastrando grão: " + TipoSolo);
        TipoSolo postObj = repository.save(TipoSolo);
        EntityModel<TipoSolo> TipoSoloModel = getTipoSoloModel(postObj);
        TipoSoloModel.add(getSelfLink());
        TipoSoloModel.add(getupdateLink(postObj.getId()));
        TipoSoloModel.add(getDeleteLink(postObj.getId()));
        return ResponseEntity.created(TipoSoloModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(TipoSoloModel);
    }

    @GetMapping("{id}")
    public ResponseEntity<EntityModel<TipoSolo>> show(@PathVariable Long id){
        log.info("Buscando TipoSolo com id " + id);
        TipoSolo TipoSolo = getTipoSolo(id);
        EntityModel<TipoSolo> TipoSoloModel = getTipoSoloModel(TipoSolo);
        TipoSoloModel.add(getSelfLink());
        TipoSoloModel.add(getUpdateLink(id));
        TipoSoloModel.add(getDeleteLink(id));
        return ResponseEntity.ok(TipoSoloModel);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<TipoSolo> destroy(@PathVariable Long id){
        log.info("Apagando TipoSolo com id " + id);
        repository.delete(getTipoSolo(id));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<EntityModel<TipoSolo>> update(@PathVariable Long id, @RequestBody @Valid TipoSolo TipoSolo){
        log.info("Alterando TipoSolo com id " + id);
        getTipoSolo(id);
        TipoSolo.setId(id);
        TipoSolo putObj = repository.save(TipoSolo);
        EntityModel<TipoSolo> TipoSoloModel = getTipoSoloModel(putObj);
        TipoSoloModel.add(getSelfLink());
        TipoSoloModel.add(getDeleteLink(putObj.getId()));
        return ResponseEntity.ok(TipoSolo);
    }

    private TipoSolo getTipoSolo(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Grão não existe"));
    }
}
