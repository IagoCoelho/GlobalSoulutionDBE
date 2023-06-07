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
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<List<Cultivos>> index(@RequestParam(required = false) Cultivos cultivo, Pageable pageable) {
        Page<Cultivos> cultivosPage;

        if (cultivo == null) {
            cultivosPage = repository.findAll(pageable);
        } else {
            cultivosPage = repository.findByNomeContaining(cultivo.getNome(), pageable);
        }

        List<Cultivos> cultivos = cultivosPage.getContent();
        return ResponseEntity.ok(cultivos);
    }

    @PostMapping
    @ApiOperation("Cria um novo cultivo")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Cultivo cadastrado com sucesso"),
        @ApiResponse(code = 400, message = "Erro na validação dos dados da requisição")
    })
    public EntityModel<Cultivos> create(@RequestBody @Valid Cultivos cultivo) {
        log.info("Cadastrando cultivo: " + cultivo);
        repository.save(cultivo);
        return EntityModel.of(cultivo);
    }

    @GetMapping("{id}")
    @ApiOperation("Retorna os detalhes de um cultivo")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Cultivo encontrado com sucesso"),
        @ApiResponse(code = 404, message = "Cultivo não encontrado")
    })
    public Cultivos show(@PathVariable Long id) {
        log.info("Buscando cultivo com id " + id);
        return getCultivo(id);
    }

    @DeleteMapping("{id}")
    @ApiOperation("Exclui um cultivo")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Cultivo excluído com sucesso"),
        @ApiResponse(code = 404, message = "Cultivo não encontrado")
    })
    public void destroy(@PathVariable Long id) {
        log.info("Apagando cultivo com id " + id);
        repository.delete(getCultivo(id));
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza um cultivo")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Cultivo atualizado com sucesso"),
        @ApiResponse(code = 400, message = "Erro na validação dos dados da requisição"),
        @ApiResponse(code = 404, message = "Cultivo não encontrado")
    })
    public EntityModel<Cultivos> update(@PathVariable Long id, @RequestBody @Valid Cultivos cultivo) {
        log.info("Alterando cultivo com id " + id);
        Cultivos existingCultivo = getCultivo(id);
        existingCultivo.setId(id);
        repository.save(existingCultivo);
        return EntityModel.of(existingCultivo);
    }

    private Cultivos getCultivo(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cultivo não existe"));
    }
    
}
