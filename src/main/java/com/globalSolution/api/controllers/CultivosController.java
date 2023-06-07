package com.globalSolution.api.controllers;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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
    public ResponseEntity<List<Cultivos>> index(@RequestParam(required = false) Cultivos cultivos, Pageable pageable) {
        Page<Cultivos> CultivosPage;
        if (cultivos == null) {
            CultivosPage = repository.findAll(pageable);
        } else {
            CultivosPage = repository.findByNameContaining(cultivos, pageable);
        }
        List<Cultivos> cultivo = CultivosPage.getContent();
        return ResponseEntity.ok(cultivo);
    }

    @PostMapping
    @ApiOperation("Cria um novo cultivo")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Cultivos cadastrado com sucesso"),
        @ApiResponse(code = 400, message = "Erro na validação dos dados da requisição")
    })
    public ResponseEntity<Cultivos> create(@RequestBody @Valid Cultivos cultivo){
        log.info("cadastrando cultivos: " + cultivo);
        repository.save(cultivo);
        return ResponseEntity.status(HttpStatus.CREATED).body(cultivo);
    }

    @GetMapping("{id}")
    @ApiOperation("Retorna os detalhes de um cultivo")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Cultivos encontrado com sucesso"),
        @ApiResponse(code = 404, message = "Cultivos não encontrado")
    })
    public ResponseEntity<Cultivos> show(@PathVariable Long id){
        log.info("buscando cultivos com id " + id);
        return ResponseEntity.ok(getCultivo(id));
    }

    @DeleteMapping("{id}")
    @ApiOperation("Exclui um cultivo")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Cultivos encontrado com sucesso"),
        @ApiResponse(code = 404, message = "Cultivos não encontrado")
    })
    public ResponseEntity<Cultivos> destroy(@PathVariable Long id){
        log.info("apagando cultivo com id " + id);
        repository.delete(getCultivo(id));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza um cultivo")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Cultivos encontrado com sucesso"),
        @ApiResponse(code = 404, message = "Cultivos não encontrado")
    })
    public ResponseEntity<Cultivos> update(@PathVariable Long id, @RequestBody @Valid Cultivos cultivo){
        log.info("alterando cultivo com id " + id);
        getCultivo(id);
        cultivo.setId(id);
        repository.save(cultivo);
        return ResponseEntity.ok(cultivo);
    }

    private Cultivos getCultivo(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cultivos não existe"));
    }

}
