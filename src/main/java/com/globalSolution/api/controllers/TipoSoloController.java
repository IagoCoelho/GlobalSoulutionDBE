package com.globalSolution.api.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.globalSolution.api.models.TipoSolo;

import javax.validation.Valid;
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
    public ResponseEntity<CollectionModel<EntityModel<TipoSolo>>> index(@RequestParam(required = false) String solo, @PageableDefault(size = 5) Pageable pageable){

        List<EntityModel<TipoSolo>> tiposSoloModel = new ArrayList<>();

        if (solo == null) {
            List<TipoSolo> tiposSolo = repository.findAll(pageable).getContent();
            for (TipoSolo tipoSolo : tiposSolo) {
                tiposSoloModel.add(getTipoSoloModel(tipoSolo));
            }
        } else {
            List<TipoSolo> tiposSolo = repository.findByNameContaining(solo, pageable).getContent();
            for (TipoSolo tipoSolo : tiposSolo) {
                tiposSoloModel.add(getTipoSoloModel(tipoSolo));
            }
        }

        CollectionModel<EntityModel<TipoSolo>> collectionModel = CollectionModel.of(tiposSoloModel);
        collectionModel.add(getSelfLink());
        return ResponseEntity.ok(collectionModel);
    }

    @PostMapping
    @ApiOperation("Cria um novo tipo de solo")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Tipo de solo cadastrado com sucesso"),
        @ApiResponse(code = 400, message = "Erro na validação dos dados da requisição")
    })
    public ResponseEntity<EntityModel<TipoSolo>> create(@RequestBody @Valid TipoSolo tipoSolo){
        log.info("Cadastrando tipo de solo: " + tipoSolo);
        TipoSolo postObj = repository.save(tipoSolo);
        EntityModel<TipoSolo> tipoSoloModel = getTipoSoloModel(postObj);
        tipoSoloModel.add(getSelfLink());
        tipoSoloModel.add(getUpdateLink(postObj.getId()));
        tipoSoloModel.add(getDeleteLink(postObj.getId()));
        return ResponseEntity.created(tipoSoloModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(tipoSoloModel);
    }

    @GetMapping("{id}")
    @ApiOperation("Retorna os detalhes de um tipo de solo")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Tipo de solo encontrado com sucesso"),
        @ApiResponse(code = 404, message = "Tipo de solo não encontrado")
    })
    public ResponseEntity<EntityModel<TipoSolo>> show(@PathVariable Long id){
        log.info("Buscando tipo de solo com id " + id);
        TipoSolo tipoSolo = getTipoSolo(id);
        EntityModel<TipoSolo> tipoSoloModel = getTipoSoloModel(tipoSolo);
        tipoSoloModel.add(getSelfLink());
        tipoSoloModel.add(getUpdateLink(id));
        tipoSoloModel.add(getDeleteLink(id));
        return ResponseEntity.ok(tipoSoloModel);
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
    public ResponseEntity<EntityModel<TipoSolo>> update(@PathVariable Long id, @RequestBody @Valid TipoSolo tipoSolo){
        log.info("Alterando tipo de solo com id " + id);
        getTipoSolo(id);
        tipoSolo.setId(id);
        TipoSolo putObj = repository.save(tipoSolo);
        EntityModel<TipoSolo> tipoSoloModel = getTipoSoloModel(putObj);
        tipoSoloModel.add(getSelfLink());
        tipoSoloModel.add(getDeleteLink(putObj.getId()));
        return ResponseEntity.ok(tipoSoloModel);
    }

    private TipoSolo getTipoSolo(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo de solo não existe"));
    }
}
