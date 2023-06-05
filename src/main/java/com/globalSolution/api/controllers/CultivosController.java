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
@RequestMapping("/api/cultivos")
public class CultivosController {
    Logger log = LoggerFactory.getLogger(CultivosController.class);

    List<Cultivo> cultivos = new ArrayList<>();

    @Autowired // IoD IoC
    CultivoRepository repository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    @GetMapping
    @ApiOperation("Retorna uma lista de cultivos")
    public ResponseEntity<CollectionModel<EntityModel<Cultivo>>> index(@RequestParam(required = false) Integer docs, @PageableDefault(size = 5) Pageable pageable){

        List<EntityModel<Cultivo>> cultivosModel = new ArrayList<>();

        if (docs == null) {
            List<Cultivo> cultivos = repository.findAll(pageable).getContent();
            for (Cultivo cultivo : cultivos) {
                cultivosModel.add(getCultivoModel(cultivo));
            }
        } else {
            List<Cultivo> cultivos = repository.findByDocsContaining(docs, pageable).getContent();
            for (Cultivo cultivo : cultivos) {
                cultivosModel.add(getCultivoModel(cultivo));
            }
        }

        CollectionModel<EntityModel<Cultivo>> collectionModel = CollectionModel.of(cultivosModel);
        collectionModel.add(getSelfLink());
        return ResponseEntity.ok(collectionModel);

    }

    @PostMapping
    @ApiOperation("Cria um novo cultivo")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Cultivo cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro na validação dos dados da requisição")
    })
    public ResponseEntity<EntityModel<Cultivo>> create(@RequestBody @Valid Cultivo cultivo){
        log.info("Cadastrando cultivo: " + cultivo);
        Cultivo postObj = repository.save(cultivo);
        EntityModel<Cultivo> cultivoModel = getCultivoModel(postObj);
        cultivoModel.add(getSelfLink());
        cultivoModel.add(getUpdateLink(postObj.getId()));
        cultivoModel.add(getDeleteLink(postObj.getId()));
        return ResponseEntity.created(cultivoModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(cultivoModel);
    }

    @GetMapping("{id}")
    @ApiOperation("Retorna os detalhes de um cultivo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cultivo encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cultivo não encontrado")
    })
    public ResponseEntity<EntityModel<Cultivo>> show(@PathVariable Long id){
        log.info("Buscando cultivo com id " + id);
        Cultivo cultivo = getCultivo(id);
        EntityModel<Cultivo> cultivoModel = getCultivoModel(cultivo);
        cultivoModel.add(getSelfLink());
        cultivoModel.add(getUpdateLink(id));
        cultivoModel.add(getDeleteLink(id));
        return ResponseEntity.ok(cultivoModel);
    }

    @DeleteMapping("{id}")
    @ApiOperation("Exclui um cultivo")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Cultivo excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cultivo não encontrado")
    })
    public ResponseEntity<Cultivo> destroy(@PathVariable Long id){
        log.info("Apagando cultivo com id " + id);
        repository.delete(getCultivo(id));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza um cultivo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cultivo atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro na validação dos dados da requisição"),
        @ApiResponse(responseCode = "404", description = "Cultivo não encontrado")
    })
    public ResponseEntity<EntityModel<Cultivo>> update(@PathVariable Long id, @RequestBody @Valid Cultivo cultivo){
        log.info("Alterando cultivo com id " + id);
        getCultivo(id);
        cultivo.setId(id);
        Cultivo putObj = repository.save(cultivo);
        EntityModel<Cultivo> cultivoModel = getCultivoModel(putObj);
        cultivoModel.add(getSelfLink());
        cultivoModel.add(getDeleteLink(putObj.getId()));
        return ResponseEntity.ok(cultivoModel);
    }

    private Cultivo getCultivo(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cultivo não existe"));
    }

}
