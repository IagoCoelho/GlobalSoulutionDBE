package com.globalSolution.api.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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

import com.globalSolution.api.models.Grao;
import com.globalSolution.api.repository.GraoRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/graos")
public class GraoController {
    Logger log = LoggerFactory.getLogger(GraoController.class);

    List<Grao> graos = new ArrayList<>();

    @Autowired // IoD IoC
    GraoRepository repository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    @GetMapping
    @ApiOperation("Retorna uma lista de grãos")
    public ResponseEntity<CollectionModel<EntityModel<Grao>>> index(@RequestParam(required = false) Integer docs, @PageableDefault(size = 5) Pageable pageable){

        List<EntityModel<Grao>> graosModel = new ArrayList<>();

        if (docs == null) {
            List<Grao> graos = repository.findAll(pageable).getContent();
            for (Grao grao : graos) {
                graosModel.add(getGraoModel(grao));
            }
        } else {
            List<Grao> graos = repository.findByNameContaining(grao, pageable).getContent();
            for (Grao grao : graos) {
                graosModel.add(getGraoModel(grao));
            }
        }

        CollectionModel<EntityModel<Grao>> collectionModel = CollectionModel.of(graosModel);
        collectionModel.add(getSelfLink());
        return ResponseEntity.ok(collectionModel);

    }

    @PostMapping
    @ApiOperation("Cria um novo grão")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Grão cadastrado com sucesso"),
        @ApiResponse(code = 400, message = "Erro na validação dos dados da requisição")
    })
    public ResponseEntity<EntityModel<Grao>> create(@RequestBody @Valid Grao grao){
        log.info("Cadastrando grão: " + grao);
        Grao postObj = repository.save(grao);
        EntityModel<Grao> graoModel = getGraoModel(postObj);
        graoModel.add(getSelfLink());
        graoModel.add(getUpdateLink(postObj.getId()));
        graoModel.add(getDeleteLink(postObj.getId()));
        return ResponseEntity.created(graoModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(graoModel);
    }

    @GetMapping("{id}")
    @ApiOperation("Retorna os detalhes de um grão")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Grão encontrado com sucesso"),
        @ApiResponse(code = 404, message = "Grão não encontrado")
    })
    public ResponseEntity<EntityModel<Grao>> show(@PathVariable Long id){
        log.info("Buscando grão com id " + id);
        Grao grao = getGrao(id);
        EntityModel<Grao> graoModel = getGraoModel(grao);
        graoModel.add(getSelfLink());
        graoModel.add(getUpdateLink(id));
        graoModel.add(getDeleteLink(id));
        return ResponseEntity.ok(graoModel);
    }

    @DeleteMapping("{id}")
    @ApiOperation("Exclui um grão")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Grão excluído com sucesso"),
        @ApiResponse(code = 404, message = "Grão não encontrado")
    })
    public ResponseEntity<Grao> destroy(@PathVariable Long id){
        log.info("Apagando grão com id " + id);
        repository.delete(getGrao(id));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza um grão")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Grão atualizado com sucesso"),
        @ApiResponse(code = 400, message = "Erro na validação dos dados da requisição"),
        @ApiResponse(code = 404, message = "Grão não encontrado")
    })
    public ResponseEntity<EntityModel<Grao>> update(@PathVariable Long id, @RequestBody @Valid Grao grao){
        log.info("Alterando grão com id " + id);
        getGrao(id);
        grao.setId(id);
        Grao putObj = repository.save(grao);
        EntityModel<Grao> graoModel = getGraoModel(putObj);
        graoModel.add(getSelfLink());
        graoModel.add(getDeleteLink(putObj.getId()));
        return ResponseEntity.ok(graoModel);
    }

    private Grao getGrao(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Grão não existe"));
    }
}
