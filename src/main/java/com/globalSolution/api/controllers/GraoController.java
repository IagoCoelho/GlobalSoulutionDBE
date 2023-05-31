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

import com.globalSolution.api.models.Grao;
import com.globalSolution.api.repository.GraoRepository;

@RestController
@RequestMapping("/api/graos")
public class GraoController {
    Logger log = LoggerFactory.getLogger(GraoController.class);

    List<Grao> grao = new ArrayList<>();

    @Autowired
    GraoRepository repository;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Grao>>> index (@RequestParam(required = false) String grao, @PageableDefault(size = 5) Pageable pageable){

        List<EntityModel<Grao>> graosModel = new ArrayList<>();
        
        if(grao == null){
            List<Grao> graos = repository.findAll(pageable).getContent();
            for(Grao grao : graos){
                graosModel.add(getGraoModel(grao));
            }
        } else {
            List<Grao> graos = repository.findByNameContaining(grao, pageable).getContent();
            for (Grao grao : graos){
                graosModel.add(getGraoModel(grao));
            }
        }

        CollectionModel<EntityModel<Grao>> collectionModel = CollectionModel.of(graosModel);
        collectionModel.add(getSelfLink());
        return ResponseEntity.ok(collectionModel);

    }

    @PostMapping
    public ResponseEntity<EntityModel<Grao>> create(@RequestBody @valid Grao grao){
        log.info("Cadastrando grão: " + grao);
        Grao postObj = repository.save(grao);
        EntityModel<Grao> graoModel = getGraoModel(postObj);
        graoModel.add(getSelfLink());
        graoModel.add(getupdateLink(postObj.getId()));
        graoModel.add(getDeleteLink(postObj.getId()));
        return ResponseEntity.created(graoModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(graoModel);
    }

    @GetMapping("{id}")
    public ResponseEntity<EntityModel<Grao>> show(@PathVariable Long id){
        log.info("Buscando grao com id " + id);
        Grao grao = getGrao(id);
        EntityModel<Grao> GraoModel = getGraoModel(grao);
        graoModel.add(getSelfLink());
        graoModel.add(getUpdateLink(id));
        graoModel.add(getDeleteLink(id));
        return ResponseEntity.ok(graoModel);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Grao> destroy(@PathVariable Long id){
        log.info("Apagando grao com id " + id);
        repository.delete(getGrao(id));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<EntityModel<Grao>> update(@PathVariable Long id, @RequestBody @Valid Grao grao){
        log.info("Alterando grao com id " + id);
        getGrao(id);
        grao.setId(id);
        Grao putObj = repository.save(grao);
        EntityModel<Grao> graoModel = getGraoModel(putObj);
        graoModel.add(getSelfLink());
        graoModel.add(getDeleteLink(putObj.getId()));
        return ResponseEntity.ok(grao);
    }

    private Grao getGrao(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Grão não existe"));
    }

}
