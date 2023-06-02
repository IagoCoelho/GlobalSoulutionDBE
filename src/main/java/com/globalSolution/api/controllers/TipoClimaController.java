package com.globalSolution.api.controllers;

import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clima")
public class TipoClimaController {
    Logger log = LoggerFactory.getLogger(TipoClimaController.class);

    List<TipoClima> TipoClima = new ArrayList<>();

    @Autowired
    TipoClimaRepository repository;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<TipoClima>>> index (@RequestParam(required = false) String TipoClima, @PageableDefault(size = 5) Pageable pageable){

        List<EntityModel<TipoClima>> TipoClimasModel = new ArrayList<>();
        
        if(TipoClima == null){
            List<TipoClima> TipoClimas = repository.findAll(pageable).getContent();
            for(TipoClima TipoClima : TipoClimas){
                TipoClimasModel.add(getTipoClimaModel(TipoClima));
            }
        } else {
            List<TipoClima> TipoClimas = repository.findByNameContaining(TipoClima, pageable).getContent();
            for (TipoClima TipoClima : TipoClimas){
                TipoClimasModel.add(getTipoClimaModel(TipoClima));
            }
        }

        CollectionModel<EntityModel<TipoClima>> collectionModel = CollectionModel.of(TipoClimasModel);
        collectionModel.add(getSelfLink());
        return ResponseEntity.ok(collectionModel);

    }

    @PostMapping
    public ResponseEntity<EntityModel<TipoClima>> create(@RequestBody @valid TipoClima TipoClima){
        log.info("Cadastrando grão: " + TipoClima);
        TipoClima postObj = repository.save(TipoClima);
        EntityModel<TipoClima> TipoClimaModel = getTipoClimaModel(postObj);
        TipoClimaModel.add(getSelfLink());
        TipoClimaModel.add(getupdateLink(postObj.getId()));
        TipoClimaModel.add(getDeleteLink(postObj.getId()));
        return ResponseEntity.created(TipoClimaModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(TipoClimaModel);
    }

    @GetMapping("{id}")
    public ResponseEntity<EntityModel<TipoClima>> show(@PathVariable Long id){
        log.info("Buscando TipoClima com id " + id);
        TipoClima TipoClima = getTipoClima(id);
        EntityModel<TipoClima> TipoClimaModel = getTipoClimaModel(TipoClima);
        TipoClimaModel.add(getSelfLink());
        TipoClimaModel.add(getUpdateLink(id));
        TipoClimaModel.add(getDeleteLink(id));
        return ResponseEntity.ok(TipoClimaModel);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<TipoClima> destroy(@PathVariable Long id){
        log.info("Apagando TipoClima com id " + id);
        repository.delete(getTipoClima(id));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<EntityModel<TipoClima>> update(@PathVariable Long id, @RequestBody @Valid TipoClima TipoClima){
        log.info("Alterando TipoClima com id " + id);
        getTipoClima(id);
        TipoClima.setId(id);
        TipoClima putObj = repository.save(TipoClima);
        EntityModel<TipoClima> TipoClimaModel = getTipoClimaModel(putObj);
        TipoClimaModel.add(getSelfLink());
        TipoClimaModel.add(getDeleteLink(putObj.getId()));
        return ResponseEntity.ok(TipoClima);
    }

    private TipoClima getTipoClima(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Grão não existe"));
    }
    
}
