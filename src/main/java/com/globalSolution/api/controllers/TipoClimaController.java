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

import com.globalSolution.api.models.TipoClima;
import com.globalSolution.api.repository.TipoClimaRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/graos")

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

    @ApiOperation("Retorna os detalhes de um grão")

    @ApiResponses({

        @ApiResponse(code = 200, message = "Grão encontrado com sucesso"),

        @ApiResponse(code = 404, message = "Grão não encontrado")

    })

    public Grao show(@PathVariable Long id) {

        log.info("Buscando grão com id " + id);

        return getGrao(id);

    }




    @DeleteMapping("{id}")

    @ApiOperation("Exclui um grão")

    @ApiResponses({

        @ApiResponse(code = 204, message = "Grão excluído com sucesso"),

        @ApiResponse(code = 404, message = "Grão não encontrado")

    })

    public void destroy(@PathVariable Long id) {

        log.info("Apagando grão com id " + id);

        repository.delete(getGrao(id));

    }




    @PutMapping("{id}")

    @ApiOperation("Atualiza um grão")

    @ApiResponses({

        @ApiResponse(code = 200, message = "Grão atualizado com sucesso"),

        @ApiResponse(code = 400, message = "Erro na validação dos dados da requisição"),

        @ApiResponse(code = 404, message = "Grão não encontrado")

    })

    public EntityModel<Grao> update(@PathVariable Long id, @RequestBody @Valid Grao grao) {

        log.info("Alterando grão com id " + id);

        Grao existingGrao = getGrao(id);

        existingGrao.setId_grao(id);

        repository.save(existingGrao);

        return EntityModel.of(existingGrao);

    }




    private Grao getGrao(Long id) {

        return repository.findById(id)

            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Grão não existe"));

    }

}