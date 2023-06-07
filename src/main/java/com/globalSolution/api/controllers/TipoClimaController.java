package com.globalSolution.api.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
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

import com.globalSolution.api.models.TipoClima;
import com.globalSolution.api.repository.TipoClimaRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/tipos-clima")
public class TipoClimaController {
    Logger log = LoggerFactory.getLogger(TipoClimaController.class);

    List<TipoClima> tiposClima = new ArrayList<>();

    @Autowired // IoD IoC
    TipoClimaRepository tipoClimarepository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    @GetMapping
    @ApiOperation("Retorna uma lista de tipo clima")
    public ResponseEntity<List<TipoClima>> index(@RequestParam(required = false) TipoClima tipoClima, Pageable pageable) {
        Page<TipoClima> TiposClimaPage;
        if (tipoClima == null) {
            TiposClimaPage = tipoClimarepository.findAll(pageable);
        } else {
            TiposClimaPage = tipoClimarepository.findByNameContaining(tipoClima, pageable);
        }
        List<TipoClima> tiposClima = TiposClimaPage.getContent();
        return ResponseEntity.ok(tiposClima);
    }

    @PostMapping
    @ApiOperation("Cria um novo tipo de clima ")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Tipo clima cadastrado com sucesso"),
        @ApiResponse(code = 400, message = "Erro na validação dos dados da requisição")
    })
    public EntityModel<TipoClima> create(@RequestBody @Valid TipoClima tipoClima) {
        log.info("Cadastrando grão: " + tipoClima);
        tipoClimarepository.save(tipoClima);
        return EntityModel.of(tipoClima);
    }

    @GetMapping("{id}")
    @ApiOperation("Retorna os detalhes de um clima")
    @ApiResponses({
        @ApiResponse(code = 200, message = " Clima encontrado com sucesso"),
        @ApiResponse(code = 404, message = "Clima não encontrado")
    })
    public TipoClima show(@PathVariable Long id) {
        log.info("Buscando clima com id " + id);
        return getTipoClima(id);
    }

    @DeleteMapping("{id}")
    @ApiOperation("Exclui um clima")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Clima excluído com sucesso"),
        @ApiResponse(code = 404, message = "Clima não encontrado")
    })
    public void destroy(@PathVariable Long id) {
        log.info("Apagando grão com id " + id);
        tipoClimarepository.delete(getTipoClima(id));
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza um clima")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Clima atualizado com sucesso"),
        @ApiResponse(code = 400, message = "Erro na validação dos dados da requisição"),
        @ApiResponse(code = 404, message = "Clima não encontrado")
    })
    public EntityModel<TipoClima> update(@PathVariable Long id, @RequestBody @Valid TipoClima tipoClima) {
        log.info("Alterando clima com id " + id);
        TipoClima existingTipoClima = getTipoClima(id);
        existingTipoClima.setId_clima(id);
        tipoClimarepository.save(existingTipoClima);
        return EntityModel.of(existingTipoClima);
    }

    private TipoClima getTipoClima(Long id) {
        return tipoClimarepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clima não existe"));

    }

}