package com.globalSolution.api.controllers;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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

import com.globalSolution.api.models.Cultivos;
import com.globalSolution.api.repository.CultivosRepository;
import com.globalSolution.api.repository.GraoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cultivos")
public class CultivosController {
    Logger log = LoggerFactory.getLogger(CultivosController.class);

    List<Cultivos> cultivos = new ArrayList<>();

    @Autowired // IoD IoC
    CultivosRepository repository;
    GraoRepository graoRepository;


    @Autowired
    PagedResourcesAssembler<Object> assembler;

    @GetMapping
    @ApiOperation("Retorna uma lista de cultivos")
    public ResponseEntity<CollectionModel<EntityModel<Cultivos>>> index(@RequestParam(required = false) Int id, @PageableDefault(size = 5) Pageable pageable){

        List<EntityModel<Cultivos>> cultivosModel = new ArrayList<>();

        if (id == null) {
            List<Cultivos> cultivos = repository.findAll(pageable).getContent();
            for (Cultivos cultivo : cultivos) {
                cultivosModel.add(getCultivosModel(cultivo));
            }
        } else {
            List<Cultivos> cultivos = repository.findById(id, pageable).getContent();
            for (Cultivos cultivo : cultivos) {
                cultivosModel.add(getCultivoModel(cultivo));
            }
        }

        CollectionModel<EntityModel<Cultivos>> collectionModel = CollectionModel.of(cultivosModel);
        collectionModel.add(getSelfLink());
        return ResponseEntity.ok(collectionModel);

    }

    @PostMapping
    @ApiOperation("Cria um novo cultivo")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Cultivos cadastrado com sucesso"),
        @ApiResponse(code = 400, message = "Erro na validação dos dados da requisição")
    })
    public ResponseEntity<EntityModel<Cultivos>> create(@RequestBody @Valid Cultivos cultivo){
        log.info("Cadastrando cultivo: " + cultivo);
        Cultivos postObj = repository.save(cultivo);
        EntityModel<Cultivos> cultivoModel = getCultivoModel(postObj);
        cultivoModel.add(getSelfLink());
        cultivoModel.add(getUpdateLink(postObj.getId()));
        cultivoModel.add(getDeleteLink(postObj.getId()));
        return ResponseEntity.created(cultivoModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(cultivoModel);
    }

    @GetMapping("{id}")
    @ApiOperation("Retorna os detalhes de um cultivo")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Cultivos encontrado com sucesso"),
        @ApiResponse(code = 404, message = "Cultivos não encontrado")
    })
    public ResponseEntity<EntityModel<Cultivos>> show(@PathVariable Long id){
        log.info("Buscando cultivo com id " + id);
        Cultivos cultivo = getCultivo(id);
        EntityModel<Cultivos> cultivoModel = getCultivoModel(cultivo);
        cultivoModel.add(getSelfLink());
        cultivoModel.add(getUpdateLink(id));
        cultivoModel.add(getDeleteLink(id));
        return ResponseEntity.ok(cultivoModel);
    }

    @DeleteMapping("{id}")
    @ApiOperation("Exclui um cultivo")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Cultivos encontrado com sucesso"),
        @ApiResponse(code = 404, message = "Cultivos não encontrado")
    })
    public ResponseEntity<Cultivos> destroy(@PathVariable Long id){
        log.info("Apagando cultivo com id " + id);
        repository.delete(getCultivo(id));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza um cultivo")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Cultivos encontrado com sucesso"),
        @ApiResponse(code = 404, message = "Cultivos não encontrado")
    })
    public ResponseEntity<EntityModel<Cultivos>> update(@PathVariable Long id, @RequestBody @Valid Cultivos cultivo){
        log.info("Alterando cultivo com id " + id);
        getCultivo(id);
        cultivo.setId(id);
        Cultivos putObj = repository.save(cultivo);
        EntityModel<Cultivos> cultivoModel = getCultivoModel(putObj);
        cultivoModel.add(getSelfLink());
        cultivoModel.add(getDeleteLink(putObj.getId()));
        return ResponseEntity.ok(cultivoModel);
    }

    private Cultivos getCultivo(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cultivos não existe"));
    }

}
