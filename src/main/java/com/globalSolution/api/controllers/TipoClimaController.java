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

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/tipos-clima")
public class TipoClimaController {
    Logger log = LoggerFactory.getLogger(TipoClimaController.class);

    List<TipoClima> tiposClima = new ArrayList<>();

    @Autowired
    TipoClimaRepository repository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    @GetMapping
    @ApiOperation("Retorna uma lista de tipos de clima")
    public ResponseEntity<CollectionModel<EntityModel<TipoClima>>> index(@RequestParam(required = false) String clima, @PageableDefault(size = 5) Pageable pageable){

        List<EntityModel<TipoClima>> tiposClimaModel = new ArrayList<>();

        if (clima == null) {
            List<TipoClima> tiposClima = repository.findAll(pageable).getContent();
            for (TipoClima tipoClima : tiposClima) {
                tiposClimaModel.add(getTipoClimaModel(tipoClima));
            }
        } else {
            List<TipoClima> tiposClima = repository.findByNameContaining(clima, pageable).getContent();
            for (TipoClima tipoClima : tiposClima) {
                tiposClimaModel.add(getTipoClimaModel(tipoClima));
            }
        }

        CollectionModel<EntityModel<TipoClima>> collectionModel = CollectionModel.of(tiposClimaModel);
        collectionModel.add(getSelfLink());
        return ResponseEntity.ok(collectionModel);
    }

    @PostMapping
    @ApiOperation("Cria um novo tipo de clima")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Tipo de clima cadastrado com sucesso"),
        @ApiResponse(code = 400, message = "Erro na validação dos dados da requisição")
    })
    public ResponseEntity<EntityModel<TipoClima>> create(@RequestBody @Valid TipoClima tipoClima){
        log.info("Cadastrando tipo de clima: " + tipoClima);
        TipoClima postObj = repository.save(tipoClima);
        EntityModel<TipoClima> tipoClimaModel = getTipoClimaModel(postObj);
        tipoClimaModel.add(getSelfLink());
        tipoClimaModel.add(getUpdateLink(postObj.getId()));
        tipoClimaModel.add(getDeleteLink(postObj.getId()));
        return ResponseEntity.created(tipoClimaModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(tipoClimaModel);
    }

    @GetMapping("{id}")
    @ApiOperation("Retorna os detalhes de um tipo de clima")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Tipo de clima encontrado com sucesso"),
        @ApiResponse(code = 404, message = "Tipo de clima não encontrado")
    })
    public ResponseEntity<EntityModel<TipoClima>> show(@PathVariable Long id){
        log.info("Buscando tipo de clima com id " + id);
        TipoClima tipoClima = getTipoClima(id);
        EntityModel<TipoClima> tipoClimaModel = getTipoClimaModel(tipoClima);
        tipoClimaModel.add(getSelfLink());
        tipoClimaModel.add(getUpdateLink(id));
        tipoClimaModel.add(getDeleteLink(id));
        return ResponseEntity.ok(tipoClimaModel);
    }

    @DeleteMapping("{id}")
    @ApiOperation("Exclui um tipo de clima")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Tipo de clima excluído com sucesso"),
        @ApiResponse(code = 404, message = "Tipo de clima não encontrado")
    })
    public ResponseEntity<TipoClima> destroy(@PathVariable Long id){
        log.info("Apagando tipo de clima com id " + id);
        repository.delete(getTipoClima(id));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza um tipo de clima")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Tipo de clima atualizado com sucesso"),
        @ApiResponse(code = 400, message = "Erro na validação dos dados da requisição"),
        @ApiResponse(code = 404, message = "Tipo de clima não encontrado")
    })
    public ResponseEntity<EntityModel<TipoClima>> update(@PathVariable Long id, @RequestBody @Valid TipoClima tipoClima){
        log.info("Alterando tipo de clima com id " + id);
        getTipoClima(id);
        tipoClima.setId(id);
        TipoClima putObj = repository.save(tipoClima);
        EntityModel<TipoClima> tipoClimaModel = getTipoClimaModel(putObj);
        tipoClimaModel.add(getSelfLink());
        tipoClimaModel.add(getDeleteLink(putObj.getId()));
        return ResponseEntity.ok(tipoClimaModel);
    }

    private TipoClima getTipoClima(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo de clima não existe"));
    }

}
