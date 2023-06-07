package com.globalSolution.api.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/tipos-solo")
public class TipoSoloController {
    Logger log = LoggerFactory.getLogger(TipoSoloController.class);

    List<TipoSolo> tiposSolo = new ArrayList<>();

    @Autowired
    TipoSoloRepository repository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    @GetMapping
    @ApiOperation("Retorna uma lista de tipos de solo")
    public ResponseEntity<List<TipoSolo>> index(@RequestParam(required = false) TipoSolo tipoSolo, Pageable pageable) {

        Page<TipoSolo> tipoSoloPage;

        if (tipoSolo == null) {
            tipoSoloPage = repository.findAll(pageable);
        } else {
            tipoSoloPage = repository.findByNameContaining(tipoSolo, pageable);
        }

        List<TipoSolo> tipoClimas = tipoSoloPage.getContent();

        return ResponseEntity.ok(tipoClimas);
    }

    @PostMapping
    @ApiOperation("Cria um novo tipo de solo")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Tipo de solo cadastrado com sucesso"),
        @ApiResponse(code = 400, message = "Erro na validação dos dados da requisição")
    })
    public EntityModel<TipoSolo> create(@RequestBody @Valid TipoSolo tipoSolo) {
        log.info("Cadastrando tipo solo: " + tipoSolo);
        repository.save(tipoSolo);
        return EntityModel.of(tipoSolo);
    }

    @GetMapping("{id}")
    @ApiOperation("Retorna os detalhes de um tipo de solo")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Tipo de solo encontrado com sucesso"),
        @ApiResponse(code = 404, message = "Tipo de solo não encontrado")
    })
    public TipoSolo show(@PathVariable Long id) {
        log.info("Buscando tipo solo com id " + id);
        return getTipoSolo(id);
    }

    @DeleteMapping("{id}")
    @ApiOperation("Exclui um tipo de solo")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Tipo de solo excluído com sucesso"),
        @ApiResponse(code = 404, message = "Tipo de solo não encontrado")
    })
    public ResponseEntity<TipoSolo> destroy(@PathVariable Long id){
        log.info("Apagando tipo de solo com id " + id);
        repository.delete(getTipoSolo(id));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza um tipo de solo")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Tipo de solo atualizado com sucesso"),
        @ApiResponse(code = 400, message = "Erro na validação dos dados da requisição"),
        @ApiResponse(code = 404, message = "Tipo de solo não encontrado")
    })
    public EntityModel<TipoSolo> update(@PathVariable Long id, @RequestBody @Valid TipoSolo tipoSolo) {
        log.info("Alterando tipoSolo com id " + id);
        TipoSolo existingTipoSolo = getTipoSolo(id);
        existingTipoSolo.setId_tipo_solo(id);;
        repository.save(existingTipoSolo);
        return EntityModel.of(existingTipoSolo);
    }

    private TipoSolo getTipoSolo(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo solo não existe"));
    }
}
